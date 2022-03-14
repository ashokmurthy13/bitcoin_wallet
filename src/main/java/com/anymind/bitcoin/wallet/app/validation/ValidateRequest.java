package com.anymind.bitcoin.wallet.app.validation;

import com.anymind.bitcoin.wallet.app.exception.BitcoinBaseException;
import com.anymind.bitcoin.wallet.app.model.Bitcoin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ValidateRequest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void validateDateRange(String startDateTime, String endDateTime) {
        LocalDateTime start = LocalDateTime.parse(startDateTime, FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endDateTime, FORMATTER);
        if (start.isAfter(end)) {
            throw new BitcoinBaseException("start date cannot be greater than end date");
        }
    }

    public void validateRequest(Bitcoin bitcoin) {
        if (bitcoin == null) {
            throw new BitcoinBaseException("Amount and Datetime cannot be null or empty");
        } else if (bitcoin.getAmount() == null) {
            throw new BitcoinBaseException("Amount cannot be null or empty");
        } else if (bitcoin.getDateTime() == null) {
            throw new BitcoinBaseException("Datetime cannot be null or empty");
        }
    }
}
