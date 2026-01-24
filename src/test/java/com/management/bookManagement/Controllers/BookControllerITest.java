package com.management.bookManagement.Controllers;

import com.management.bookManagement.DTO.BookDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Repositories.AuthorRepository;
import com.management.bookManagement.Repositories.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BookControllerITest {
    @Autowired
    private BookController bookController;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
        bookRepository.deleteAll();
        setUpAuthors();
    }

    @Test
    void getBooks() {
        ResponseEntity<List<BookDTO>> bookList = bookController.getBooks();
        Assertions.assertThat(bookList.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        List<BookDTO> bookListResponse = bookList.getBody().stream().toList();

        BookDTO book1 = bookListResponse.getFirst();
        assertThat(book1.getTitle()).isEqualTo("A title again");
        assertThat(book1.getAuthors().size()).isEqualTo(1);
        assertThat(book1.getAuthors().stream().toList().getFirst()).isEqualTo("John Smith");
        assertThat(book1.getRead()).isEqualTo(true);
        assertThat(book1.getGenre()).isEqualTo("Non-Fiction");
        assertThat(book1.getOwned()).isEqualTo(false);

        BookDTO book2 = bookListResponse.getLast();
        assertThat(book2.getTitle()).isEqualTo("title, The");
        assertThat(book2.getAuthors().size()).isEqualTo(1);
        assertThat(book2.getAuthors().stream().toList().getFirst()).isEqualTo("Jane Doe");
        assertThat(book2.getRead()).isEqualTo(false);
        assertThat(book2.getGenre()).isEqualTo("Fiction");
        assertThat(book2.getOwned()).isEqualTo(true);
    }

    @Test
    void getBook() {
        Book bookToFind = bookRepository.findByTitle("The title");
        ResponseEntity<BookDTO> bookResponse = bookController.getBook(bookToFind.getId());
        assertThat(bookResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        BookDTO book = bookResponse.getBody();

        assertThat(book.getTitle()).isEqualTo("title, The");
        assertThat(book.getAuthors().size()).isEqualTo(1);
        assertThat(book.getAuthors().stream().toList().getFirst()).isEqualTo("Jane Doe");
        assertThat(book.getRead()).isEqualTo(false);
        assertThat(book.getGenre()).isEqualTo("Fiction");
        assertThat(book.getOwned()).isEqualTo(true);
    }

    @Test
    void saveBook() {
        Book book = setupBook();
        ResponseEntity<BookDTO> bookResponse = bookController.saveBook(book);
        assertThat(bookResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        BookDTO savedBook = bookResponse.getBody();

        assertThat(savedBook.getTitle()).isEqualTo("Saved book");
        assertThat(savedBook.getGenre()).isEqualTo("Genre");
        assertThat(savedBook.getOwned()).isEqualTo(false);
        assertThat(savedBook.getRead()).isEqualTo(true);
        assertThat(savedBook.getAuthors().size()).isEqualTo(1);
        assertThat(savedBook.getAuthors().stream().toList().getFirst()).isEqualTo("Author");
    }

    @Test
    void saveAuthor_doesNotAddDuplicateBooks() {
        Book book = setupBook();

        ResponseEntity<BookDTO> result = bookController.saveBook(book);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Assertions.assertThat(result.getBody().getTitle()).isEqualTo("Saved book");

        List<String> author = new ArrayList<>(result.getBody().getAuthors());
        Assertions.assertThat(author.getFirst()).isEqualTo("Author");

        Book sameBookAgain = setupBook();
        ResponseEntity<BookDTO> sameBook = bookController.saveBook(sameBookAgain);
        Assertions.assertThat(sameBook.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Assertions.assertThat(sameBook.getBody().getAuthors().size()).isEqualTo(1);
    }
    @Test
    void deleteBook() {
        Book bookToFind = bookRepository.findByTitle("The title");
        ResponseEntity<HttpStatus> bookResponse = bookController.deleteBook(bookToFind.getId());
        assertThat(bookResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(bookRepository.existsById(bookToFind.getId())).isFalse();
    }

    private static Book setupBook() {
        Set<Author> authors = new HashSet<>();
        Author author = Author.builder().name("Author").build();
        authors.add(author);
        return Book.builder()
                .title("Saved book")
                .genre("Genre")
                .owned(false)
                .read(true)
                .authors(authors)
                .build();
    }
    private void setUpAuthors() {
        Set<Book> books = new HashSet<>();
        Book book1 = Book.builder()
                .title("The title")
                .read(false)
                .owned(true)
                .genre("Fiction")
                .build();
        books.add(book1);

        Author jane = Author.builder()
                .name("Jane Doe")
                .books(books)
                .build();
        authorRepository.save(jane);

        Set<Book> moreBooks = new HashSet<>();
        Book book2 = Book.builder()
                .title("A title again")
                .read(true)
                .owned(false)
                .genre("Non-Fiction")
                .build();
        moreBooks.add(book2);

        Author john = Author.builder()
                .name("John Smith")
                .books(moreBooks)
                .build();
        authorRepository.save(john);
    }
}