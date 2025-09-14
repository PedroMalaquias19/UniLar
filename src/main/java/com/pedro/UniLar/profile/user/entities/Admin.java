package com.pedro.UniLar.profile.user.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admins")
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
}
