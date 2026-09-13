package com.selfpreparation.sportstores.dto;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity
) {}
