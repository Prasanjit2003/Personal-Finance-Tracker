package com.jit.financetracker.dto.request;

import lombok.*;

@Data
@NoArgsConstructor   
@AllArgsConstructor
public class OtpVerifyRequestDto {

    private String email;
    private String otp;
}
