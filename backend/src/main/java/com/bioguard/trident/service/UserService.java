package com.bioguard.trident.service;

import com.bioguard.trident.dto.BatchRequestDto;
import com.bioguard.trident.dto.CenterRequestDto;
import com.bioguard.trident.exception.TridentException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService ();
    CenterRequestDto requestCenter (CenterRequestDto centerRequestDto) throws TridentException;
    BatchRequestDto requestBatch (BatchRequestDto batchRequestDto) throws TridentException;
}
