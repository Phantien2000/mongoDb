package com.javatechie.querydsl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStatistic is a Querydsl query type for Statistic
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStatistic extends EntityPathBase<Statistic> {

    private static final long serialVersionUID = -180499599L;

    public static final QStatistic statistic = new QStatistic("statistic");

    public final StringPath field1 = createString("field1");

    public final StringPath field2 = createString("field2");

    public final StringPath field3 = createString("field3");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> number1 = createNumber("number1", Integer.class);

    public final NumberPath<Integer> number2 = createNumber("number2", Integer.class);

    public QStatistic(String variable) {
        super(Statistic.class, forVariable(variable));
    }

    public QStatistic(Path<? extends Statistic> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStatistic(PathMetadata metadata) {
        super(Statistic.class, metadata);
    }

}

