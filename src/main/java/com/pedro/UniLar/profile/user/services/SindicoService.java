package com.pedro.UniLar.profile.user.services;

import com.pedro.UniLar.profile.user.dto.SindicoResponse;
import com.pedro.UniLar.profile.user.mapper.SindicoMapper;
import com.pedro.UniLar.profile.user.repositories.SindicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SindicoService {

    private final SindicoRepository sindicoRepository;
    private final SindicoMapper sindicoMapper;

    public List<SindicoResponse> listarTodos() {
        return sindicoMapper.toResponseList(sindicoRepository.findAll());
    }

    public List<SindicoResponse> listarSemMandatoAtivo() {
        return sindicoMapper.toResponseList(sindicoRepository.findAllWithoutActiveMandate());
    }
}
