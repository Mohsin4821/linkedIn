package com.my.linkedInProject.userService.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email, password;
}
