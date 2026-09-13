package com.selfpreparation.sportstores.repository;

import com.selfpreparation.sportstores.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
