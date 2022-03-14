package com.anymind.bitcoin.wallet.app.exception;

public class BitcoinBaseException extends RuntimeException {

    public BitcoinBaseException() {
        super();
    }

    public BitcoinBaseException(String message) {
        super(message);
    }

    public BitcoinBaseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
