package com.management.bookManagement.Services;

import com.management.bookManagement.Entities.Book;

import java.util.List;


public interface BookService {
    List<Book> getAllBooks();
    Book saveBook(Book book);
    Book getBook(Long id);
    void deleteBook(Long id);
}
