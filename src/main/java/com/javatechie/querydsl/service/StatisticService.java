package com.javatechie.querydsl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.querydsl.dto.Param;
import com.javatechie.querydsl.entity.Statistic;
import com.javatechie.querydsl.repository.StatisticRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    public JsonNode getAll() throws JsonProcessingException {
        List<Statistic> list = statisticRepository.findAll();
         String jsonString = objectMapper.writeValueAsString(list);
         JsonNode jsonNode = objectMapper.readTree(jsonString);
         return  jsonNode;
    }

    public void delete() {
        Criteria criteria = Criteria.where("number1").gt(3);
        Query query= new Query()
                .addCriteria(criteria);
        mongoTemplate.remove(query, "statistic");

    }

    public void addStatistic(List<Statistic> statistics) {
        statisticRepository.saveAll(statistics);
    }

    public JsonNode getWithCondition(Param param) {
        // Build the match criteria
        Criteria criteria = Criteria.where("number1").gt(0);
        MatchOperation match = Aggregation.match(criteria);

//
        List<String> list = new ArrayList<>();
        list.add("field1");
        list.add("field2");


        Fields fields = Fields.fields();
        for (String field : list) {
            fields = fields.and(field);
        }
        GroupOperation groupBy = Aggregation.group(fields);

        List<String> select = new ArrayList<>();
        select.add("number1");
        select.add("number2");
        for (String field : select) {
            groupBy = groupBy
                    .sum(field).as(field);
        }

//        GroupOperation groupBy = Aggregation.group("field1", "field2")
//                .sum("number1").as("sumNumber1");

        // Projection operation (selecting fields)
        ProjectionOperation project = Aggregation.project();

        for(String field : list){
            project = project.and(field).as(field);
        }
        for(String field : select){
            project = project.and(field).as(field);
        }
        project = project.andExclude("_id");

        List<Sort.Order> sortOrders = new ArrayList<>();
        sortOrders.add(Sort.Order.asc("number1"));
        sortOrders.add(Sort.Order.asc("number2"));

        // Sorting operation
        SortOperation orderBy = Aggregation.sort(Sort.Direction.DESC, "number1");
        orderBy = orderBy.and(Sort.Direction.ASC, "number2");

        // Limit operation
        LimitOperation limit = Aggregation.limit(10);

        // Aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(match, groupBy, project, orderBy, limit);

//        List<Map> result = mongoTemplate.aggregate(aggregation, "yourCollectionName", Map.class).getMappedResults();
        // Execute the aggregation with Document.class
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "statistic", Document.class);
        List<Document> statisticResponses = results.getMappedResults();

        // Convert the list of Documents to JsonNode
        return objectMapper.valueToTree(statisticResponses);
    }

    private Map<String, Object> mapResult(Map<String, Object> result) {
        Map<String, Object> map = new HashMap<>();
        map.put("field3", result.get("_id"));
        map.put("totalNumber1", result.get("totalNumber1"));
        map.put("totalNumber2", result.get("totalNumber2"));

        return map;
    }
}
