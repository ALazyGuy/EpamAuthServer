package com.epam.esm.service;

import com.epam.esm.model.dto.FullJwtResponse;
import com.epam.esm.model.dto.UserAuthRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    FullJwtResponse authenticate(UserAuthRequest userAuthRequest);
}
