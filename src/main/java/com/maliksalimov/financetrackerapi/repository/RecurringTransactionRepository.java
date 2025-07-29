package com.maliksalimov.financetrackerapi.repository;

import com.maliksalimov.financetrackerapi.entity.Account;
import com.maliksalimov.financetrackerapi.entity.Category;
import com.maliksalimov.financetrackerapi.entity.RecurringTransaction;
import com.maliksalimov.financetrackerapi.entity.User;
import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import com.maliksalimov.financetrackerapi.entity.enums.RecurrenceInterval;
import com.maliksalimov.financetrackerapi.entity.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {

    List<RecurringTransaction> findByUser(User user);
    List<RecurringTransaction> findByUserAndIsActiveTrue(User user);
    List<RecurringTransaction> findByUserIdAndIsActiveTrue(Long userId);

    List<RecurringTransaction> findByAccount(Account account);
    List<RecurringTransaction> findByAccountAndIsActiveTrue(Account account);

    List<RecurringTransaction> findByUserAndCategory(User user, Category category);
    List<RecurringTransaction> findByUserAndCategoryAndIsActiveTrue(User user, Category category);

    List<RecurringTransaction> findByUserAndType(User user, TransactionType type);
    List<RecurringTransaction> findByUserAndTypeAndIsActiveTrue(User user, TransactionType type);

    List<RecurringTransaction> findByUserAndInterval(User user, RecurrenceInterval interval);
    List<RecurringTransaction> findByUserAndIntervalAndIsActiveTrue(User user, RecurrenceInterval interval);

    List<RecurringTransaction> findByUserAndCurrency(User user, Currency currency);

    List<RecurringTransaction> findByUserAndNextDueDateLessThanEqual(User user, LocalDate dueDate);
    List<RecurringTransaction> findByUserAndNextDueDateBetween(User user, LocalDate startDate, LocalDate endDate);

    @Query("SELECT rt FROM RecurringTransaction rt WHERE rt.user = :user AND rt.isActive = true AND rt.nextDueDate <= :dueDate")
    List<RecurringTransaction> findDueRecurringTransactionsByUser(@Param("user") User user, @Param("dueDate") LocalDate dueDate);

    List<RecurringTransaction> findByUserAndAutoCreateTrue(User user);
    List<RecurringTransaction> findByUserAndAutoCreateTrueAndIsActiveTrue(User user);

    @Query("SELECT rt FROM RecurringTransaction rt WHERE rt.user = :user AND rt.isActive = true AND rt.autoCreate = true AND rt.nextDueDate <= :dueDate")
    List<RecurringTransaction> findAutoCreateDueRecurringTransactionsByUser(@Param("user") User user, @Param("dueDate") LocalDate dueDate);

    List<RecurringTransaction> findByUserAndNotificationEnabledTrue(User user);

    @Query("SELECT rt FROM RecurringTransaction rt WHERE rt.user = :user AND rt.isActive = true AND rt.notificationEnabled = true " +
            "AND rt.nextDueDate <= :notificationDate")
    List<RecurringTransaction> findRecurringTransactionsForNotification(@Param("user") User user, @Param("notificationDate") LocalDate notificationDate);

    List<RecurringTransaction> findByUserAndNameContainingIgnoreCase(User user, String name);
    Optional<RecurringTransaction> findByUserAndName(User user, String name);

    List<RecurringTransaction> findByUserAndAmountBetween(User user, BigDecimal minAmount, BigDecimal maxAmount);
    List<RecurringTransaction> findByUserAndAmountGreaterThan(User user, BigDecimal amount);
    List<RecurringTransaction> findByUserAndAmountLessThan(User user, BigDecimal amount);

    List<RecurringTransaction> findByUserAndStartDateLessThanEqualAndEndDateGreaterThanEqual(User user, LocalDate endDate, LocalDate startDate);

    @Query("SELECT rt FROM RecurringTransaction rt WHERE rt.user = :user AND rt.isActive = true " +
            "AND (:currentDate BETWEEN rt.startDate AND rt.endDate OR rt.endDate IS NULL)")
    List<RecurringTransaction> findActiveRecurringTransactionsForUserOnDate(@Param("user") User user, @Param("currentDate") LocalDate currentDate);

    @Query("SELECT rt.interval, COUNT(rt) FROM RecurringTransaction rt WHERE rt.user = :user AND rt.isActive = true GROUP BY rt.interval")
    List<Object[]> getRecurringTransactionCountByIntervalForUser(@Param("user") User user);

    @Query("SELECT rt.type, COUNT(rt) FROM RecurringTransaction rt WHERE rt.user = :user AND rt.isActive = true GROUP BY rt.type")
    List<Object[]> getRecurringTransactionCountByTypeForUser(@Param("user") User user);

    @Query("SELECT rt.currency, SUM(rt.amount) FROM RecurringTransaction rt WHERE rt.user = :user AND rt.isActive = true GROUP BY rt.currency")
    List<Object[]> getTotalRecurringAmountByCurrencyForUser(@Param("user") User user);

    @Query("SELECT rt.category, SUM(rt.amount) FROM RecurringTransaction rt WHERE rt.user = :user AND rt.isActive = true AND rt.type = :type GROUP BY rt.category ORDER BY SUM(rt.amount) DESC")
    List<Object[]> getTotalRecurringAmountByCategoryForUserAndType(@Param("user") User user, @Param("type") TransactionType type);

    @Query("SELECT rt FROM RecurringTransaction rt WHERE rt.user = :user AND rt.isActive = true " +
            "AND (:type IS NULL OR rt.type = :type) " +
            "AND (:category IS NULL OR rt.category = :category) " +
            "AND (:interval IS NULL OR rt.interval = :interval) " +
            "AND (:currency IS NULL OR rt.currency = :currency) " +
            "AND (:startDate IS NULL OR rt.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR rt.endDate <= :endDate OR rt.endDate IS NULL) " +
            "ORDER BY rt.nextDueDate ASC")
    Page<RecurringTransaction> findRecurringTransactionsWithFilters(@Param("user") User user,
                                                                    @Param("type") TransactionType type,
                                                                    @Param("category") Category category,
                                                                    @Param("interval") RecurrenceInterval interval,
                                                                    @Param("currency") Currency currency,
                                                                    @Param("startDate") LocalDate startDate,
                                                                    @Param("endDate") LocalDate endDate,
                                                                    Pageable pageable);

    @Query("SELECT rt FROM RecurringTransaction rt WHERE rt.user = :user AND rt.isActive = true ORDER BY rt.createdAt DESC")
    List<RecurringTransaction> findRecentRecurringTransactionsByUser(@Param("user") User user, Pageable pageable);

    Page<RecurringTransaction> findByUserAndIsActiveTrue(User user, Pageable pageable);
    Page<RecurringTransaction> findByUserAndTypeAndIsActiveTrue(User user, TransactionType type, Pageable pageable);
    Page<RecurringTransaction> findByUserAndIntervalAndIsActiveTrue(User user, RecurrenceInterval interval, Pageable pageable);

    @Query("SELECT rt FROM RecurringTransaction rt WHERE rt.user = :user AND rt.isActive = true " +
            "AND rt.nextDueDate BETWEEN :startDate AND :endDate ORDER BY rt.nextDueDate ASC")
    List<RecurringTransaction> findUpcomingRecurringTransactionsByUser(@Param("user") User user,
                                                                       @Param("startDate") LocalDate startDate,
                                                                       @Param("endDate") LocalDate endDate);
}