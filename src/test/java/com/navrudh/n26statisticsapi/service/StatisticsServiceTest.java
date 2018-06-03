package com.navrudh.n26statisticsapi.service;

import com.navrudh.n26statisticsapi.bean.StatisticsBody;
import com.navrudh.n26statisticsapi.bean.TransactionsBody;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

    @InjectMocks
    private StatisticsService statisticsService;

    @Mock
    private TransactionsService transactionsService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void computeStatistics_5transactions() {
        List<TransactionsBody> transactionsBodyList = new LinkedList<>();
        transactionsBodyList.add(new TransactionsBody(10, 0));
        transactionsBodyList.add(new TransactionsBody(20, 0));
        transactionsBodyList.add(new TransactionsBody(30, 0));
        transactionsBodyList.add(new TransactionsBody(40, 0));
        transactionsBodyList.add(new TransactionsBody(50, 0));
        Mockito.when(transactionsService.getTransactions()).thenReturn(transactionsBodyList);

        statisticsService.computeStatistics();

        Assert.assertEquals(StatisticsBody.with(150, 30, 50, 10, 5), statisticsService.getStatisticsBody());
    }

    @Test
    public void computeStatistics_3transactions() {
        List<TransactionsBody> transactionsBodyList = new LinkedList<>();
        transactionsBodyList.add(new TransactionsBody(20, 0));
        transactionsBodyList.add(new TransactionsBody(30, 0));
        transactionsBodyList.add(new TransactionsBody(70, 0));
        Mockito.when(transactionsService.getTransactions()).thenReturn(transactionsBodyList);

        statisticsService.computeStatistics();

        Assert.assertEquals(StatisticsBody.with(120, 40, 70, 20, 3), statisticsService.getStatisticsBody());
    }
}