package com.anymind.bitcoin.wallet.app.exception;

import com.anymind.bitcoin.wallet.app.model.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;
import java.util.Date;

@ControllerAdvice
@Slf4j
public class ExceptionResolver {

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BitcoinBaseException.class)
    public RestResponse<Object> resolveErrors(Exception e) {
        log.error("resolved exception: {}", e.getMessage());
        RestResponse<Object> response = new RestResponse<>();
        response.setMessage(e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setTimestamp(new Timestamp(new Date().getTime()));
        return response;
    }
}
