package com.navrudh.n26statisticsapi.service;

import com.navrudh.n26statisticsapi.bean.TransactionsBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class TransactionsService {

    private List<TransactionsBody> transactionsBodyList = new LinkedList<>();

    public synchronized void addTransaction(TransactionsBody transactionsBody) {
        transactionsBodyList.add(transactionsBody);
    }

    public synchronized List<TransactionsBody> getTransactions() {
        return new LinkedList<>(transactionsBodyList);
    }

    @Scheduled(cron = "* * * * * *")
    private synchronized void clearOlderTransactions() {
        long now = (System.currentTimeMillis() / 1000) * 1000;
        long beforeSixtySeconds = now - (60) * 1000;

        if (transactionsBodyList.removeIf(transactionsBody -> transactionsBody.getTimestamp() < beforeSixtySeconds
                || transactionsBody.getTimestamp() > now)) {
            log.debug("Removed transactions older than 60 seconds");
        }
    }
}
