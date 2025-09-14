package com.pedro.UniLar.profile.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    // Permissões para Condômino
    CONDOMINO_READ("condomino:read"),
    CONDOMINO_UPDATE("condomino:update"),
    CONDOMINO_CREATE("condomino:create"),
    CONDOMINO_DELETE("condomino:delete"),

    // Permissões para Síndico
    SINDICO_READ("sindico:read"),
    SINDICO_UPDATE("sindico:update"),
    SINDICO_CREATE("sindico:create"),
    SINDICO_DELETE("sindico:delete");

    private final String permission;
}
