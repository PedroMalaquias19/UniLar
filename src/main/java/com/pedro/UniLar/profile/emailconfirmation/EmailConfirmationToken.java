package com.pedro.UniLar.profile.emailconfirmation;

import com.pedro.UniLar.profile.user.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailConfirmationToken {

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Long id;

    @Column(
            nullable = false
    )
    private String token;

    private LocalDateTime createAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    public int getVersion() {
        return 0;
    }
}
