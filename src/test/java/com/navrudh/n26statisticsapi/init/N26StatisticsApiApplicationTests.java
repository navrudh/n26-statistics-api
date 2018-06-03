package com.navrudh.n26statisticsapi.init;

import com.navrudh.n26statisticsapi.bean.StatisticsBody;
import com.navrudh.n26statisticsapi.bean.TransactionsBody;
import com.navrudh.n26statisticsapi.controller.StatisticsEndpoint;
import com.navrudh.n26statisticsapi.controller.TransactionsEndpoint;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = N26StatisticsApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class N26StatisticsApiApplicationTests {

    @Autowired
    private TransactionsEndpoint transactionsEndpoint;

    @Autowired
    private StatisticsEndpoint statisticsEndpoint;

    @Test
    public void getStatistics() throws InterruptedException {

        long sixtySecondsAgo = System.currentTimeMillis() - 60000;
        StatisticsBody statisticsBody;

        transactionsEndpoint.postTransaction(new TransactionsBody(10, sixtySecondsAgo + 100));
        transactionsEndpoint.postTransaction(new TransactionsBody(20, sixtySecondsAgo + 3000));
        transactionsEndpoint.postTransaction(new TransactionsBody(30, sixtySecondsAgo + 6000));
        transactionsEndpoint.postTransaction(new TransactionsBody(40, sixtySecondsAgo + 9000));
        transactionsEndpoint.postTransaction(new TransactionsBody(50, sixtySecondsAgo + 12000));

        Thread.sleep(500);

        statisticsBody = statisticsEndpoint.getStatistics();
        Assert.assertEquals(StatisticsBody.with(150, 30, 50, 10, 5), statisticsBody);

        Thread.sleep(3000);

        statisticsBody = statisticsEndpoint.getStatistics();
        Assert.assertEquals(StatisticsBody.with(140, 35, 50, 20, 4), statisticsBody);

        Thread.sleep(3000);

        statisticsBody = statisticsEndpoint.getStatistics();
        Assert.assertEquals(StatisticsBody.with(120, 40, 50, 30, 3), statisticsBody);

        Thread.sleep(3000);

        statisticsBody = statisticsEndpoint.getStatistics();
        Assert.assertEquals(StatisticsBody.with(90, 45, 50, 40, 2), statisticsBody);

        Thread.sleep(3000);

        statisticsBody = statisticsEndpoint.getStatistics();
        Assert.assertEquals(StatisticsBody.with(50, 50, 50, 50, 1), statisticsBody);
    }

    @Test
    public void postTransaction_valid() {
        long sixtySecondsAgo = System.currentTimeMillis() - 59000;

        ResponseEntity responseEntity = transactionsEndpoint.postTransaction(new TransactionsBody(10, sixtySecondsAgo));

        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void postTransaction_invalid_olderThan60() {
        // 61 Seconds Old
        long sixtySecondsAgo = System.currentTimeMillis() - 61000;

        ResponseEntity responseEntity = transactionsEndpoint.postTransaction(new TransactionsBody(10, sixtySecondsAgo));

        Assert.assertEquals(204, responseEntity.getStatusCodeValue());
    }

    @Test
    public void postTransaction_invalid_inTheFuture() {
        // 1 Second Into The Future
        long sixtySecondsAgo = System.currentTimeMillis() + 1000;

        ResponseEntity responseEntity = transactionsEndpoint.postTransaction(new TransactionsBody(10, sixtySecondsAgo));

        Assert.assertEquals(204, responseEntity.getStatusCodeValue());
    }
}
