package com.pedro.UniLar.profile.user.dto;

import com.pedro.UniLar.profile.user.enums.Role;

public record UserResponse(
        Long id,
        String nome,
        String sobrenome,
        String email,
        String telefone,
        String NIF,
        boolean enabled,
        boolean nonLocked,
        Role role,
        String fotografia) {
}
