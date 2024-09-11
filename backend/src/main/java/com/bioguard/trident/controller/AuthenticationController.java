package com.bioguard.trident.controller;

import com.bioguard.trident.dto.JwtDto.JwtAuthenticationRequest;
import com.bioguard.trident.dto.JwtDto.JwtAuthenticationResponse;
import com.bioguard.trident.dto.JwtDto.RefreshTokenRequest;
import com.bioguard.trident.dto.UserDto;
import com.bioguard.trident.entity.User;
import com.bioguard.trident.exception.TridentException;
import com.bioguard.trident.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody JwtAuthenticationRequest request) throws TridentException {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws TridentException {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
