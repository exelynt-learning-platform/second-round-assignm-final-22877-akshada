package com.ecommerce_backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce_backend.dto.AuthRequest;
import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

 private final AuthService service;

 public AuthController(AuthService service){
  this.service = service;
 }

 @PostMapping("/register")
 public String register(@RequestBody User user){
	 System.out.println("Register request: " + user);
  return service.register(user);
 }

 @PostMapping("/login")
 public String login(@RequestBody AuthRequest request) {
  return service.login(request.getEmail(), request.getPassword());
 }
}
