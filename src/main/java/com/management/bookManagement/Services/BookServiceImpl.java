package com.management.bookManagement.Services;


import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService{

    BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks(){
        return bookRepository.findAllByOrderByTitleAsc();
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBook(Long id){
        return bookRepository.findById(id).get();
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


}
