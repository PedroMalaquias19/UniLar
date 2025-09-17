package com.pedro.UniLar.condominio.moradia.morador;

import com.pedro.UniLar.condominio.moradia.contrato.ContratoPropriedade;
import com.pedro.UniLar.condominio.moradia.contrato.ContratoPropriedadeService;
import com.pedro.UniLar.condominio.moradia.contrato.enums.StatusContratoPropriedade;
import com.pedro.UniLar.condominio.moradia.morador.dto.MoradorRequest;
import com.pedro.UniLar.condominio.moradia.morador.dto.MoradorResponse;
import com.pedro.UniLar.exception.BadRequestException;
import com.pedro.UniLar.exception.NotAllowedException;
import com.pedro.UniLar.exception.NotFoundException;
import com.pedro.UniLar.profile.user.UserRepository;
import com.pedro.UniLar.profile.user.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MoradorService {

    private final MoradorRepository repository;
    private final UserRepository userRepository;
    private final ContratoPropriedadeService contratoService;
    private final MoradorMapper mapper;

    @Transactional
    public MoradorResponse adicionar(Long contratoId, MoradorRequest request){
        ContratoPropriedade contrato = contratoService.getEntity(contratoId);
        if(!contrato.ativo()){
            throw new NotAllowedException("Contrato não está ativo");
        }
        User usuario = userRepository.findById(request.usuarioId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + request.usuarioId()));

        boolean proprietario = Boolean.TRUE.equals(request.proprietario());
        if(proprietario){
            // garantir que ainda não há morador marcado como proprietário neste contrato
            if(repository.existsByContrato_IdContratoAndProprietarioTrue(contratoId)){
                throw new NotAllowedException("Já existe proprietário associado ao contrato");
            }
            // garantir que usuário é igual ao proprietário do contrato
            if(!usuario.getIdUsuario().equals(contrato.getProprietario().getIdUsuario())){
                throw new BadRequestException("Usuário não corresponde ao proprietário do contrato");
            }
        }

        Morador morador = mapper.toEntity(request);
        morador.setContrato(contrato);
        morador.setUsuario(usuario);

        Morador salvo = repository.save(morador);
        return mapper.toResponse(salvo);
    }

    public List<MoradorResponse> listar(Long contratoId){
        return repository.findByContrato_IdContrato(contratoId).stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public void remover(Long id){
        Morador morador = getEntity(id);
        repository.delete(morador);
    }

    protected Morador getEntity(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Morador não encontrado: " + id));
    }
}
