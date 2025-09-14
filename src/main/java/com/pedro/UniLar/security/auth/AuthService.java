package com.pedro.UniLar.security.auth;

// Unused imports removed
import com.pedro.UniLar.profile.token.Token;
import com.pedro.UniLar.profile.token.TokenRepository;
import com.pedro.UniLar.profile.user.Role;
import com.pedro.UniLar.profile.user.User;
import com.pedro.UniLar.profile.user.UserService;
// Unused import removed
import com.pedro.UniLar.security.config.JwtService;
// Unused imports removed
import lombok.RequiredArgsConstructor;
// Unused imports removed
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// Unused imports removed
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
// Unused imports removed

// Unused imports removed
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenRepository tokenRepository;
    // Email confirmation disabled
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    // JwtConfig removed (unused)
    // EmailConfig removed

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

    // Email confirmation disabled for simplicity

    // Email confirmation endpoint removed for simplicity

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
        List<Token> allValidTokensByUser = tokenRepository.findAllValidTokensByUser(user.getId_usuario());

        if (allValidTokensByUser.isEmpty())
            return;

        allValidTokensByUser.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(allValidTokensByUser);

    }

}
