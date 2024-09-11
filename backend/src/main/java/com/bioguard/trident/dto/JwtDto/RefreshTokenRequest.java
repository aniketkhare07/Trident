package com.bioguard.trident.dto.JwtDto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
}
