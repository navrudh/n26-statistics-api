package com.navrudh.n26statisticsapi.constant;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.util.Date;

@RunWith(JUnit4.class)
public class SchedulerConstantsTest {

    @Test
    public void cronShouldRunEverySecond() {
        CronTrigger trigger = new CronTrigger(SchedulerConstants.EVERY_SECOND_CRON);
        Date currentTriggerTime = new Date();
        Date nextExecutionTime = trigger.nextExecutionTime(new SimpleTriggerContext(currentTriggerTime, currentTriggerTime, currentTriggerTime));
        Assert.assertEquals(1 + currentTriggerTime.getTime() / 1000, nextExecutionTime.getTime() / 1000);
    }
}