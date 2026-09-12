package com.selfpreparation.sportstores.repository;

import com.selfpreparation.sportstores.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCustomerName(String customerName);
    Optional<CartItem> findByCustomerNameAndProductId(String customerName, Long productId);
    void deleteByCustomerName(String customerName);
    void deleteByCustomerNameAndProductId(String customerName, Long productId);
}
