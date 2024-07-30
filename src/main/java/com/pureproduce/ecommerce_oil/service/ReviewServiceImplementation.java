package com.pureproduce.ecommerce_oil.service;

// import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pureproduce.ecommerce_oil.exception.ProductException;
import com.pureproduce.ecommerce_oil.model.Product;
import com.pureproduce.ecommerce_oil.model.Review;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.repository.ReviewRepository;
import com.pureproduce.ecommerce_oil.request.ReviewRequest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ReviewServiceImplementation implements ReviewService {

    private ReviewRepository reviewRepository;
    private ProductService productService;

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product= productService.findProductById(req.getProductId());
        

        Review review = new Review();
        review.setReview(req.getReview());
        review.setProduct(product);
        review.setUser(user);
        // review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }

}
