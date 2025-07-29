package com.maliksalimov.financetrackerapi.dto.account;

import com.maliksalimov.financetrackerapi.entity.enums.AccountType;
import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @NotBlank(message = "Account name is required")
    @Size(min = 2, max = 100, message = "Account name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Account type is required")
    private AccountType type;

    @DecimalMin(value = "0.0", message = "Initial balance cannot be negative")
    private BigDecimal initialBalance = BigDecimal.ZERO;

    @NotNull(message = "Currency is required")
    private Currency currency;

    private Boolean isActive = true;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}