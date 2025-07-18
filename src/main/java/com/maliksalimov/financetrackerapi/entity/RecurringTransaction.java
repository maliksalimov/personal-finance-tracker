package com.maliksalimov.financetrackerapi.entity;

import com.maliksalimov.financetrackerapi.entity.enums.RecurrenceInterval;
import com.maliksalimov.financetrackerapi.entity.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "recurring_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecurringTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Transaction name is required")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Transaction description is required")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence_interval", nullable = false)
    private RecurrenceInterval interval;

    @Column(name = "interval_count", nullable = false)
    private Integer intervalCount;

    @Column(name = "start_date", nullable = false)
    @NotBlank(message = "Start date is required")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "next_due_date")
    private LocalDate nextDueDate;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "auto_create")
    private Boolean autoCreate = true;

    @Column(name = "notification_enabled")
    private Boolean notificationEnabled = true;

    @Column(name = "notification_days_before")
    private Integer notificationDaysBefore = 1;

    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull(message = "Account is required")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Category is required")
    private Category category;

    @OneToMany(mappedBy = "recurringTransaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> generatedTransactions;
}
