package com.anymind.bitcoin.wallet.app.service;

import com.anymind.bitcoin.wallet.app.exception.BitcoinBaseException;
import com.anymind.bitcoin.wallet.app.model.Bitcoin;
import com.anymind.bitcoin.wallet.app.model.BitcoinHistoryResponse;
import com.anymind.bitcoin.wallet.app.model.RestResponse;
import com.anymind.bitcoin.wallet.app.repository.BitcoinRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BitcoinService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final @NonNull BitcoinRepository bitcoinRepository;

    public RestResponse<Bitcoin> saveRecord(Bitcoin bitcoin) {
        Bitcoin savedBitcoin;
        BigDecimal currentBalance = bitcoinRepository.findCurrentBalance();
        if (currentBalance == null) {
            bitcoin.setBalance(bitcoin.getAmount());
        } else {
            bitcoin.setBalance(bitcoin.getAmount().add(currentBalance));
        }
        savedBitcoin = bitcoinRepository.save(bitcoin);
        return new RestResponse<>(savedBitcoin, HttpStatus.OK);
    }

    public RestResponse<List<BitcoinHistoryResponse>> getHistory(String startDateTime, String endDateTime) {
        List<Bitcoin> history = bitcoinRepository.findBetweenDates(startDateTime, endDateTime);
        if (history.isEmpty()) {
            throw new BitcoinBaseException("No Records found");
        }
        return new RestResponse<>(findHourlyDate(startDateTime, endDateTime, history), HttpStatus.OK);
    }

    private List<BitcoinHistoryResponse> findHourlyDate(String startDateTime, String endDateTime, List<Bitcoin> history) {
        List<BitcoinHistoryResponse> response = new ArrayList<>();
        LocalDateTime startDate = LocalDateTime.parse(startDateTime, FORMATTER);
        LocalDateTime endDate = LocalDateTime.parse(endDateTime, FORMATTER);
        LocalDateTime currentDate = startDate;
        long numOfHoursBetween = ChronoUnit.HOURS.between(startDate, endDate);
        while (numOfHoursBetween > 0) {
            if (currentDate.getMinute() != 0) {
                int minRem = 60 - currentDate.getMinute();
                currentDate = currentDate.plusMinutes(minRem);
            } else {
                currentDate = currentDate.plusHours(1);
            }
            LocalDateTime finalCurrentDate = currentDate;
            history.forEach(date -> {
                if (date.getDateTime().getHours() == finalCurrentDate.getHour()) {
                    BitcoinHistoryResponse balanceAtHour = new BitcoinHistoryResponse();
                    balanceAtHour.setAmount(date.getBalance());
                    balanceAtHour.setDateTime(date.getDateTime());
                    response.add(balanceAtHour);
                }
            });
            numOfHoursBetween--;
        }
        return response;
    }
}
