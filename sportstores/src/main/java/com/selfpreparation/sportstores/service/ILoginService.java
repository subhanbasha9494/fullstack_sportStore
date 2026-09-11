package com.selfpreparation.sportstores.service;

import com.selfpreparation.sportstores.dto.LoginRequestDto;
import com.selfpreparation.sportstores.dto.LoginResponseDto;


public interface ILoginService {
    LoginResponseDto login(LoginRequestDto request);
}
