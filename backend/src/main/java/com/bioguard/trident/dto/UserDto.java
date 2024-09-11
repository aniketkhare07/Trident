package com.bioguard.trident.dto;

import com.bioguard.trident.entity.Center;
import com.bioguard.trident.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private long userid;
    private String name;
    @NotBlank(message = "Username is required")
    @NotEmpty(message = "Username is required")
    private String username;
    private String password;
    private String contact;
    private boolean isActive;

    private Set<CenterDto> centerDtos;

    private Role role;
}
