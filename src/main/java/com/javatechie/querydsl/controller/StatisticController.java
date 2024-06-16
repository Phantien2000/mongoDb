package com.javatechie.querydsl.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.javatechie.querydsl.entity.Statistic;
import com.javatechie.querydsl.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping
    public List<Statistic> getAll() {
        return statisticService.getAll();
    }

    @PostMapping
    public void addStatistics(@RequestBody List<Statistic> statisticList){
        statisticService.addStatistic(statisticList);
    }

    @GetMapping("/cd")
    public JsonNode getWithCondition() {
        return statisticService.getWithCondition();
    }
}
