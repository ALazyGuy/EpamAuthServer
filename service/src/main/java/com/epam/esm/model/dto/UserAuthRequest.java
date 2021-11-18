package com.epam.esm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserAuthRequest {
    @JsonProperty(required = true)
    @Pattern(regexp = "(^(\\w)*$)", message = "Invalid username")
    private String username;
    @JsonProperty(required = true)
    @Pattern(regexp = "(^(\\w|_!\\.){6,20}$)", message = "Invalid Password")
    private String password;
}
