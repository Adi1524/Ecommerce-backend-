package com.pureproduce.ecommerce_oil.service;

// import java.time.LocalDate;
// import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pureproduce.ecommerce_oil.exception.ProductException;
import com.pureproduce.ecommerce_oil.model.Product;
import com.pureproduce.ecommerce_oil.model.Rating;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.repository.RatingRepository;
import com.pureproduce.ecommerce_oil.request.RatingRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RatingServiceImplementation implements RatingService{
    private RatingRepository ratingRepository;
    private ProductService productService;

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        if (product == null) {
            throw new ProductException("Product not found for the given ID.");
        }
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        // rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRatings(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
    

}
