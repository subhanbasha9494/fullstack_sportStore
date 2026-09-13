package com.selfpreparation.sportstores.dto;

import java.math.BigDecimal;

public record OrderItemRequestDto(
        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity
) {}