package com.maliksalimov.financetrackerapi.dto.budget;

import com.maliksalimov.financetrackerapi.entity.enums.BudgetPeriod;
import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetRequest {

    @NotBlank(message = "Budget name is required")
    @Size(min = 2, max = 100, message = "Budget name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Budget amount is required")
    @Positive(message = "Budget amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Currency is required")
    private Currency currency;

    @NotNull(message = "Budget period is required")
    private BudgetPeriod period;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    @Min(value = 1, message = "Warning threshold must be at least 1%")
    @Max(value = 100, message = "Warning threshold cannot exceed 100%")
    private Integer warningThreshold;

    private Boolean isActive;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}