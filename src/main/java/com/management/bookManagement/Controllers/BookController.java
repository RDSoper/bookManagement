package com.management.bookManagement.Controllers;

import com.management.bookManagement.DTO.BookDTO;
import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Services.BookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class BookController {

    BookService bookService;
    ModelMapper modelMapper;

    @GetMapping("books")
    public ResponseEntity<List<BookDTO>> getBooks(){
        List<BookDTO> books = bookService.getBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("{id}")
    //TODO: Error handling
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id){
        BookDTO book = bookService.getBook(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookDTO> saveBook(@RequestBody Book book) {
        BookDTO newBook = bookService.saveBook(book);
        return ResponseEntity.ok(newBook);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
