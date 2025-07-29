package com.maliksalimov.financetrackerapi.dto.auth;

import com.maliksalimov.financetrackerapi.dto.user.UserSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private UserSummaryDto user;
    private boolean requiresTwoFactor;
}