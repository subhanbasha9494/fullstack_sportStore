package com.selfpreparation.sportstores.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PublicpathConfig {

    @Bean
    public List<String> publicPaths(){
        return List.of(
                "/api/products",
                "/api/contacts",
                "/api/auth",
                "/h2-console/**"
        );
    };
}
