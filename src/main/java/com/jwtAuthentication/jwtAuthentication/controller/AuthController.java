package com.jwtAuthentication.jwtAuthentication.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jwtAuthentication.jwtAuthentication.exception.AppException;
import com.jwtAuthentication.jwtAuthentication.model.ApiResponse;
import com.jwtAuthentication.jwtAuthentication.model.JwtAuthenticationResponse;
import com.jwtAuthentication.jwtAuthentication.model.LoginRequest;
import com.jwtAuthentication.jwtAuthentication.model.Role;
import com.jwtAuthentication.jwtAuthentication.model.RoleName;
import com.jwtAuthentication.jwtAuthentication.model.SignUpRequest;
import com.jwtAuthentication.jwtAuthentication.model.User;
import com.jwtAuthentication.jwtAuthentication.repository.RoleRepository;
import com.jwtAuthentication.jwtAuthentication.repository.UserRepository;
import com.jwtAuthentication.jwtAuthentication.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	 @Autowired
	 UserRepository userRepository;
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
    JwtTokenProvider tokenProvider;
	
	 @Autowired
	 PasswordEncoder passwordEncoder;
	 
	 @Autowired
	 RoleRepository roleRepository;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		System.out.println("Sign In api called");
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(),loginRequest.getPassword());
		
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		 String jwt = tokenProvider.generateToken(authentication);
		
		System.out.println(jwt);
		
		 return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}
	
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(
        			signUpRequest.getName(), 
        			signUpRequest.getUsername(),
        			signUpRequest.getEmail(), 
        			signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
