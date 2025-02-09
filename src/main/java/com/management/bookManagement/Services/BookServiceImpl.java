package com.management.bookManagement.Services;


import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService{

    BookRepository bookRepository;
    ModelMapper modelMapper;

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
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();
        for(Author author: book.getAuthors()){
            book.removeAuthor(author);
        }
        bookRepository.delete(book);
    }

    @Override
    public Book getBook(String title) {
        return bookRepository.findByTitle(title);
    }


}
