package com.pedro.UniLar.profile.token;

import achama.website.profile.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Token")
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue
    private Long id;

    private String token;

    private TokenType type;

    private boolean expired;

    private boolean revoked;

    public enum TokenType {
        BEARER
    }

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;

}
