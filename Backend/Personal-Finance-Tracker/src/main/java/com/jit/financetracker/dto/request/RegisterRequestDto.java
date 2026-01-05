package com.jit.financetracker.dto.request;

import lombok.*;

@Data
@NoArgsConstructor  
@AllArgsConstructor
public class RegisterRequestDto {

    private String email;
    private String password;
}
