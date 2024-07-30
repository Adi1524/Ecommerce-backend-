package com.pureproduce.ecommerce_oil.service;

import com.pureproduce.ecommerce_oil.exception.CartItemException;
import com.pureproduce.ecommerce_oil.exception.UserException;
import com.pureproduce.ecommerce_oil.model.Cart;
import com.pureproduce.ecommerce_oil.model.CartItem;
import com.pureproduce.ecommerce_oil.model.Product;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId)throws CartItemException;
    

}
