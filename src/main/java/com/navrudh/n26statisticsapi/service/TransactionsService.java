package com.navrudh.n26statisticsapi.service;

import com.navrudh.n26statisticsapi.bean.TransactionsBody;
import com.navrudh.n26statisticsapi.constant.SchedulerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class TransactionsService {

    private List<TransactionsBody> transactionsBodyList = Collections.synchronizedList(new ArrayList<>());

    public synchronized void addTransaction(TransactionsBody transactionsBody) {
        transactionsBodyList.add(transactionsBody);
    }

    synchronized List<TransactionsBody> getTransactions() {
        return Collections.unmodifiableList(transactionsBodyList);
    }

    @Scheduled(cron = SchedulerConstants.EVERY_SECOND_CRON)
    final synchronized void clearOlderTransactions() {
        long now = (System.currentTimeMillis() / 1000) * 1000;
        long beforeSixtySeconds = now - (60) * 1000;

        if (transactionsBodyList.removeIf(transactionsBody -> transactionsBody.getTimestamp() < beforeSixtySeconds)) {
            log.debug("Removed transactions older than 60 seconds");
        }
    }
}
