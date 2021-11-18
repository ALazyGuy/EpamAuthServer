package com.epam.esm.service.impl;

import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.JwtService;
import com.epam.esm.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String generateRefreshToken(UserEntity userEntity) {
        return JwtUtils.generate(userEntity.getUsername(), 5, "refresh");
    }

    @Override
    public String generateAccessToken() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return JwtUtils.generate(username, 60, "access");
    }
}
