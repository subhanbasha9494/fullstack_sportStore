package com.selfpreparation.sportstores.repository;

import com.selfpreparation.sportstores.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCustomerName(String customerName);
    Optional<CartItem> findByCustomerNameAndProductId(String customerName, Long productId);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.customerName = :customerName")
    void deleteByCustomerName(@Param("customerName") String customerName);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.customerName = :customerName AND c.productId = :productId")
    void deleteByCustomerNameAndProductId(@Param("customerName") String customerName, @Param("productId") Long productId);
}
