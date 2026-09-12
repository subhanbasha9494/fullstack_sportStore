package com.selfpreparation.sportstores.dto;

import java.math.BigDecimal;

public record CartResponseDto(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        Integer quantity
) {}
