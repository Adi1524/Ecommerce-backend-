package com.pureproduce.ecommerce_oil.service;

import org.springframework.stereotype.Service;
import com.pureproduce.ecommerce_oil.exception.ProductException;
import com.pureproduce.ecommerce_oil.model.Cart;
import com.pureproduce.ecommerce_oil.model.CartItem;
import com.pureproduce.ecommerce_oil.model.Product;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.repository.CartRepository;
import com.pureproduce.ecommerce_oil.request.AddItemRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartServiceImplementation implements CartService {

    private CartRepository cartRepository;
    private CartItemService cartItemservice;
    private ProductService productService;



    @Override
    public Cart createCart(User user) {
        Cart cart= new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public CartItem addCartItem(Long userId, AddItemRequest req) throws ProductException {

        Cart cart = cartRepository.findByUserId(userId);
        Product product= productService.findProductById(req.getProductId());
        CartItem isPresent = cartItemservice.isCartItemExist(cart, product, req.getSize(), userId);

        if(isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setSize(req.getSize());
            cartItem.setUserId(userId);

            int price = req.getQuantity()*product.getPrice();

            cartItem.setPrice(price);

            CartItem createdCartItem = cartItemservice.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
            return createdCartItem;
        }
        return isPresent;
    }

    @Override
    public Cart findUserCart(Long userid) {
        Cart cart= cartRepository.findByUserId(userid);
        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalItem=0;

        for(CartItem cartItem: cart.getCartItems()){
            totalPrice=totalPrice+cartItem.getPrice();
            totalDiscountedPrice= totalDiscountedPrice+cartItem.getDiscountedPrice();
            totalItem= totalItem+cartItem.getQuantity();
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice-totalDiscountedPrice);

        return cartRepository.save(cart);
    }

}
