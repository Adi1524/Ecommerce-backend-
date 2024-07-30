package com.pureproduce.ecommerce_oil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pureproduce.ecommerce_oil.exception.CartItemException;
import com.pureproduce.ecommerce_oil.exception.UserException;
import com.pureproduce.ecommerce_oil.model.CartItem;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.request.UpdateCartRequest;
import com.pureproduce.ecommerce_oil.response.ApiResponse;
import com.pureproduce.ecommerce_oil.service.CartItemService;
import com.pureproduce.ecommerce_oil.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/cart_items")
@AllArgsConstructor
@Tag(name="Cart Item Management", description = "create cart item delete cart item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandler(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws CartItemException, UserException{
        System.out.println("user jwt"+jwt);
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse("Item Removed from cart", true);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestBody UpdateCartRequest cartItemRequest,
            @RequestHeader("Authorization") String jwt) 
            throws CartItemException, UserException {
    
        // Log the received request
        System.out.println("Received cartItem: " + cartItemRequest);

        // If you are using ObjectMapper to map JSON, let's log the incoming request directly
        System.out.println("Received cartItem JSON: " + cartItemRequest.toString());
        
        User user = userService.findUserProfileByJwt(jwt);
        // CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItemRequest);
        // return new ResponseEntity<CartItem>(updatedCartItem, HttpStatus.ACCEPTED);
        return null;
    }
    
    
    
    

}
