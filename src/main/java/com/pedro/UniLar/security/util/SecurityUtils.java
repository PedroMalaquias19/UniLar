package com.pedro.UniLar.security.util;

import com.pedro.UniLar.profile.user.entities.User;
import com.pedro.UniLar.profile.user.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static User getCurrentUserOrThrow() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User user)) {
            throw new IllegalStateException("Usuário não autenticado");
        }
        return user;
    }

    public static boolean isAdmin(User user) {
        return user.getRole() == Role.ADMIN;
    }

    public static boolean isSindico(User user) {
        return user.getRole() == Role.SINDICO;
    }
}
