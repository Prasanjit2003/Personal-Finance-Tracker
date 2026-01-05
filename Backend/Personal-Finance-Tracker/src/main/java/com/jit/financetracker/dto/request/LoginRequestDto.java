package com.jit.financetracker.dto.request;

import lombok.*;

@Data
@NoArgsConstructor   
@AllArgsConstructor
public class LoginRequestDto {

    private String email;
    private String password;
}
