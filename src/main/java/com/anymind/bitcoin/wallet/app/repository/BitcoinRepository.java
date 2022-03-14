package com.anymind.bitcoin.wallet.app.repository;

import com.anymind.bitcoin.wallet.app.model.Bitcoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BitcoinRepository extends JpaRepository<Bitcoin, Long> {

    @Query(value = "select * from bitcoin_tbl where date_time >= :startDateTime and date_time <= :endDateTime", nativeQuery = true)
    List<Bitcoin> findBetweenDates(String startDateTime, String endDateTime);

    @Query(value = "select balance from bitcoin_tbl order by id desc limit 1", nativeQuery = true)
    BigDecimal findCurrentBalance();
}
