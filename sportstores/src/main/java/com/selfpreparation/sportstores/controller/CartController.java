package com.selfpreparation.sportstores.controller;

import com.selfpreparation.sportstores.dto.CartItemDto;
import com.selfpreparation.sportstores.dto.CartResponseDto;
import com.selfpreparation.sportstores.entity.CartItem;
import com.selfpreparation.sportstores.entity.Product;
import com.selfpreparation.sportstores.repository.CartItemRepository;
import com.selfpreparation.sportstores.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cart")
public class CartController {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<CartResponseDto>> getCart(Authentication authentication) {
        String username = authentication.getName();
        List<CartItem> cartItems = cartItemRepository.findByCustomerName(username);
        List<CartResponseDto> response = cartItems.stream()
                .map(item -> productRepository.findById(item.getProductId())
                        .map(product -> new CartResponseDto(
                                product.getId(),
                                product.getName(),
                                product.getDescription(),
                                product.getPrice(),
                                product.getImageUrl(),
                                item.getQuantity()
                        )).orElse(null))
                .filter(item -> item != null)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CartItem> addToCart(@RequestBody CartItemDto dto, Authentication authentication) {
        String username = authentication.getName();
        Optional<CartItem> existing = cartItemRepository.findByCustomerNameAndProductId(username, dto.productId());
        CartItem cartItem = existing.orElse(new CartItem());
        cartItem.setCustomerName(username);
        cartItem.setProductId(dto.productId());
        cartItem.setQuantity(existing.isPresent() ? cartItem.getQuantity() + dto.quantity() : dto.quantity());
        return ResponseEntity.ok(cartItemRepository.save(cartItem));
    }

    @DeleteMapping("/{productId}")
    @Transactional
    public ResponseEntity<Void> removeFromCart(@PathVariable Long productId, Authentication authentication) {
        String username = authentication.getName();
        cartItemRepository.deleteByCustomerNameAndProductId(username, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> clearCart(Authentication authentication) {
        String username = authentication.getName();
        cartItemRepository.deleteByCustomerName(username);
        return ResponseEntity.ok().build();
    }
}
