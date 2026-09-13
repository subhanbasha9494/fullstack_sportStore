package com.selfpreparation.sportstores.service.impl;

import com.selfpreparation.sportstores.dto.OrderItemResponseDto;
import com.selfpreparation.sportstores.dto.OrderResponseDto;
import com.selfpreparation.sportstores.dto.PlaceOrderRequestDto;
import com.selfpreparation.sportstores.entity.Order;
import com.selfpreparation.sportstores.entity.OrderItem;
import com.selfpreparation.sportstores.repository.OrderRepository;
import com.selfpreparation.sportstores.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderResponseDto placeOrder(String customerName, PlaceOrderRequestDto request){
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setTotalAmount(request.totalAmount());
        order.setStatus("PLACED");
        order.setCreatedAt(Instant.now());

        List<OrderItem> items = request.items().stream().map(i -> {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(i.productId());
            item.setProductName(i.productName());
            item.setPrice(i.price());
            item.setQuantity(i.quantity());
            return item;
        }).toList();

        order.setOrderItems(items);
        Order saved = orderRepository.save(order);
        return mapToDto(saved);
    }

    @Override
    public List<OrderResponseDto> getOrderHistory(String customerName) {
        return orderRepository.findByCustomerNameOrderByCreatedAtDesc(customerName)
                .stream().map(this::mapToDto).toList();
    }

    private OrderResponseDto mapToDto(Order order) {
        List<OrderItemResponseDto> items = order.getOrderItems().stream()
                .map(i -> new OrderItemResponseDto(i.getProductId(), i.getProductName(), i.getPrice(), i.getQuantity()))
                .toList();
        return new OrderResponseDto(order.getOrder_id(), order.getStatus(), order.getTotalAmount(), order.getCreatedAt(), items);
    }
}
