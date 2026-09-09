package com.selfpreparation.sportstores.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDto {

    private Long userId;
    private String name;
    private String email;
    private String mobileNumber;
}
