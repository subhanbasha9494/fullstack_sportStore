package com.selfpreparation.sportstores.service;

import com.selfpreparation.sportstores.dto.RegisterRequestDto;
import com.selfpreparation.sportstores.dto.RegisterResponseDto;

public interface IRegisterService {
    RegisterResponseDto register(RegisterRequestDto request);
}
