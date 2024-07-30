package com.pureproduce.ecommerce_oil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pureproduce.ecommerce_oil.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{

}
