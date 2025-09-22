package com.footlink.footlink.user.manage.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.footlink.footlink.user.manage.service.ManageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class ManageController {

	 private final ManageService manageService;
	
	 @GetMapping("/check-phone")
	 public Map<String, Boolean> checkPhone(@RequestParam String phone) {

		 boolean exists = manageService.checkPhoneDuplicate(phone);
		 return Map.of("exists", exists);
}

}
