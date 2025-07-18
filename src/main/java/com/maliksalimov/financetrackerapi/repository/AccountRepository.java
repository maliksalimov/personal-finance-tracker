package com.maliksalimov.financetrackerapi.repository;

import com.maliksalimov.financetrackerapi.entity.Account;
import com.maliksalimov.financetrackerapi.entity.User;
import com.maliksalimov.financetrackerapi.entity.enums.AccountType;
import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.accessibility.AccessibleComponent;
import java.math.BigDecimal;
import java.util.List;

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


}
