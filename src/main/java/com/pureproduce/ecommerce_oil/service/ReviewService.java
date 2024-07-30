package com.pureproduce.ecommerce_oil.service;

import java.util.List;

import com.pureproduce.ecommerce_oil.exception.ProductException;
import com.pureproduce.ecommerce_oil.model.Review;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.request.ReviewRequest;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;

    public List<Review> getAllReview(Long productId);

}
