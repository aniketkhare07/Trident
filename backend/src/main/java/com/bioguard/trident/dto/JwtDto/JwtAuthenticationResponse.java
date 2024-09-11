package com.bioguard.trident.dto.JwtDto;

import com.bioguard.trident.dto.UserDto;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    private UserDto userDto;
}
