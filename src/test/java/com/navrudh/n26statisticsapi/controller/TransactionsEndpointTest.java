package com.navrudh.n26statisticsapi.controller;

import com.navrudh.n26statisticsapi.bean.TransactionsBody;
import com.navrudh.n26statisticsapi.service.TransactionsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class TransactionsEndpointTest {

    @InjectMocks
    private TransactionsEndpoint transactionsEndpoint;

    @Mock
    private TransactionsService transactionsService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void postTransaction_valid() {
        long fiftyNineSecondsAgo = System.currentTimeMillis() - 59000;

        TransactionsBody transactionsBody = new TransactionsBody(10, fiftyNineSecondsAgo);
        ResponseEntity responseEntity = transactionsEndpoint.postTransaction(transactionsBody);

        Mockito.verify(transactionsService).addTransaction(transactionsBody);
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void postTransaction_invalid_olderThan60() {
        // 61 Seconds Old
        long sixtyOneSecondsAgo = System.currentTimeMillis() - 61000;

        TransactionsBody transactionsBody = new TransactionsBody(10, sixtyOneSecondsAgo);
        ResponseEntity responseEntity = transactionsEndpoint.postTransaction(transactionsBody);

        Mockito.verify(transactionsService, Mockito.times(0)).addTransaction(transactionsBody);
        Assert.assertEquals(204, responseEntity.getStatusCodeValue());
    }

    @Test
    public void postTransaction_invalid_inTheFuture() {
        // 1 Second Into The Future
        long oneSecondAgo = System.currentTimeMillis() + 1000;

        TransactionsBody transactionsBody = new TransactionsBody(10, oneSecondAgo);
        ResponseEntity responseEntity = transactionsEndpoint.postTransaction(transactionsBody);

        Mockito.verify(transactionsService, Mockito.times(0)).addTransaction(transactionsBody);
        Assert.assertEquals(204, responseEntity.getStatusCodeValue());
    }


}