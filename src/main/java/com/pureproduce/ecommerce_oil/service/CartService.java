package com.pureproduce.ecommerce_oil.service;

import com.pureproduce.ecommerce_oil.exception.ProductException;
import com.pureproduce.ecommerce_oil.model.Cart;
import com.pureproduce.ecommerce_oil.model.CartItem;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);
 
    public CartItem addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userid);

    
    
    

}
