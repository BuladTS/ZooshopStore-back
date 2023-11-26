package ru.sfu.zooshopback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sfu.zooshopback.repository.ProductRepository;
import ru.sfu.zooshopback.repository.RatingRepository;
import ru.sfu.zooshopback.service.exception.ResourceNotFoundException;

@Service
public class RatingService {
    private final ProductRepository productRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(ProductRepository productRepository, RatingRepository ratingRepository) {
        this.productRepository = productRepository;
        this.ratingRepository = ratingRepository;
    }

    public Double getProductRating(Long productId) throws ResourceNotFoundException {
        // check if product exists
        productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with id " + productId)
        );

        return ratingRepository.getAverageProductRating(productId);
    }
}
