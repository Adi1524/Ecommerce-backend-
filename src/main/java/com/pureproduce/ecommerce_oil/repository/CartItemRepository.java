package com.pureproduce.ecommerce_oil.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pureproduce.ecommerce_oil.model.Cart;
import com.pureproduce.ecommerce_oil.model.CartItem;
import com.pureproduce.ecommerce_oil.model.Product;
import com.pureproduce.ecommerce_oil.model.Size;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    
@Query("SELECT ci FROM CartItem ci WHERE ci.cart=:cart AND ci.product=:product AND ci.size=:size AND ci.userId=:userId")
public CartItem isCartItemExist(
    @Param("cart") Cart cart,
    @Param("product") Product product,
    @Param("size") Size size,
    @Param("userId")Long userId);

}





















