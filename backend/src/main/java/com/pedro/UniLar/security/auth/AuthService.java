package com.pedro.UniLar.security.auth;

import com.pedro.UniLar.profile.user.entities.Admin;
import com.pedro.UniLar.profile.user.entities.Sindico;
import com.pedro.UniLar.profile.user.entities.Condomino;
import com.pedro.UniLar.security.auth.dto.SindicoRegisterRequest;
import com.pedro.UniLar.security.auth.dto.CondominoRegisterRequest;
import com.pedro.UniLar.profile.token.Token;
import com.pedro.UniLar.profile.token.TokenRepository;
import com.pedro.UniLar.profile.user.enums.Role;
import com.pedro.UniLar.profile.user.entities.User;
import com.pedro.UniLar.profile.user.UserService;
import com.pedro.UniLar.security.auth.dto.AuthRequest;
import com.pedro.UniLar.security.auth.dto.AuthResponse;
import com.pedro.UniLar.security.auth.dto.RegisterRequest;
import com.pedro.UniLar.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User register(RegisterRequest request) {
        var user = User.builder()
                .nome(request.nome())
                .sobrenome(request.sobrenome())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .NIF(request.NIF())
                .telefone(request.telefone())
                .enabled(true)
                .nonLocked(true)
                .role(Role.USER)
                .build();

        return userService.saveUser(user);
    }

    public Admin registerAdmin(RegisterRequest request) {
        var admin = new Admin();
        admin.setNome(request.nome());
        admin.setSobrenome(request.sobrenome());
        admin.setEmail(request.email());
        admin.setPassword(passwordEncoder.encode(request.password()));
        admin.setNIF(request.NIF());
        admin.setTelefone(request.telefone());
        admin.setEnabled(true);
        admin.setNonLocked(true);
        admin.setRole(Role.ADMIN);
        return (Admin) userService.saveUser(admin);
    }

    public Sindico registerSindico(SindicoRegisterRequest request) {
        var sindico = new Sindico();
        sindico.setNome(request.nome());
        sindico.setSobrenome(request.sobrenome());
        sindico.setEmail(request.email());
        sindico.setPassword(passwordEncoder.encode(request.password()));
        sindico.setNIF(request.NIF());
        sindico.setTelefone(request.telefone());
        sindico.setEnabled(true);
        sindico.setNonLocked(true);
        sindico.setRole(Role.SINDICO);
        return (Sindico) userService.saveUser(sindico);
    }

    public Condomino registerCondomino(CondominoRegisterRequest request) {
        var condomino = new Condomino();
        condomino.setNome(request.nome());
        condomino.setSobrenome(request.sobrenome());
        condomino.setEmail(request.email());
        condomino.setPassword(passwordEncoder.encode(request.password()));
        condomino.setNIF(request.NIF());
        condomino.setTelefone(request.telefone());
        condomino.setEnabled(true);
        condomino.setNonLocked(true);
        condomino.setRole(Role.CONDOMINO);
        condomino.setTipo(request.tipo());
        return (Condomino) userService.saveUser(condomino);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = userService.findByEmail(request.getEmail());

        revokeAllUserTokens(user);

        var jwtToken = jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }

    private void saveUserToken(User saveUser, String jwtToken) {
        var token = Token.builder()
                .user(saveUser)
                .token(jwtToken)
                .type(Token.TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> allValidTokensByUser = tokenRepository.findAllValidTokensByUser(user.getIdUsuario());

        if (allValidTokensByUser.isEmpty())
            return;

        allValidTokensByUser.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(allValidTokensByUser);

    }

}
