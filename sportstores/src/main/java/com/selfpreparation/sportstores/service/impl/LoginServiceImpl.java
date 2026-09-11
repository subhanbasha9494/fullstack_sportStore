package com.selfpreparation.sportstores.service.impl;

import com.selfpreparation.sportstores.dto.LoginRequestDto;
import com.selfpreparation.sportstores.dto.LoginResponseDto;
import com.selfpreparation.sportstores.dto.RegisterResponseDto;
import com.selfpreparation.sportstores.dto.UserDto;
import com.selfpreparation.sportstores.entity.Customer;
import com.selfpreparation.sportstores.repository.CustomerRepository;
import com.selfpreparation.sportstores.service.ILoginService;
import com.selfpreparation.sportstores.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        Customer customer = customerRepository.findByName(request.name())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
        if(!passwordEncoder.matches(request.password(), customer.getPassword()))
            throw new RuntimeException("Invalid username or password");
        String token = jwtUtil.generateJwtToken(customer.getName());
        return new LoginResponseDto("Login Successful", token);
    }
}
