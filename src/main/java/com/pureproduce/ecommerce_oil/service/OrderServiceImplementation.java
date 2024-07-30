package com.pureproduce.ecommerce_oil.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pureproduce.ecommerce_oil.exception.OrderException;
import com.pureproduce.ecommerce_oil.model.Address;
import com.pureproduce.ecommerce_oil.model.Cart;
import com.pureproduce.ecommerce_oil.model.CartItem;
import com.pureproduce.ecommerce_oil.model.Order;
import com.pureproduce.ecommerce_oil.model.OrderItem;
import com.pureproduce.ecommerce_oil.model.User;
import com.pureproduce.ecommerce_oil.repository.AddressRepository;
import com.pureproduce.ecommerce_oil.repository.OrderItemRepository;
import com.pureproduce.ecommerce_oil.repository.OrderRepository;
import com.pureproduce.ecommerce_oil.repository.UserRepository;
import com.pureproduce.ecommerce_oil.user.domain.OrderStatus;
import com.pureproduce.ecommerce_oil.user.domain.PaymentStatus;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImplementation implements OrderService {

    private CartService cartService;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);
        user.getAddresses().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem item : cart.getCartItems() ){
            OrderItem orderItem = new OrderItem();
            
            orderItem.setUserId(item.getUserId());
            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setDeliveryDate(LocalDateTime.now().plusDays(7));;

            OrderItem createdOrderItem = orderItemRepository.save(orderItem);

            orderItems.add(createdOrderItem);
        }

        Order createdOrder = new Order();

        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItem(cart.getTotalItem());
        createdOrder.setDeliveryDate(LocalDateTime.now().plusDays(7));

        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus(OrderStatus.PENDING);
        createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
        createdOrder.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(createdOrder);

        for(OrderItem item: orderItems){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional <Order> opt = orderRepository.findById(orderId);

        if (opt.isPresent()){
            return opt.get();
        }
        throw new OrderException("\n Order not found with the id "+orderId+"!!");
    }

    @Override
    public List<Order> userOrderHistory(Long orderId) throws OrderException {
        List<Order> userOrders = orderRepository.getUsersOrders(orderId);
        return userOrders;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.PLACED);
        order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
        return order;
    }

    @Override
    public Order orderConfirmed(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order =findOrderById(orderId);
        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);

        
    }

    @Override
    public List<Order> getAllOrders() {

        
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public Order cancledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public String deleteOrder(Long orderId) throws OrderException {
        orderRepository.deleteById(orderId);
        return "\n the order has been deleted";
    }

}
