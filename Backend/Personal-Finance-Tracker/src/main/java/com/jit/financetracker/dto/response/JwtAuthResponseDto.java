package com.jit.financetracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtAuthResponseDto {

    private String token;
    private String tokenType = "Bearer";
    
    public JwtAuthResponseDto(String token) {
        this.token = token;
    }
}
