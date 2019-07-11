package com.techragesh.springbootmicrometerdemo.service.impl;

import com.techragesh.springbootmicrometerdemo.dao.BookEntity;
import com.techragesh.springbootmicrometerdemo.dao.BookRepository;
import com.techragesh.springbootmicrometerdemo.mapper.BookMapper;
import com.techragesh.springbootmicrometerdemo.model.Book;
import de.triology.cb.CommandHandler;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
class AddBookCommandHandler
        implements CommandHandler<Book, AddBookCommand> {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public AddBookCommandHandler() {
        bookMapper = Mappers.getMapper(BookMapper.class);
    }

    @Override
    @Transactional
    public Book handle(AddBookCommand command) {
        Book book = command.getBook();
        if(book.getBookId()==0 || bookRepository.existsById(book.getBookId())) {
            BookEntity bookEntity = bookRepository.save(bookMapper.fromBook(book));
            return bookMapper.toBook(bookEntity);
        }else {
            return null;
        }
    }
}