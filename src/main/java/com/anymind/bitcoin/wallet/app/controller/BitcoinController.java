package com.anymind.bitcoin.wallet.app.controller;

import com.anymind.bitcoin.wallet.app.model.Bitcoin;
import com.anymind.bitcoin.wallet.app.model.BitcoinHistoryResponse;
import com.anymind.bitcoin.wallet.app.model.RestResponse;
import com.anymind.bitcoin.wallet.app.service.BitcoinService;
import com.anymind.bitcoin.wallet.app.validation.ValidateRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restapi/v1/bitcoin")
@RequiredArgsConstructor
public class BitcoinController {

    private final @NonNull BitcoinService bitcoinService;
    private final @NonNull ValidateRequest validate;

    @PostMapping("")
    public RestResponse<Bitcoin> saveRecord(@RequestBody Bitcoin bitcoin) {
        validate.validateRequest(bitcoin);
        return bitcoinService.saveRecord(bitcoin);
    }

    @GetMapping("")
    public RestResponse<List<BitcoinHistoryResponse>> getHistory(@RequestParam("startDateTime") String startDateTime,
                                                                 @RequestParam("endDateTime") String endDateTime) {
        validate.validateDateRange(startDateTime, endDateTime);
        return bitcoinService.getHistory(startDateTime, endDateTime);
    }
}
