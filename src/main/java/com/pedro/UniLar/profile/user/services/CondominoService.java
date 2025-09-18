package com.pedro.UniLar.profile.user.services;

import com.pedro.UniLar.profile.user.dto.CondominoResponse;
import com.pedro.UniLar.profile.user.enums.TipoCondomino;
import com.pedro.UniLar.profile.user.mapper.CondominoMapper;
import com.pedro.UniLar.profile.user.repositories.CondominoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CondominoService {

    private final CondominoRepository condominoRepository;
    private final CondominoMapper condominoMapper;

    public List<CondominoResponse> listarTodos() {
        return condominoMapper.toResponseList(condominoRepository.findAll());
    }

    public List<CondominoResponse> listarPorCondominio(Long condominioId) {
        return condominoMapper.toResponseList(condominoRepository.findAllByCondominio(condominioId));
    }

    public List<CondominoResponse> listarProprietariosPorCondominio(Long condominioId) {
        return condominoMapper.toResponseList(
                condominoRepository.findAllByCondominioAndTipo(condominioId, TipoCondomino.PROPRIETARIO));
    }
}
