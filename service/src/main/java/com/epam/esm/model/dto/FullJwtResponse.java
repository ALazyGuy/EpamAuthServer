package com.epam.esm.model.dto;

import lombok.Data;

@Data
public class FullJwtResponse {
    private String refresh;
    private String access;
}
