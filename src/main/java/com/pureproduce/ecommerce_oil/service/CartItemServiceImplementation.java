package com.pureproduce.ecommerce_oil.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.pureproduce.ecommerce_oil.exception.CartItemException;
import com.pureproduce.ecommerce_oil.exception.UserException;
import com.pureproduce.ecommerce_oil.model.Cart;
import com.pureproduce.ecommerce_oil.model.CartItem;
import com.pureproduce.ecommerce_oil.model.Product;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class CartItemServiceImplementation implements CartItemService {

    private static final Logger logger = LoggerFactory.getLogger(CartItemServiceImplementation.class);

    private CartItemRepository cartItemRepository;
    private UserService userService;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        logger.debug("Condition met, updating item.");
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

        CartItem createdCartItem= cartItemRepository.save(cartItem);

        return createdCartItem; 
    }

    @Override
public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
    CartItem item = findCartItemById(id);
    User user = userService.findUserById(item.getUserId());

    if (user.getId().equals(userId)) {
        System.out.println("\n\ncartItem from service, this is the quantity: " + item.getQuantity() + "\nthis is cartItem to be updated quantity: " + cartItem.getQuantity() + "\n\n");

        // Only update fields that are provided
        if (cartItem.getQuantity() != null) {
            item.setQuantity(cartItem.getQuantity());
        }

        // Recalculate the price and discounted price based on the updated quantity
        if (item.getProduct() != null) {
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getQuantity() * item.getProduct().getDiscountedPrice());
        } else {
            throw new CartItemException("Product is null for the cart item");
        }

        return cartItemRepository.save(item);
    } else {
        throw new CartItemException("You can't update another user's cart item");
    }
}

    

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {

        CartItem cartItem =  cartItemRepository.isCartItemExist(cart, product, null, userId);
        return cartItem;

    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItemId);
        User user = userService.findUserById(cartItem.getUserId());
        User reqUser = userService.findUserById(userId);

        if(user.getId().equals(reqUser.getId())){
            cartItemRepository.delete(cartItem);
        }else{
            throw new UserException("you can't remove anthet user's item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        if(cartItem.isPresent()){
            return cartItem.get();
        }else{
            throw new CartItemException("Cart item not present with the given id"+cartItemId);
        }
        
    }

}
