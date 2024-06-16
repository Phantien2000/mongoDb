package com.javatechie.querydsl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuthor is a Querydsl query type for Author
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthor extends EntityPathBase<Author> {

    private static final long serialVersionUID = -1420104950L;

    public static final QAuthor author = new QAuthor("author");

    public final NumberPath<Integer> authorId = createNumber("authorId", Integer.class);

    public final ListPath<Book, QBook> books = this.<Book, QBook>createList("books", Book.class, QBook.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath name = createString("name");

    public QAuthor(String variable) {
        super(Author.class, forVariable(variable));
    }

    public QAuthor(Path<? extends Author> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthor(PathMetadata metadata) {
        super(Author.class, metadata);
    }

}

