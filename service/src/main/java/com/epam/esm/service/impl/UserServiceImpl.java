package com.epam.esm.service.impl;

import com.epam.esm.model.dto.FullJwtResponse;
import com.epam.esm.model.dto.UserAuthRequest;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.JwtService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public FullJwtResponse authenticate(UserAuthRequest userAuthRequest) {
        UserEntity user = userRepository.findByUsername(userAuthRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(userAuthRequest.getUsername()));

        if(passwordEncoder.matches(userAuthRequest.getPassword(), user.getPasswordHash())){
            throw new BadCredentialsException("Invalid password");
        }

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPasswordHash(),
                user.getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority(r.getRole()))
                        .collect(Collectors.toList())
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String refreshToken = jwtService.generateRefreshToken(user);
        String accessToken = jwtService.generateAccessToken();
        FullJwtResponse response = new FullJwtResponse();
        response.setRefresh(refreshToken);
        response.setAccess(accessToken);
        return response;
    }
}
