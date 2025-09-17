package com.pedro.UniLar.profile.user;

import com.pedro.UniLar.profile.user.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.pedro.UniLar.profile.user.enums.Role.ADMIN;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    protected CommandLineRunner commandLineRunner() {
        return args -> {
            User existingUser = userRepository.findByEmail("javahater000@gmail.com")
                    .orElse(null);

            if(existingUser == null){
                var user = User.builder()
                        .nome("Admin")
                        .sobrenome("Admin")
                        .email("javahater000@gmail.com")
                        .telefone(null)
                        .NIF("008283849213232")
                        .password(passwordEncoder.encode("admin"))
                        .enabled(true)
                        .nonLocked(true)
                        .role(ADMIN)
                        .build();

                userRepository.save(user);
                log.info("Admin registration complete");
            } else {
                // If the admin already exists, skip the registration process
                log.info("Admin already exists. Skipping registration.");
            }
        };
    }
}
