package com.navrudh.n26statisticsapi.controller;

import com.navrudh.n26statisticsapi.bean.StatisticsBody;
import com.navrudh.n26statisticsapi.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class StatisticsEndpoint {

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @ResponseBody
    public StatisticsBody getStatistics() {
        log.info("Request timestamp: {}", System.currentTimeMillis());
        return statisticsService.getStatisticsBody();
    }
}
