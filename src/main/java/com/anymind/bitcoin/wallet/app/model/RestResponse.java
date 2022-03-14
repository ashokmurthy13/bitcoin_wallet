package com.anymind.bitcoin.wallet.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {

    private String message;
    private HttpStatus status;
    private T result;
    private Timestamp timestamp;

    public RestResponse(T result, HttpStatus status) {
        this.message = "success";
        this.status = status;
        this.result = result;
        this.timestamp = new Timestamp(new Date().getTime());
    }
}