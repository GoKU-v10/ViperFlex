package com.wizard.ViperFlex.controller;

import com.wizard.ViperFlex.dto.AuthResponse;
import com.wizard.ViperFlex.dto.LoginRequest;
import com.wizard.ViperFlex.dto.RegisterRequest;
import com.wizard.ViperFlex.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){

        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }
}
