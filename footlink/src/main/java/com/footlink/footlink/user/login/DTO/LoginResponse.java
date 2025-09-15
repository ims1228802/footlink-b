package com.footlink.footlink.user.login.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String name;
    // getter, setter, constructor
}