package com.pureproduce.ecommerce_oil.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pureproduce.ecommerce_oil.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>{

    @Query("SELECT c FROM Cart c WHERE c.userId=:userId ")
    public Cart findByUserId(@Param("userId")Long userId);

}
