package com.maliksalimov.financetrackerapi.repository;

import com.maliksalimov.financetrackerapi.entity.Account;
import com.maliksalimov.financetrackerapi.entity.User;
import com.maliksalimov.financetrackerapi.entity.enums.AccountType;
import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
    List<Account> findByUserAndIsActiveIsTrue(User user);
    List<Account> findByUserIdAndIsActive(Long userId, Boolean isActive);

    List<Account> findByUserAndType(User user, AccountType type);
    List<Account> findByUserAndCurrency(User user, Currency currency);
    List<Account> findByUserAndTypeAndCurrency(User user, AccountType type, Currency currency);

    List<Account> findByUserAndBalanceGreaterThan(User user, BigDecimal balance);
    List<Account> findByUserAndBalanceLessThan(User user, BigDecimal balance);
    List<Account> findByUserAndBalanceBetween(User user, BigDecimal balanceFrom, BigDecimal balanceTo);

    List<Account> findByUserAndNameContainingIgnoreCase(User user, String name);
    Optional<Account> findByUserAndName(User user, String name);


    @Query("SELECT a FROM Account a WHERE a.user = :user AND a.isActive = true ORDER BY a.balance DESC")
    List<Account> findActiveAccountsByUserOrderByBalanceDesc(@Param("user") User user);

    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.user = :user AND a.isActive = true AND a.currency = :currency")
    BigDecimal getTotalBalanceByUserAndCurrency(@Param("user") User user, @Param("currency") Currency currency);

    @Query("SELECT a.currency, SUM(a.balance) FROM Account a WHERE a.user = :user AND a.isActive = true GROUP BY a.currency")
    List<Object[]> getTotalBalanceByUserGroupedByCurrency(@Param("user") User user);

    @Query("SELECT a.type, COUNT(a) FROM Account a WHERE a.user = :user AND a.isActive = true GROUP BY a.type")
    List<Object[]> getAccountCountByTypeForUser(@Param("user") User user);

    @Query("SELECT a FROM Account a WHERE a.user = :user AND a.createdAt >= :date ORDER BY a.createdAt DESC")
    List<Account> findRecentAccountsByUser(@Param("user") User user, @Param("date") LocalDateTime date);

    Page<Account> findByUserAndIsActiveTrue(User user, Pageable pageable);
    Page<Account> findByUserAndTypeAndIsActiveTrue(User user, AccountType type, Pageable pageable);


}
