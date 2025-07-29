package com.maliksalimov.financetrackerapi.dto.budget;

import com.maliksalimov.financetrackerapi.dto.category.CategoryDto;
import com.maliksalimov.financetrackerapi.entity.enums.BudgetPeriod;
import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {
    private Long id;
    private String name;
    private BigDecimal amount;
    private BigDecimal spentAmount;
    private Currency currency;
    private BudgetPeriod period;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer warningThreshold;
    private Boolean isActive;
    private String description;
    private CategoryDto category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Calculated fields
    private BigDecimal remainingAmount;
    private Double usagePercentage;
    private Boolean isExceeded;
    private Boolean isWarning;
}