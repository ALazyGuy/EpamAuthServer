package com.epam.esm.service.impl;

import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.service.JwtService;
import com.epam.esm.util.JwtUtils;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String generateRefreshToken(UserEntity userEntity) {
        return JwtUtils.generate(userEntity.getUsername(), 5, "refresh");
    }

    //TODO Implement after spring security configuration
    @Override
    public String generateAccessToken() {
        return null;
    }
}
