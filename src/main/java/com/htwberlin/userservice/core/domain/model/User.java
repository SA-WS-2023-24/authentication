package com.htwberlin.userservice.core.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;

    @ElementCollection
    private List<UUID> orderHistoty;


    private String paymentMethod;

    private boolean isSubscribedToNewsletter;

}
