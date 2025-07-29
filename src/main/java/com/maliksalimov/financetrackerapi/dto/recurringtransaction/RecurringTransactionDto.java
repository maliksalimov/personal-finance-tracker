package com.maliksalimov.financetrackerapi.dto.recurringtransaction;

import com.maliksalimov.financetrackerapi.dto.account.AccountSummaryDto;
import com.maliksalimov.financetrackerapi.dto.category.CategoryDto;
import com.maliksalimov.financetrackerapi.dto.transaction.TransactionSummaryDto;
import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import com.maliksalimov.financetrackerapi.entity.enums.RecurrenceInterval;
import com.maliksalimov.financetrackerapi.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecurringTransactionDto {
    private Long id;
    private String name;
    private BigDecimal amount;
    private String description;
    private TransactionType type;
    private Currency currency;
    private RecurrenceInterval interval;
    private Integer intervalCount;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate nextDueDate;
    private Boolean isActive;
    private Boolean autoCreate;
    private Boolean notificationEnabled;
    private Integer notificationDaysBefore;
    private String notes;
    private AccountSummaryDto account;
    private CategoryDto category;
    private List<TransactionSummaryDto> generatedTransactions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}