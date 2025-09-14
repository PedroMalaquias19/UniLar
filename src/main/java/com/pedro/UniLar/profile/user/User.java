package com.pedro.UniLar.profile.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pedro.UniLar.profile.emailconfirmation.EmailConfirmationToken;
import com.pedro.UniLar.profile.token.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Long id_usuario;

    @Column(nullable = false)
    private String nome;

    private String sobrenome;

    @Column(nullable = false, unique = true)
    @Email
    @NotBlank(message = "O email é obrigatório")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "A password é obrigatória")
    private String password;

    private String telefone;

    @Column(unique = true, nullable = false)
    private String NIF;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private boolean nonLocked;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String fotografia;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Token> tokens;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<EmailConfirmationToken> confirmationTokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getPassword(){
        return password;
    }

    public Optional<String> getFotografia() {
        return Optional.ofNullable(fotografia);
    }
}
