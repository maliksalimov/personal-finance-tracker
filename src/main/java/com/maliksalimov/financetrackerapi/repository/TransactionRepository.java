package com.maliksalimov.financetrackerapi.repository;

import com.maliksalimov.financetrackerapi.entity.Account;
import com.maliksalimov.financetrackerapi.entity.Category;
import com.maliksalimov.financetrackerapi.entity.Transaction;
import com.maliksalimov.financetrackerapi.entity.User;
import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import com.maliksalimov.financetrackerapi.entity.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    List<Transaction> findByAccount(Account account);
    List<Transaction> findByAccountOrderByTransactionDateDesc(Account account);

    @Query("SELECT t FROM Transaction t WHERE t.account.user = :user")
    List<Transaction> findByUser(@Param("user") User user);

    @Query("SELECT t FROM Transaction t WHERE t.account.user = :user ORDER BY t.transactionDate DESC")
    List<Transaction> findByUserOrderByTransactionDateDesc(@Param("user") User user);

    List<Transaction> findByAccountAndType(Account account, TransactionType type);

    @Query("SELECT t FROM Transaction t WHERE t.account.user = :user AND t.type = :type")
    List<Transaction> findByUserAndType(@Param("user") User user, @Param("type") TransactionType type);

    List<Transaction> findByAccountAndTransactionDateBetween(Account account, LocalDate startDate, LocalDate endDate);

    @Query("SELECT t FROM Transaction t WHERE t.account.user = :user AND t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findByUserAndTransactionDateBetween(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Transaction> findByAccountAndAmountBetween(Account account, BigDecimal minAmount, BigDecimal maxAmount);
    List<Transaction> findByAccountAndAmountGreaterThan(Account account, BigDecimal amount);
    List<Transaction> findByAccountAndAmountLessThan(Account account, BigDecimal amount);

    List<Transaction> findByCategory(Category category);
    List<Transaction> findByAccountAndCategory(Account account, Category category);

    @Query("SELECT t FROM Transaction t WHERE t.account.user = :user AND t.category = :category")
    List<Transaction> findByUserAndCategory(@Param("user") User user, @Param("category") Category category);

    List<Transaction> findByAccountAndCurrency(Account account, Currency currency);

    @Query("SELECT t FROM Transaction t WHERE t.account.user = :user AND t.currency = :currency")
    List<Transaction> findByUserAndCurrency(@Param("user") User user, @Param("currency") Currency currency);

    Optional<Transaction> findByReferenceNumber(String referenceNumber);
    List<Transaction> findByAccountAndReferenceNumberContaining(Account account, String referenceNumber);

    List<Transaction> findByAccountAndDescriptionContainingIgnoreCase(Account account, String description);

    @Query("SELECT t FROM Transaction t WHERE t.account.user = :user AND LOWER(t.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    List<Transaction> findByUserAndDescriptionContaining(@Param("user") User user, @Param("description") String description);

    @Query("SELECT t FROM Transaction t WHERE t.account.user = :user " +
            "AND (:type IS NULL OR t.type = :type) " +
            "AND (:category IS NULL OR t.category = :category) " +
            "AND (:currency IS NULL OR t.currency = :currency) " +
            "AND (:startDate IS NULL OR t.transactionDate >= :startDate) " +
            "AND (:endDate IS NULL OR t.transactionDate <= :endDate) " +
            "AND (:minAmount IS NULL OR t.amount >= :minAmount) " +
            "AND (:maxAmount IS NULL OR t.amount <= :maxAmount) " +
            "ORDER BY t.transactionDate DESC")
    Page<Transaction> findTransactionsWithFilters(@Param("user") User user,
                                                  @Param("type") TransactionType type,
                                                  @Param("category") Category category,
                                                  @Param("currency") Currency currency,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate,
                                                  @Param("minAmount") BigDecimal minAmount,
                                                  @Param("maxAmount") BigDecimal maxAmount,
                                                  Pageable pageable);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.account.user = :user AND t.type = :type AND t.transactionDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalAmountByUserAndTypeAndDateRange(@Param("user") User user, @Param("type") TransactionType type, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t WHERE t.account.user = :user AND t.type = :type AND t.transactionDate BETWEEN :startDate AND :endDate GROUP BY t.category ORDER BY SUM(t.amount) DESC")
    List<Object[]> getTotalAmountByUserAndTypeAndDateRangeGroupedByCategory(@Param("user") User user, @Param("type") TransactionType type, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT MONTH(t.transactionDate), YEAR(t.transactionDate), SUM(t.amount) FROM Transaction t WHERE t.account.user = :user AND t.type = :type GROUP BY YEAR(t.transactionDate), MONTH(t.transactionDate) ORDER BY YEAR(t.transactionDate), MONTH(t.transactionDate)")
    List<Object[]> getMonthlyTotalsByUserAndType(@Param("user") User user, @Param("type") TransactionType type);

    @Query("SELECT t.currency, SUM(t.amount) FROM Transaction t WHERE t.account.user = :user AND t.type = :type AND t.transactionDate BETWEEN :startDate AND :endDate GROUP BY t.currency")
    List<Object[]> getTotalAmountByUserAndTypeAndDateRangeGroupedByCurrency(@Param("user") User user, @Param("type") TransactionType type, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT t FROM Transaction t WHERE t.account.user = :user ORDER BY t.createdAt DESC")
    List<Transaction> findRecentTransactionsByUser(@Param("user") User user, Pageable pageable);

    Page<Transaction> findByAccountOrderByTransactionDateDesc(Account account, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.account.user = :user ORDER BY t.transactionDate DESC")
    Page<Transaction> findByUserOrderByTransactionDateDesc(@Param("user") User user, Pageable pageable);
}
