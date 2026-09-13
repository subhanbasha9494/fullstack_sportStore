package com.selfpreparation.sportstores.service;

import com.selfpreparation.sportstores.dto.OrderResponseDto;
import com.selfpreparation.sportstores.dto.PlaceOrderRequestDto;

import java.util.List;

public interface IOrderService {
    OrderResponseDto placeOrder(String customerName, PlaceOrderRequestDto request);
    List<OrderResponseDto> getOrderHistory(String customerName);
}
