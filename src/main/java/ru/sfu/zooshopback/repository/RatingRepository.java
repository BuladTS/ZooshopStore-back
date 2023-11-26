package ru.sfu.zooshopback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshopback.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.product = ?1")
    Double getAverageProductRating(Long productId);
}
