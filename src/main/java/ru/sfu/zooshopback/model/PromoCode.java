package ru.sfu.zooshopback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

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

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "promoCode")
//    @JsonIgnore
//    @ToString.Exclude
//    private List<Order> orderList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "category_promocode_ref",
            joinColumns = {@JoinColumn(name = "promocode_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    @JsonIgnore
    private List<Category> categoryList;
}
