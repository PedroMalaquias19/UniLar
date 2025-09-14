package com.pedro.UniLar.security.auth;

import achama.website.email.EmailConfig;
import achama.website.email.EmailSender;
import achama.website.exception.UserAlreadyConfirmedException;
import achama.website.profile.emailconfirmation.EmailConfirmationService;
import achama.website.profile.emailconfirmation.EmailConfirmationToken;
import achama.website.profile.token.Token;
import achama.website.profile.token.TokenRepository;
import achama.website.profile.user.Role;
import achama.website.profile.user.User;
import achama.website.profile.user.UserService;
import achama.website.security.config.JwtConfig;
import achama.website.security.config.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.ResourceClosedException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final EmailConfirmationService emailConfirmationService;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final EmailConfig emailConfig;

    public String register(RegisterRequest request) {

        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .NIF(request.NIF())
                .address(request.address())
                .contact(request.contact())
                .enabled(false)
                .nonLocked(false)
                .role(Role.USER)
                .build();

        var saveUser = userService.saveUser(user);

        sendConfirmationEmail(saveUser);
        return "Please confirm your email to complete the registration";
    }

    private void sendConfirmationEmail(User user) {
        String token = UUID.randomUUID().toString();
        EmailConfirmationToken confirmationToken = EmailConfirmationToken.builder()
                .token(token)
                .createAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .confirmedAt(null)
                .user(user)
                .build();

        emailConfirmationService.saveToken(confirmationToken);

        //Send email confirmation to user
        String confirmationLink = emailConfig.getFrontDomain() + "api/v1/auth/emailConfirmed?token=" + token;
        emailSender.send(user.getEmail(), "Confirm you email", buildConfirmationEmail(user.getFirstName(), confirmationLink));
    }

    @Transactional
    public ResponseEntity<String> confirmEmail(String token) {

        EmailConfirmationToken confirmationToken = emailConfirmationService.getConfirmationToken(token);

        if(confirmationToken.getConfirmedAt() != null){
            throw new UserAlreadyConfirmedException("User already confirmed");
        }

        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new ResourceClosedException("Link Expired");
        }

        User user = confirmationToken.getUser();

        // Optimistic locking with versioning
        int updatedVersion = emailConfirmationService.setConfirmedAt(confirmationToken);

        // Check if update was successful (version mismatch indicates conflict)
        if (updatedVersion != confirmationToken.getVersion()) {
            throw new OptimisticLockingFailureException("Confirmation failed due to conflict");
        }

        emailSender.send(user.getEmail(), "Email Confirmed Successfully", buildSuccessConfirmationEmail(user.getFirstName()));

        return ResponseEntity.accepted().body("Email Confirmed Successfully");
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
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

    private void revokeAllUserTokens(User user) {
        List<Token> allValidTokensByUser = tokenRepository.findAllValidTokensByUser(user.getId());

        if(allValidTokensByUser.isEmpty())
            return;

        allValidTokensByUser.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(allValidTokensByUser);
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

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        final String refreshToken;
        final String userEmail;

        if (Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith(jwtConfig.getTokenPrefix())) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){

            var user = this.userService.findByEmail(userEmail);

            if(jwtService.isTokenValid(refreshToken, user)) {

                var accessToken = jwtService.generateToken(user);

                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                var authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }


    private String buildSuccessConfirmationEmail(String firstName) {
        return "<body style=\"font-family: sans-serif; background-color: #f5f5f5; margin: 0; padding: 0;\">\n" +
                "  <table style=\"width: 100%; max-width: 600px; background-color: #fff; margin: 10px auto; padding: 30px;\">\n" +
                "    <tr>\n" +
                "      <td style=\"text-align: center;\">\n" +
                "        <img src=\"https://your-ach-logo.com/logo.png\" alt=\"ACHAMA Logo\" width=\"150\" height=\"auto\" />\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "\n" +
                "    <tr>\n" +
                "      <td>\n" +
                "        <h2 style=\"text-align: center; font-size: 24px; margin: 20px 0;\">Welcome to ACHAMA, " + firstName + "!</h2>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "\n" +
                "    <tr>\n" +
                "      <td>\n" +
                "        <p style=\"text-align: center; font-size: 16px; line-height: 1.5;\">\n" +
                "          Your ACHAMA registration is complete! We're thrilled to have you join our community.\n" +
                "        </p>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "\n" +
                "    <tr>\n" +
                "      <td>\n" +
                "        <p style=\"text-align: center; font-size: 16px; line-height: 1.5;\">\n" +
                "          Get ready to explore all that ACHAMA has to offer! You can now:\n" +
                "          <ul>\n" +
                "            <li>**[Feature 1]**</li>\n" +
                "            <li>**[Feature 2]**</li>\n" +
                "            <li>**[Feature 3]**</li>\n" +
                "          </ul>\n" +
                "        </p>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "\n" +
                "    <tr>\n" +
                "      <td>\n" +
                "        <p style=\"text-align: center; font-size: 14px; margin-top: 20px;\">\n" +
                "          We're excited to be part of your journey. Happy exploring!\n" +
                "        </p>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "\n" +
                "    <tr>\n" +
                "      <td>\n" +
                "        <p style=\"text-align: center; font-size: 12px; margin-top: 20px;\">\n" +
                "          Sincerely,<br />\n" +
                "          The ACHAMA Team\n" +
                "        </p>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </table>\n" +
                "</body>\n";
    }
    private String buildConfirmationEmail(String firstName, String confirmationLink) {
        return "<body style=\"font-family: sans-serif; background-color: #f5f5f5; margin: 0; padding: 0;\">\n" +
                "  <table style=\"width: 100%; max-width: 600px; background-color: #fff; margin: 10px auto; padding: 30px;\">\n" +
                "    <tr>\n" +
                "      <td style=\"text-align: center;\">\n" +
                "        <img src=\"img-temporary/logo.png\" alt=\"ACHAMA Logo\" width=\"150\" height=\"auto\" />\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "\n" +
                "    <tr>\n" +
                "      <td>\n" +
                "        <h2 style=\"text-align: center; font-size: 24px; margin: 20px 0;\">Welcome to ACHAMA, "+ firstName + "!</h2>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "\n" +
                "    <tr>\n" +
                "      <td>\n" +
                "        <p style=\"text-align: center; font-size: 16px; line-height: 1.5;\">\n" +
                "          Thank you for signing up with ACHAMA! To complete your registration and unlock all the features of ACHAMA, please confirm your email address by clicking the link below:\n" +
                "        </p>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "\n" +
                "    <tr>\n" +
                "      <td style=\"text-align: center;\">\n" +
                "        <a href=\""+ confirmationLink +"\" style=\"background-color: #007bff; color: #fff; padding: 10px 20px; border-radius: 5px; text-decoration: none; font-size: 16px;\">Confirm Your Email Address</a>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "\n" +
                "    <tr>\n" +
                "      <td>\n" +
                "        <p style=\"text-align: center; font-size: 14px; margin-top: 20px;\">\n" +
                "          Thank you for joining our community! We're excited to have you on board.\n" +
                "        </p>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "\n" +
                "    <tr>\n" +
                "      <td>\n" +
                "        <p style=\"text-align: center; font-size: 12px; margin-top: 20px;\">\n" +
                "          Sincerely,<br />\n" +
                "          The ACHAMA Team\n" +
                "        </p>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </table>\n" +
                "</body>";

    }

}
