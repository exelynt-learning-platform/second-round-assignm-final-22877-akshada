package com.ecommerce_backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.repository.UserRepository;
import com.ecommerce_backend.security.JwtUtil;

@Service
public class AuthService {

 private final UserRepository repo;
 private final PasswordEncoder encoder;
 private final JwtUtil jwt;

 public AuthService(UserRepository repo, PasswordEncoder encoder, JwtUtil jwt){
  this.repo = repo;
  this.encoder = encoder;
  this.jwt = jwt;
 }

 public String register(User user){
  user.setPassword(encoder.encode(user.getPassword()));
  user.setRole("ROLE_USER");
  repo.save(user);
  return "User Registered";
 }

 public String login(String email, String password){
  User user = repo.findByEmail(email).orElseThrow();
  if(!encoder.matches(password, user.getPassword())){
   throw new RuntimeException("Invalid Password");
  }
  return jwt.generateToken(email);
 }
}
