package ru.sfu.zooshopback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sfu.zooshopback.model.Comment;
import ru.sfu.zooshopback.repository.CommentRepository;
import ru.sfu.zooshopback.repository.ProductRepository;
import ru.sfu.zooshopback.service.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, ProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
    }

    public List<Comment> getAllProductComments(Long productId) throws ResourceNotFoundException {
        // check if product exists
        productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with id " + productId)
        );

        return commentRepository.getAllProductComments(productId);
    }
}
