package com.maliksalimov.financetrackerapi.repository;

import com.maliksalimov.financetrackerapi.entity.ExchangeRate;
import com.maliksalimov.financetrackerapi.entity.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    List<ExchangeRate> findByFromCurrencyAndToCurrency(Currency fromCurrency, Currency toCurrency);
    List<ExchangeRate> findByFromCurrencyAndToCurrencyOrderByDateDesc(Currency fromCurrency, Currency toCurrency);

    Optional<ExchangeRate> findTopByFromCurrencyAndToCurrencyOrderByDateDesc(Currency fromCurrency, Currency toCurrency);

    @Query("SELECT er FROM ExchangeRate er WHERE er.fromCurrency = :fromCurrency AND er.toCurrency = :toCurrency " +
            "AND er.date = (SELECT MAX(er2.date) FROM ExchangeRate er2 WHERE er2.fromCurrency = :fromCurrency AND er2.toCurrency = :toCurrency)")
    Optional<ExchangeRate> findLatestRateByFromAndToCurrency(@Param("fromCurrency") Currency fromCurrency, @Param("toCurrency") Currency toCurrency);

    Optional<ExchangeRate> findByFromCurrencyAndToCurrencyAndDate(Currency fromCurrency, Currency toCurrency, LocalDate date);
    List<ExchangeRate> findByFromCurrencyAndToCurrencyAndDateBetween(Currency fromCurrency, Currency toCurrency, LocalDate startDate, LocalDate endDate);

    @Query("SELECT er FROM ExchangeRate er WHERE er.fromCurrency = :fromCurrency AND er.toCurrency = :toCurrency " +
            "AND er.date <= :date ORDER BY er.date DESC LIMIT 1")
    Optional<ExchangeRate> findLatestRateOnOrBeforeDate(@Param("fromCurrency") Currency fromCurrency,
                                                        @Param("toCurrency") Currency toCurrency,
                                                        @Param("date") LocalDate date);

    List<ExchangeRate> findByFromCurrency(Currency fromCurrency);
    List<ExchangeRate> findByToCurrency(Currency toCurrency);
    List<ExchangeRate> findByFromCurrencyOrToCurrency(Currency fromCurrency, Currency toCurrency);

    List<ExchangeRate> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<ExchangeRate> findByDateGreaterThanEqual(LocalDate date);
    List<ExchangeRate> findByDateLessThanEqual(LocalDate date);

    List<ExchangeRate> findByFromCurrencyAndToCurrencyAndRateBetween(Currency fromCurrency, Currency toCurrency, BigDecimal minRate, BigDecimal maxRate);
    List<ExchangeRate> findByFromCurrencyAndToCurrencyAndRateGreaterThan(Currency fromCurrency, Currency toCurrency, BigDecimal rate);
    List<ExchangeRate> findByFromCurrencyAndToCurrencyAndRateLessThan(Currency fromCurrency, Currency toCurrency, BigDecimal rate);

    @Query("SELECT er.fromCurrency, er.toCurrency, COUNT(er) FROM ExchangeRate er GROUP BY er.fromCurrency, er.toCurrency")
    List<Object[]> getExchangeRateCountByCurrencyPair();

    @Query("SELECT er.fromCurrency, er.toCurrency, MIN(er.rate), MAX(er.rate), AVG(er.rate) " +
            "FROM ExchangeRate er WHERE er.date BETWEEN :startDate AND :endDate " +
            "GROUP BY er.fromCurrency, er.toCurrency")
    List<Object[]> getExchangeRateStatisticsByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT DISTINCT er.fromCurrency, er.toCurrency FROM ExchangeRate er ORDER BY er.fromCurrency, er.toCurrency")
    List<Object[]> findAllCurrencyPairs();

    @Query("SELECT er FROM ExchangeRate er WHERE er.fromCurrency = :fromCurrency AND er.toCurrency = :toCurrency " +
            "AND er.date BETWEEN :startDate AND :endDate ORDER BY er.date ASC")
    List<ExchangeRate> findHistoricalRates(@Param("fromCurrency") Currency fromCurrency,
                                           @Param("toCurrency") Currency toCurrency,
                                           @Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    @Query("SELECT er1.date, er1.rate, er2.rate, " +
            "((er1.rate - er2.rate) / er2.rate * 100) as percentChange " +
            "FROM ExchangeRate er1 " +
            "JOIN ExchangeRate er2 ON er1.fromCurrency = er2.fromCurrency AND er1.toCurrency = er2.toCurrency " +
            "WHERE er1.fromCurrency = :fromCurrency AND er1.toCurrency = :toCurrency " +
            "AND er1.date = :currentDate AND er2.date = :previousDate")
    List<Object[]> getRateChange(@Param("fromCurrency") Currency fromCurrency,
                                 @Param("toCurrency") Currency toCurrency,
                                 @Param("currentDate") LocalDate currentDate,
                                 @Param("previousDate") LocalDate previousDate);

    boolean existsByFromCurrencyAndToCurrencyAndDate(Currency fromCurrency, Currency toCurrency, LocalDate date);

    @Query("SELECT er FROM ExchangeRate er ORDER BY er.createdAt DESC")
    List<ExchangeRate> findRecentExchangeRates(Pageable pageable);

    Page<ExchangeRate> findByFromCurrencyAndToCurrencyOrderByDateDesc(Currency fromCurrency, Currency toCurrency, Pageable pageable);
    Page<ExchangeRate> findByDateBetweenOrderByDateDesc(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("DELETE FROM ExchangeRate er WHERE er.date < :cutoffDate")
    void deleteRatesOlderThan(@Param("cutoffDate") LocalDate cutoffDate);

    @Query("SELECT YEAR(er.date), MONTH(er.date), AVG(er.rate) " +
            "FROM ExchangeRate er WHERE er.fromCurrency = :fromCurrency AND er.toCurrency = :toCurrency " +
            "GROUP BY YEAR(er.date), MONTH(er.date) ORDER BY YEAR(er.date), MONTH(er.date)")
    List<Object[]> getMonthlyAverageRates(@Param("fromCurrency") Currency fromCurrency, @Param("toCurrency") Currency toCurrency);

}
