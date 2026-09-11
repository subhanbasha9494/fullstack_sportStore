package com.selfpreparation.sportstores.controller;

import com.selfpreparation.sportstores.dto.LoginRequestDto;
import com.selfpreparation.sportstores.dto.LoginResponseDto;
import com.selfpreparation.sportstores.dto.RegisterRequestDto;
import com.selfpreparation.sportstores.entity.Customer;
import com.selfpreparation.sportstores.repository.CustomerRepository;
import com.selfpreparation.sportstores.service.ILoginService;
import com.selfpreparation.sportstores.service.IRegisterService;
import com.selfpreparation.sportstores.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    //private final AuthenticationManager authenticationManager;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CustomerRepository userRepository;
    @Autowired
    IRegisterService registerService;

    @Autowired
    ILoginService loginService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto request){
        return ResponseEntity.ok(registerService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request){
        return ResponseEntity.ok(loginService.login(request));
    }

}
