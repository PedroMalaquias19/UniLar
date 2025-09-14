package com.pedro.UniLar.profile.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    BENEFICIARY(Collections.emptySet()),
    ADMIN(
            Set.of(
                    Permission.DONATION_READ,
                    Permission.DONATION_CREATE,
                    Permission.DONATION_UPDATE,
                    Permission.DONATION_DELETE,

                    Permission.COMPANY_READ,
                    Permission.COMPANY_CREATE,
                    Permission.COMPANY_UPDATE,
                    Permission.COMPANY_DELETE,

                    Permission.CONTENT_READ,
                    Permission.CONTENT_CREATE,
                    Permission.CONTENT_UPDATE,
                    Permission.CONTENT_DELETE,

                    Permission.ADMIN_READ,
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_DELETE
            )
    ),
    DONATION_MANAGER(
            Set.of(
                    Permission.DONATION_READ,
                    Permission.DONATION_CREATE,
                    Permission.DONATION_UPDATE,
                    Permission.DONATION_DELETE
            )
    ),
    COMPANY_MANAGER(
            Set.of(
                    Permission.COMPANY_READ,
                    Permission.COMPANY_CREATE,
                    Permission.COMPANY_UPDATE,
                    Permission.COMPANY_DELETE
            )
    ),
    CONTENT_MANAGER(
            Set.of(
                    Permission.CONTENT_READ,
                    Permission.CONTENT_CREATE,
                    Permission.CONTENT_UPDATE,
                    Permission.CONTENT_DELETE
            )
    );

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities(){
        Set<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
