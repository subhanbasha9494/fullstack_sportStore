package com.selfpreparation.sportstores.dto;

public record RegisterResponseDto(String message, UserDto user, String jwtToken) {
}
