package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BooksEnum {
    BOOK1("Clean Code", "Robert Martin", 2008, 50.0),
    BOOK2("The Clean Coder","Robert Martin", 2011, 50.0),
    BOOK3("Clean Architecture","Robert Martin", 2017, 50.0),
    BOOK4("Test Driven Development by Example", "Kent Beck", 2003, 50.0),
    BOOK5("Working  Effectively with Legacy Code", "Michael C. Feathers", 2004, 50.0);

    private final String title;
    private final String author;
    private final int year;
    private final double price;

}
