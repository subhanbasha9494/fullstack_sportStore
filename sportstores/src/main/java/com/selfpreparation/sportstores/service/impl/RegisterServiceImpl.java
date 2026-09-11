package com.selfpreparation.sportstores.service.impl;

import com.selfpreparation.sportstores.dto.RegisterRequestDto;
import com.selfpreparation.sportstores.dto.RegisterResponseDto;
import com.selfpreparation.sportstores.dto.UserDto;
import com.selfpreparation.sportstores.entity.Customer;
import com.selfpreparation.sportstores.repository.CustomerRepository;
import com.selfpreparation.sportstores.security.TokenBlacklistService;
import com.selfpreparation.sportstores.service.IRegisterService;
import com.selfpreparation.sportstores.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements IRegisterService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    TokenBlacklistService tokenBlacklistService;


    @Override
    public RegisterResponseDto register(RegisterRequestDto request){
            if(customerRepository.existsByName(request.getName()))
                throw new RuntimeException("User Already Exists");
            if(customerRepository.existsByEmail(request.getEmail()))
                throw new RuntimeException("Email Already Exists");
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setMobileNumber(request.getMobileNumber());
        Customer savedCustomer = customerRepository.save(customer);

        UserDto userDto = new UserDto();
        userDto.setUserId(savedCustomer.getCustomerId()); // Adjust getter to match Customer entity ID
        userDto.setName(savedCustomer.getName());
        userDto.setEmail(savedCustomer.getEmail());
        userDto.setMobileNumber(savedCustomer.getMobileNumber());

        String token = jwtUtil.generateJwtToken(savedCustomer.getName());

        return new RegisterResponseDto("Register Successfully", userDto, token);
    }
}
