package com.bioguard.trident.service.implementation;

import com.bioguard.trident.dto.JwtDto.JwtAuthenticationRequest;
import com.bioguard.trident.dto.JwtDto.JwtAuthenticationResponse;
import com.bioguard.trident.dto.JwtDto.RefreshTokenRequest;
import com.bioguard.trident.dto.UserDto;
import com.bioguard.trident.entity.Role;
import com.bioguard.trident.entity.User;
import com.bioguard.trident.exception.TridentException;
import com.bioguard.trident.repository.UserRepository;
import com.bioguard.trident.service.AuthenticationService;
import com.bioguard.trident.service.JwtService;
import com.bioguard.trident.utility.Convertor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public User signUp(UserDto userDto) {

        Optional<UserDetails> optional = userRepository.findByUsername(userDto.getUsername());
        if(optional.isPresent()) {
            System.out.println("User Not Found");
            return null;
        }
        else {
            User user = new User();
            user.setName(userDto.getName());
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setContact(userDto.getContact());
            user.setIsActive(true);
            user.setRole(Role.USER);

            return userRepository.save(user);
        }
    }

    public JwtAuthenticationResponse login(JwtAuthenticationRequest request) throws TridentException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword()));

        var userDetails = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new TridentException("Invalid Username or password"));
        User user = new User();
        if(userDetails instanceof User) {
            user = (User) userDetails;
        }
        var token = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), userDetails);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(token);
        response.setRefreshToken(refreshToken);
        response.setUserDto(Convertor.convertToUserDto(user));

        return response;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request) throws TridentException {
        String username = jwtService.extractUsername(request.getToken());
        User user = (User) userRepository.findByUsername(username).orElseThrow(() -> new TridentException("User Not Found"));

        if(jwtService.isTokenValid(request.getToken(),user)) {
            var token = jwtService.generateToken(user);

            JwtAuthenticationResponse response = new JwtAuthenticationResponse();

            response.setToken(token);
            response.setRefreshToken(request.getToken());

            return response;
        }
        return null;
    }
}
