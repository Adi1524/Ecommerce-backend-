package com.pureproduce.ecommerce_oil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pureproduce.ecommerce_oil.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Custom query to get orders for a specific user with certain statuses
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = com.pureproduce.ecommerce_oil.user.domain.OrderStatus.PLACED OR o.orderStatus = com.pureproduce.ecommerce_oil.user.domain.OrderStatus.CONFIRMED OR o.orderStatus = com.pureproduce.ecommerce_oil.user.domain.OrderStatus.SHIPPED OR o.orderStatus = com.pureproduce.ecommerce_oil.user.domain.OrderStatus.DELIVERED)")
    List<Order> getUsersOrders(@Param("userId") Long userId);

    // Retrieve all orders sorted by creation date in descending order
    List<Order> findAllByOrderByCreatedAtDesc();
}
