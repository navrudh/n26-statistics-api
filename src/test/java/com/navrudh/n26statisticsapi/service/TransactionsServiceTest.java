package com.navrudh.n26statisticsapi.service;

import com.navrudh.n26statisticsapi.bean.TransactionsBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TransactionsServiceTest {

    private TransactionsService transactionsService = new TransactionsService();

    @Test
    public void clearOlderTransactions() {
        long now = System.currentTimeMillis();
        transactionsService.addTransaction(new TransactionsBody(0, now - 59 * 1000));
        transactionsService.addTransaction(new TransactionsBody(0, now - 61 * 1000));
        transactionsService.addTransaction(new TransactionsBody(0, now - 62 * 1000));
        transactionsService.addTransaction(new TransactionsBody(0, now));

        transactionsService.clearOlderTransactions();

        List<TransactionsBody> transactionsBodyList = transactionsService.getTransactions();
        assertEquals(2, transactionsBodyList.size());
        assertEquals(now - 59 * 1000, transactionsBodyList.get(0).getTimestamp());
        assertEquals(now, transactionsBodyList.get(1).getTimestamp());
    }

}