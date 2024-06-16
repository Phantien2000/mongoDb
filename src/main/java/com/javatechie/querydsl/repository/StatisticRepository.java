package com.javatechie.querydsl.repository;

import com.javatechie.querydsl.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<Statistic, Integer> {
}
