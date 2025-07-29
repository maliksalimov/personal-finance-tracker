package com.maliksalimov.financetrackerapi.dto.transaction;

import com.maliksalimov.financetrackerapi.dto.account.AccountSummaryDto;
import com.maliksalimov.financetrackerapi.dto.category.CategoryDto;
import com.maliksalimov.financetrackerapi.dto.recurringtransaction.RecurringTransactionSummaryDto;
import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import com.maliksalimov.financetrackerapi.entity.enums.TransactionType;
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
public class TransactionDto {
    private Long id;
    private BigDecimal amount;
    private String description;
    private LocalDate transactionDate;
    private TransactionType type;
    private Currency currency;
    private BigDecimal exchangeRate;
    private BigDecimal amountInBaseCurrency;
    private String notes;
    private String referenceNumber;
    private AccountSummaryDto account;
    private CategoryDto category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}