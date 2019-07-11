package com.techragesh.springbootmicrometerdemo.service.impl;

import com.techragesh.springbootmicrometerdemo.dao.BookRepository;
import com.techragesh.springbootmicrometerdemo.mapper.BookMapper;
import com.techragesh.springbootmicrometerdemo.model.Book;
import com.techragesh.springbootmicrometerdemo.service.BookService;
import de.triology.cb.CommandBus;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CommandBus commandBus;

    public BookServiceImpl() {
        bookMapper = Mappers.getMapper(BookMapper.class);
    }


    @Override
    public Optional<Book> getBookById(int bookId) {
        return bookRepository.findById(bookId).map(bookEntity-> bookMapper.toBook(bookEntity));
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll().parallelStream()
                .map(bookEntity -> bookMapper.toBook(bookEntity))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteBookById(int bookId) {
        bookRepository.deleteById(bookId);
        return true;
    }

    @Override
    public Optional<Book> saveorupdateBook(Book book) {
        return Optional.ofNullable(commandBus.execute(new AddBookCommand(book)));
    }
}
