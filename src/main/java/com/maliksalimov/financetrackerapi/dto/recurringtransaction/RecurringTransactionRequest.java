package com.maliksalimov.financetrackerapi.dto.recurringtransaction;

import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import com.maliksalimov.financetrackerapi.entity.enums.RecurrenceInterval;
import com.maliksalimov.financetrackerapi.entity.enums.TransactionType;
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
public class RecurringTransactionRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @NotNull(message = "Currency is required")
    private Currency currency;

    @NotNull(message = "Recurring interval is required")
    private RecurrenceInterval interval;

    @NotNull(message = "Interval count is required")
    @Min(value = 1, message = "Interval count must be at least 1")
    private Integer intervalCount;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate nextDueDate;

    private Boolean isActive;

    private Boolean autoCreate;

    private Boolean notificationEnabled;

    @Min(value = 0, message = "Notification days before must be at least 0")
    private Integer notificationDaysBefore;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    @NotNull(message = "Account ID is required")
    private Long accountId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}