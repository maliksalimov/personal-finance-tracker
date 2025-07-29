package com.maliksalimov.financetrackerapi.dto.user;

import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryDto {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Currency defaultCurrency;
    private Boolean twoFactorEnabled;
}