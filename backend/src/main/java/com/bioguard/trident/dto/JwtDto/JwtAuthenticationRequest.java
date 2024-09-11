package com.bioguard.trident.dto.JwtDto;

import lombok.Data;

@Data
public class JwtAuthenticationRequest {

    private String username;
    private String password;
}
