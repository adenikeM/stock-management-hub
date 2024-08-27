package com.springboot.stockmanagementhub.controller;

import com.springboot.stockmanagementhub.model.dto.JwtAuthenticationResponse;
import com.springboot.stockmanagementhub.model.dto.SignInRequest;
import com.springboot.stockmanagementhub.model.dto.SignUpRequest;
import com.springboot.stockmanagementhub.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
@CrossOrigin(methods = {POST, GET, PATCH, PUT}, origins = "localhost:8080")
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public JwtAuthenticationResponse signup(@Valid @RequestBody SignUpRequest request){
        log.info("login request {}", request);
        return authenticationService.signup(request);
    }
    @PostMapping("/signin")
    public JwtAuthenticationResponse signin(@RequestBody SignInRequest request){
        return authenticationService.signin(request);
    }

}