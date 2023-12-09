package ru.sfu.zooshopback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_user_details")
@Data
@NoArgsConstructor
public class UserDetails {

    @Id
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "telephone", unique = true)
    private String telephone;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
}
