package com.pedro.UniLar.profile.user.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
        USER(Set.of()),
        CONDOMINO(Set.of(
                        Permission.CONDOMINO_READ,
                        Permission.CONDOMINO_UPDATE)),
        SINDICO(Set.of(
                        Permission.SINDICO_READ,
                        Permission.SINDICO_UPDATE,
                        Permission.CONDOMINO_CREATE,
                        Permission.CONDOMINO_READ,
                        Permission.CONDOMINO_UPDATE)),
        ADMIN(Set.of(
                        Permission.ADMIN_READ,
                        Permission.ADMIN_CREATE,
                        Permission.ADMIN_UPDATE,
                        Permission.SINDICO_READ,
                        Permission.SINDICO_CREATE,
                        Permission.SINDICO_UPDATE,
                        Permission.CONDOMINO_READ,
                        Permission.CONDOMINO_CREATE,
                        Permission.CONDOMINO_UPDATE
                ));

        private final Set<Permission> permissions;

        Role(Set<Permission> permissions) {
                this.permissions = permissions;
        }

        public Set<SimpleGrantedAuthority> getAuthorities() {
                Set<SimpleGrantedAuthority> authorities = permissions
                                .stream()
                                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                                .collect(Collectors.toSet());
                authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
                return authorities;
        }
}
