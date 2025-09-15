package com.footlink.footlink.user.login.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.footlink.footlink.user.login.DTO.LoginRequest;
import com.footlink.footlink.user.login.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

//    @GetMapping("/login") 
//    public LoginRequest loginProcess(
//            @PathVariable(value="email") String email,
//            @PathVariable(value="password") String password) {
//    	System.out.println("이메일 검증 :" + email );
//        Map<String, Object> resultMap = loginService.matchedUser(email, password);
//        boolean isMatched = (boolean) resultMap.get("isMatched");
//        LoginRequest userInfo = (LoginRequest) resultMap.get("userInfo");
//        
//        if (isMatched) {
//            return userInfo;
//        } else {
//            return null;
//        }
//    }
    
    @PostMapping("/login")
    public LoginRequest loginProcess(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println("이메일 검증: " + email);

        Map<String, Object> resultMap = loginService.matchedUser(email, password);
        boolean isMatched = (boolean) resultMap.get("isMatched");
        LoginRequest userInfo = (LoginRequest) resultMap.get("userInfo");
        System.out.println("DBINFO: " + userInfo);

        if (isMatched) {
            return userInfo;
        } else {
            return null;
        }
    }

    
}
