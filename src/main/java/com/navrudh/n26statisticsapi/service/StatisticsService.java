package com.navrudh.n26statisticsapi.service;

import com.navrudh.n26statisticsapi.bean.StatisticsBody;
import com.navrudh.n26statisticsapi.bean.TransactionsBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

@Service
public class StatisticsService {

    @Autowired
    private TransactionsService transactionsService;

    private StatisticsBody statisticsBody = StatisticsBody.with(0, 0, 0, 0, 0);

    public StatisticsBody getStatisticsBody() {
        return statisticsBody;
    }

    @Scheduled(cron = "* * * * * *")
    private void computeStatistics() {
        List<TransactionsBody> transactionsBodyList = transactionsService.getTransactions();

        double sum = transactionsBodyList.stream().mapToDouble(TransactionsBody::getAmount).sum();
        OptionalDouble avg = transactionsBodyList.stream().mapToDouble(TransactionsBody::getAmount).average();
        OptionalDouble max = transactionsBodyList.stream().mapToDouble(TransactionsBody::getAmount).max();
        OptionalDouble min = transactionsBodyList.stream().mapToDouble(TransactionsBody::getAmount).min();
        long count = transactionsBodyList.stream().count();

        statisticsBody = StatisticsBody.with(sum, avg.orElse(0), max.orElse(0), min.orElse(0), count);
    }
}
