package com.maliksalimov.financetrackerapi.dto.account;

import com.maliksalimov.financetrackerapi.entity.enums.AccountType;
import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String name;
    private AccountType type;
    private BigDecimal balance;
    private Currency currency;
    private Boolean isActive;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}