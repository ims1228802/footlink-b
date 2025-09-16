package com.footlink.footlink.user.login.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String name;
}