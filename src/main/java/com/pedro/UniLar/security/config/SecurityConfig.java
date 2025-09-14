package com.pedro.UniLar.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static achama.website.profile.user.Permission.*;
import static achama.website.profile.user.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        //COMPANY PERMISSIONS AND ROLES
                        .requestMatchers(GET, "/api/v1/company/**").permitAll()
                        .requestMatchers("/api/v1/company/**").hasAnyRole(ADMIN.name(), COMPANY_MANAGER.name())
                        .requestMatchers(PUT, "/api/v1/company/**").hasAnyAuthority(COMPANY_UPDATE.name(), ADMIN.name())
                        .requestMatchers(POST, "/api/v1/company/**").hasAnyAuthority(COMPANY_CREATE.name(), ADMIN.name())
                        .requestMatchers(DELETE, "/api/v1/company/**").hasAnyAuthority(COMPANY_DELETE.name(), ADMIN.name())

                        //DONATION PERMISSIONS AND ROLES
                        .requestMatchers(POST, "/api/v1/donations/financial/").permitAll()
                        .requestMatchers(POST, "/api/v1/donations/material/").permitAll()
                        .requestMatchers(POST, "/api/v1/donations/request/").permitAll()
                        .requestMatchers("/api/v1/donations/**").permitAll()
                        .requestMatchers(GET, "/api/v1/donations/**").permitAll()
                        .requestMatchers(PUT, "/api/v1/donations/**").permitAll()
                        .requestMatchers(POST, "/api/v1/donations/**").permitAll()
                        .requestMatchers(DELETE, "/api/v1/donations/**").permitAll()
//                        .requestMatchers("/api/v1/donations/**").hasAnyRole(ADMIN.name(), DONATION_MANAGER.name())
//                        .requestMatchers(GET, "/api/v1/donations/**").hasAnyAuthority(DONATION_READ.name(), ADMIN.name())
//                        .requestMatchers(PUT, "/api/v1/donations/**").hasAnyAuthority(DONATION_UPDATE.name(), ADMIN.name())
//                        .requestMatchers(POST, "/api/v1/donations/**").hasAnyAuthority(DONATION_CREATE.name(), ADMIN.name())
//                        .requestMatchers(DELETE, "/api/v1/donations/**").hasAnyAuthority(DONATION_DELETE.name(), ADMIN.name())

                        //CONTENT PERMISSIONS AND ROLES
                        .requestMatchers(GET, "/api/v1/content/**").permitAll()
                        .requestMatchers("/api/v1/content/**").hasAnyRole(ADMIN.name(), CONTENT_MANAGER.name())
                        .requestMatchers(PUT, "/api/v1/content/**").hasAnyAuthority(CONTENT_UPDATE.name(), ADMIN.name())
                        .requestMatchers(POST, "/api/v1/content/**").hasAnyAuthority(CONTENT_CREATE.name(), ADMIN.name())
                        .requestMatchers(DELETE, "/api/v1/content/**").hasAnyAuthority(CONTENT_DELETE.name(), ADMIN.name())

                        .anyRequest()
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) ->
                                SecurityContextHolder.clearContext()
                        )
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000/", "http://127.0.0.1:5500", "https://achama.netlify.app", "http://localhost:63342"));
        configuration.setAllowedMethods(List.of(GET.name(), POST.name(), PUT.name(), DELETE.name(), PATCH.name(),  OPTIONS.name()));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
