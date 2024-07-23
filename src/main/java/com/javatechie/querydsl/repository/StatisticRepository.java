package com.javatechie.querydsl.repository;

import com.javatechie.querydsl.entity.Statistic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends MongoRepository<Statistic, String> {
}
