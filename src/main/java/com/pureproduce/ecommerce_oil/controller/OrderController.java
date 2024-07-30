package com.pureproduce.ecommerce_oil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pureproduce.ecommerce_oil.exception.OrderException;
import com.pureproduce.ecommerce_oil.exception.UserException;
import com.pureproduce.ecommerce_oil.model.Address;
import com.pureproduce.ecommerce_oil.model.Order;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.service.OrderService;
import com.pureproduce.ecommerce_oil.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrderHandler(@RequestBody Address shippingAddress, @RequestHeader("Authorization")String jwt) throws UserException{
        User user= userService.findUserProfileByJwt(jwt);
        Order order = orderService.createOrder(user, shippingAddress);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistoryHandler(@RequestHeader("Authorization") String jwt) throws OrderException,UserException{
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> userOrders = orderService.userOrderHistory(user.getId());
        return new ResponseEntity<List<Order>>(userOrders, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderByIdHandler(@RequestHeader("Authorization") String jwt, @PathVariable("orderId") Long orderId) throws OrderException,UserException{
        // User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
    }



}
