package com.pureproduce.ecommerce_oil.repository;

import com.pureproduce.ecommerce_oil.model.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT r FROM Rating r WHERE r.product.id = :productId")
    List<Rating> getAllProductsRating(@Param("productId") Long productId);
}
