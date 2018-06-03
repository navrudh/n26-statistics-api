package com.navrudh.n26statisticsapi.bean;

import lombok.Value;

@Value(staticConstructor = "with")
public class StatisticsBody {
    private final double sum;
    private final double avg;
    private final double max;
    private final double min;
    private final long count;
}
