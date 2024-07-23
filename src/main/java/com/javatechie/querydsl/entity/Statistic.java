package com.javatechie.querydsl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "statistic")
public class Statistic {
    @Id
    private String id;
    private String field1;
    private String field2;
    private String field3;
    private int number1;
    private int number2;

//    @Column(columnDefinition = "DATE")
    private LocalDate date = LocalDate.now();;


}
