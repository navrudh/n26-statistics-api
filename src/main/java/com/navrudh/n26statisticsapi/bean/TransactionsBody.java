package com.navrudh.n26statisticsapi.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsBody {
    private double amount;
    private long timestamp;
}
