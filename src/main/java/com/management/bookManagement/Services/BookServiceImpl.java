package com.management.bookManagement.Services;

import com.management.bookManagement.DTO.BookDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService{

    BookRepository bookRepository;
    ModelMapper modelMapper;

    @Override
    public List<BookDTO> getAllBooks(){
        List<Book> books = bookRepository.findAllByOrderByTitleAsc();
        List<BookDTO> bookDTOs = new ArrayList<>();
        for(Book book: books){
            bookDTOs.add(modelMapper.map(book, BookDTO.class));
        }
        return bookDTOs;
    }

    @Override
    public BookDTO saveBook(Book book) {
        if (book.getAuthors() != null) {
            for (Author author : book.getAuthors()) {
                author.addBook(book);
            }
        }

        return modelMapper.map(bookRepository.save(book), BookDTO.class);
    }

    @Override
    public BookDTO getBook(Long id){
        Book book = bookRepository.findById(id).orElseThrow();
        return modelMapper.map(book, BookDTO.class);
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
        Book book = bookRepository.findByTitle(title);
        book.setTitle(modelMapper.map(book.getTitle(), String.class));

        return book;
    }
}
