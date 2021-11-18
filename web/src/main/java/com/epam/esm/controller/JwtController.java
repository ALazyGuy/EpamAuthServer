package com.epam.esm.controller;

import com.epam.esm.model.dto.FullJwtResponse;
import com.epam.esm.model.dto.JwtResponse;
import com.epam.esm.model.dto.UserAuthRequest;
import com.epam.esm.service.JwtService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.POST})
@RequestMapping("/v3/auth")
public class JwtController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public JwtController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FullJwtResponse> authenticate(@Valid @RequestBody UserAuthRequest userAuthRequest){
        return ResponseEntity.ok(userService.authenticate(userAuthRequest));
    }

    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponse> refresh(){
        String token = jwtService.generateAccessToken();
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
