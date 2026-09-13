package com.selfpreparation.sportstores.repository;

import com.selfpreparation.sportstores.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerNameOrderByCreatedAtDesc(String customerName);
}
