package com.pureproduce.ecommerce_oil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pureproduce.ecommerce_oil.model.OrderItem;
import com.pureproduce.ecommerce_oil.repository.OrderItemRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderItemServiceImplementation implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

}
