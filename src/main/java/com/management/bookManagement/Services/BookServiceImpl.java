package com.management.bookManagement.Services;


import com.management.bookManagement.DTO.BookDTO;
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
    public BookDTO saveBook(Book book) {
        if (book.getAuthors() != null) {
            for (Author author : book.getAuthors()) {
                author.addBook(book);
            }
        }
        String title = handleTheInBookTitle(book.getTitle());
        book.setTitle(title);

        return modelMapper.map(bookRepository.save(book), BookDTO.class);
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

    private String handleTheInBookTitle(String title) {
        return (title != null && !title.isEmpty() && title.toLowerCase().startsWith("the "))
                ? title.substring(4) + ", The"
                : title;
    }

}
