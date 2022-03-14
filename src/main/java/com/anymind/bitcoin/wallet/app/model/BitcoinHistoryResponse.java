package com.anymind.bitcoin.wallet.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BitcoinHistoryResponse {

    private BigDecimal amount;
    private Timestamp dateTime;
}
