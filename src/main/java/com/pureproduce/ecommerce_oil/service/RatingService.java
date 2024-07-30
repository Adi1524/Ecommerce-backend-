package com.pureproduce.ecommerce_oil.service;



import java.util.List;

import com.pureproduce.ecommerce_oil.exception.ProductException;
import com.pureproduce.ecommerce_oil.model.Rating;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.request.RatingRequest;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductsRatings(Long productId);
    
}
