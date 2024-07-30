package com.pureproduce.ecommerce_oil.service;



import java.util.List;

import com.pureproduce.ecommerce_oil.exception.OrderException;
import com.pureproduce.ecommerce_oil.model.Address;
import com.pureproduce.ecommerce_oil.model.Order;
import com.pureproduce.ecommerce_oil.model.User;

public interface OrderService {
         
    public Order createOrder(User user, Address shippingAddress);

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> userOrderHistory(Long orderId) throws OrderException;

    public Order placedOrder(Long orderId) throws OrderException;

    public Order orderConfirmed(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order cancledOrder(Long orderId) throws OrderException;

	public String deleteOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrders();

    

}
