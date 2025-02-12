package com.management.bookManagement.Services;

import com.management.bookManagement.DTO.BookDTO;
import com.management.bookManagement.Entities.Book;

import java.util.List;


public interface BookService {
    List<Book> getAllBooks();
    BookDTO saveBook(Book book);
    Book getBook(Long id);
    void deleteBook(Long id);
    Book getBook(String title);
}
