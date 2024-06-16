package com.javatechie.querydsl.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.querydsl.entity.Statistic;
import com.javatechie.querydsl.repository.StatisticRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.javatechie.querydsl.entity.QStatistic.statistic;

@Service
public class StatisticService extends QuerydslRepositorySupport {
    @Autowired
    private StatisticRepository statisticRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @PersistenceContext
    private EntityManager entityManager;

    public StatisticService() {
        super(Statistic.class);
    }

    public List<Statistic> getAll(){
        return statisticRepository.findAll();
    }

    public void addStatistic(List<Statistic> statistics) {
        statisticRepository.saveAll(statistics) ;
    }

    public JsonNode getWithCondition() {

        BooleanExpression where = statistic.number1.gt(1)
                .or(statistic.number2.gt(3));

        Expression[] selectArr = { statistic.number1.sum(), statistic.field3 ,statistic.number2.sum()}; // Chọn các trường cần lấy, ở đây chỉ lấy toàn bộ entity Statistic

        OrderSpecifier<?>[] orderBy = { statistic.number2.sum().desc() }; // Sắp xếp theo trường number1 tăng dần

        Expression<?>[] groupBy = { statistic.field3 }; // Nhóm theo trường field3 của entity Statistic

        JPAQuery<Tuple> query = new JPAQuery<>(entityManager)
                .select(selectArr)
                .from(statistic)
                .where(where)
                .groupBy(groupBy)
                .orderBy(orderBy);

        List<Tuple> resultRow = query.fetch(); // Thực thi truy vấn và lấy danh sách tuples

        List<Map<String, Object>> result = resultRow.stream()
                .map(row -> tupleToMap(row, selectArr))
                .collect(Collectors.toList());



        // Chuyển đổi danh sách tuples thành JsonNode
        JsonNode result1 = objectMapper.convertValue(result, JsonNode.class);

        return result1;
    }

    private static Map<String, Object> tupleToMap(Tuple tuple, Expression<?>[] select) {
        Map<String, Object> map = new HashMap<>();
        for (Expression<?> expression : select) {
            String key = getFieldName(expression);
            map.put(key, tuple.get(expression));
        }
        return map;
    }

    private static String getFieldName(Expression<?> expression) {
        String fullName = expression.toString();
        // Extract the field name
        if (fullName.contains("(")) {
            // Remove aggregate function syntax
            int start = fullName.indexOf('(') + 1;
            int end = fullName.indexOf(')');
            return fullName.substring(start, end).split("\\.")[1]; // Get the field name inside the parentheses
        } else {
            // Fallback for normal fields
            String[] parts = fullName.split("\\.");
            return parts[parts.length - 1];
        }
    }

}
