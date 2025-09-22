package com.pedro.UniLar.profile.user.services;

import com.pedro.UniLar.condominio.moradia.Moradia;
import com.pedro.UniLar.condominio.moradia.MoradiaMapper;
import com.pedro.UniLar.condominio.moradia.MoradiaRepository;
import com.pedro.UniLar.condominio.moradia.contrato.ContratoPropriedadeMapper;
import com.pedro.UniLar.condominio.moradia.contrato.ContratoPropriedadeRepository;
import com.pedro.UniLar.pagamento.PagamentoMapper;
import com.pedro.UniLar.pagamento.PagamentoRepository;
import com.pedro.UniLar.profile.user.dto.CondominoPropriedadeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CondominoPropriedadeService {

    private final MoradiaRepository moradiaRepository;
    private final MoradiaMapper moradiaMapper;
    private final ContratoPropriedadeRepository contratoRepository;
    private final ContratoPropriedadeMapper contratoMapper;
    private final PagamentoRepository pagamentoRepository;

    public List<CondominoPropriedadeResponse> listarPropriedadesComContratoEPagamentos(Long condominoId) {
        // Moradias que possuem contratos em que o condómino é proprietário
        List<Moradia> moradias = moradiaRepository.findByProprietario(condominoId);

        return moradias.stream().map(m -> {
            var moradiaResp = moradiaMapper.toResponse(m);
            var contratoOpt = contratoRepository.findAtivoByMoradia(m.getIdMoradia());
            var contratoResp = contratoOpt.map(contratoMapper::toResponse).orElse(null);
            var pagamentos = pagamentoRepository.findByMoradia_IdMoradia(m.getIdMoradia()).stream()
                    .map(PagamentoMapper::toResponse)
                    .toList();
            return new CondominoPropriedadeResponse(moradiaResp, contratoResp, pagamentos);
        }).toList();
    }
}
