package com.example.zooshopback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "promo_codes")
@Data
@NoArgsConstructor
public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promo_code_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "value", unique = true)
    private String value;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @Column(name = "discount", nullable = false, columnDefinition = "smallint")
    private Short discount;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
