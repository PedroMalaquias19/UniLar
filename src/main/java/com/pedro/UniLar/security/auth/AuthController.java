package com.pedro.UniLar.security.auth;

import com.pedro.UniLar.profile.user.entities.User;
import com.pedro.UniLar.profile.user.entities.Admin;
import com.pedro.UniLar.profile.user.entities.Sindico;
import com.pedro.UniLar.profile.user.entities.Condomino;
import com.pedro.UniLar.security.auth.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<Admin> registerAdmin(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.registerAdmin(request));
    }

    @PostMapping("/register/sindico")
    @PreAuthorize("hasAuthority('sindico:create') or hasAuthority('admin:create') or hasRole('ADMIN')")
    public ResponseEntity<Sindico> registerSindico(@RequestBody SindicoRegisterRequest request) {
        return ResponseEntity.ok(authenticationService.registerSindico(request));
    }

    @PostMapping("/register/condomino")
    @PreAuthorize("hasAuthority('condomino:create') or hasAuthority('admin:create') or hasRole('ADMIN')")
    public ResponseEntity<Condomino> registerCondomino(@RequestBody CondominoRegisterRequest request) {
        return ResponseEntity.ok(authenticationService.registerCondomino(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    // refresh-token endpoint removido
}
