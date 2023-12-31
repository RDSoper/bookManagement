package com.management.bookManagement.Controllers;

import com.management.bookManagement.DTO.BookDTO;
import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.management.bookManagement.Utils.Mappers.mapBookToBookDTO;

@RestController
@AllArgsConstructor
@RequestMapping("book")
public class BookController {

    BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getBooks(){
        List<Book> allBooks = bookService.getAllBooks();
        List<BookDTO> allBookDTOs = new ArrayList<>();

        for(Book book: allBooks){
            allBookDTOs.add(mapBookToBookDTO(book));
        }

        return new ResponseEntity<>(allBookDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    //TODO: Error handling
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id){
            return new ResponseEntity<>(mapBookToBookDTO(bookService.getBook(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book){
        return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
