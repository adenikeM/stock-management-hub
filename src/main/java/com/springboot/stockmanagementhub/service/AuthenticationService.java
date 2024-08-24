package com.springboot.stockmanagementhub.service;

import com.springboot.stockmanagementhub.model.Role;
import com.springboot.stockmanagementhub.model.User;
import com.springboot.stockmanagementhub.model.dto.JwtAuthenticationResponse;
import com.springboot.stockmanagementhub.model.dto.SignInRequest;
import com.springboot.stockmanagementhub.model.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signup(SignUpRequest request){
        Role role = roleService.getRoleByTitle(request.getRoleTitle());
        if(role == null) {
            throw new IllegalArgumentException("Invalid role!");
        }
        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .userRole(role)
                .build();
        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse signin(SignInRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userService.findUserByEmail(request.getEmail())
                              .orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
