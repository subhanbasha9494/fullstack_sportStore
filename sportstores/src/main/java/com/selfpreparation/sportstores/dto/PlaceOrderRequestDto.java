package com.selfpreparation.sportstores.dto;

import java.math.BigDecimal;
import java.util.List;

public record PlaceOrderRequestDto (
    List<OrderItemRequestDto> items,
    BigDecimal totalAmount
){}
