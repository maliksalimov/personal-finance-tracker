package com.maliksalimov.financetrackerapi.dto.recurringtransaction;

import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import com.maliksalimov.financetrackerapi.entity.enums.RecurrenceInterval;
import com.maliksalimov.financetrackerapi.entity.enums.TransactionType;
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
public class RecurringTransactionSummaryDto {
    private Long id;
    private String name;
    private BigDecimal amount;
    private TransactionType type;
    private Currency currency;
    private RecurrenceInterval interval;
    private Integer intervalCount;
    private LocalDate nextDueDate;
}