package com.techragesh.springbootmicrometerdemo.service.impl;

import com.techragesh.springbootmicrometerdemo.model.Book;
import de.triology.cb.Command;

public class AddBookCommand implements Command<Book> {

    private final Book book;

    public AddBookCommand(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
