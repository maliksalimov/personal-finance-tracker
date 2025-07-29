package com.maliksalimov.financetrackerapi.repository;

import com.maliksalimov.financetrackerapi.entity.Budget;
import com.maliksalimov.financetrackerapi.entity.Category;
import com.maliksalimov.financetrackerapi.entity.User;
import com.maliksalimov.financetrackerapi.entity.enums.BudgetPeriod;
import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUser(User user);
    List<Budget> findByUserAndIsActiveTrue(User user);
    List<Budget> findByUserIdAndIsActiveTrue(Long userId);

    List<Budget> findByUserAndCategory(User user, Category category);
    List<Budget> findByUserAndCategoryAndIsActiveTrue(User user, Category category);

    List<Budget> findByUserAndPeriod(User user, BudgetPeriod period);
    List<Budget> findByUserAndPeriodAndIsActiveTrue(User user, BudgetPeriod period);

    List<Budget> findByUserAndCurrency(User user, Currency currency);

    List<Budget> findByUserAndStartDateLessThanEqualAndEndDateGreaterThanEqual(User user, LocalDate endDate, LocalDate startDate);

    @Query("SELECT b FROM Budget b WHERE b.user = :user AND b.isActive = true AND :currentDate BETWEEN b.startDate AND b.endDate")
    List<Budget> findActiveBudgetsForUserOnDate(@Param("user") User user, @Param("currentDate") LocalDate currentDate);

    @Query("SELECT b FROM Budget b WHERE b.user = :user AND b.isActive = true AND b.spentAmount >= b.amount")
    List<Budget> findExceededBudgetsByUser(@Param("user") User user);

    @Query("SELECT b FROM Budget b WHERE b.user = :user AND b.isActive = true AND b.spentAmount >= (b.amount * b.warningThreshold)")
    List<Budget> findBudgetsNearLimitByUser(@Param("user") User user);

    @Query("SELECT b FROM Budget b WHERE b.user = :user AND b.isActive = true AND b.spentAmount < (b.amount * b.warningThreshold)")
    List<Budget> findBudgetsWithinLimitByUser(@Param("user") User user);

    List<Budget> findByUserAndNameContainingIgnoreCase(User user, String name);
    Optional<Budget> findByUserAndName(User user, String name);

    List<Budget> findByUserAndAmountBetween(User user, BigDecimal minAmount, BigDecimal maxAmount);
    List<Budget> findByUserAndAmountGreaterThan(User user, BigDecimal amount);
    List<Budget> findByUserAndAmountLessThan(User user, BigDecimal amount);

    @Query("SELECT b.period, COUNT(b) FROM Budget b WHERE b.user = :user AND b.isActive = true GROUP BY b.period")
    List<Object[]> getBudgetCountByPeriodForUser(@Param("user") User user);

    @Query("SELECT b.currency, SUM(b.amount) FROM Budget b WHERE b.user = :user AND b.isActive = true GROUP BY b.currency")
    List<Object[]> getTotalBudgetAmountByCurrencyForUser(@Param("user") User user);

    @Query("SELECT b.category, SUM(b.amount), SUM(b.spentAmount) FROM Budget b WHERE b.user = :user AND b.isActive = true GROUP BY b.category")
    List<Object[]> getBudgetSummaryByCategoryForUser(@Param("user") User user);

    @Query("SELECT AVG((b.spentAmount / b.amount) * 100) FROM Budget b WHERE b.user = :user AND b.isActive = true AND b.amount > 0")
    Double getAverageBudgetUsagePercentageForUser(@Param("user") User user);

    @Query("SELECT b FROM Budget b WHERE b.user = :user AND b.isActive = true ORDER BY (b.spentAmount / b.amount) DESC")
    List<Budget> findBudgetsOrderedByUsagePercentageDesc(@Param("user") User user);

    @Query("SELECT b FROM Budget b WHERE b.user = :user AND b.isActive = true ORDER BY b.createdAt DESC")
    List<Budget> findRecentBudgetsByUser(@Param("user") User user, Pageable pageable);

    Page<Budget> findByUserAndIsActiveTrue(User user, Pageable pageable);
    Page<Budget> findByUserAndPeriodAndIsActiveTrue(User user, BudgetPeriod period, Pageable pageable);

    @Query("SELECT b FROM Budget b WHERE b.user = :user AND b.category = :category AND b.isActive = true " +
            "AND ((:startDate BETWEEN b.startDate AND b.endDate) OR (:endDate BETWEEN b.startDate AND b.endDate) " +
            "OR (b.startDate BETWEEN :startDate AND :endDate) OR (b.endDate BETWEEN :startDate AND :endDate))")
    List<Budget> findOverlappingBudgets(@Param("user") User user, @Param("category") Category category,
                                        @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
