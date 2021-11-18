package com.epam.esm.service;

import com.epam.esm.model.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    String generateRefreshToken(UserEntity userEntity);
    String generateAccessToken();
}
