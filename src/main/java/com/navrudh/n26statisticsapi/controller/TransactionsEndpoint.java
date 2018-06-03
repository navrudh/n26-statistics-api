package com.navrudh.n26statisticsapi.controller;

import com.navrudh.n26statisticsapi.bean.TransactionsBody;
import com.navrudh.n26statisticsapi.service.TransactionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
public class TransactionsEndpoint {

    @Autowired
    private TransactionsService transactionsService;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity postTransaction(@RequestBody TransactionsBody transactionsBody) {

        long now = System.currentTimeMillis();
        long sixtySecondsBeforeNow = now - 60 * 1000;

        log.debug("Request timestamp: {}", now);
        log.info("Transaction Body: {}", transactionsBody);

        if (now > transactionsBody.getTimestamp() && transactionsBody.getTimestamp() > sixtySecondsBeforeNow) {
            log.info("Transaction entry accepted");
            transactionsService.addTransaction(transactionsBody);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        log.warn("Transaction entry rejected");
        return ResponseEntity.noContent().build();
    }
}

