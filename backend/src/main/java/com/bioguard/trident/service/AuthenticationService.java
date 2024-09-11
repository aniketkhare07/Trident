package com.bioguard.trident.service;

import com.bioguard.trident.dto.JwtDto.JwtAuthenticationRequest;
import com.bioguard.trident.dto.JwtDto.JwtAuthenticationResponse;
import com.bioguard.trident.dto.JwtDto.RefreshTokenRequest;
import com.bioguard.trident.dto.UserDto;
import com.bioguard.trident.entity.User;
import com.bioguard.trident.exception.TridentException;

public interface AuthenticationService {
    JwtAuthenticationResponse login(JwtAuthenticationRequest request) throws TridentException;
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest request) throws TridentException;
}
