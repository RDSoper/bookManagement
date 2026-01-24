package com.management.bookManagement.Controllers;

import com.management.bookManagement.DTO.AuthorBookDTO;
import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Repositories.AuthorRepository;
import com.management.bookManagement.Repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthorControllerITest {

    @Autowired
    private AuthorController authorController;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
        bookRepository.deleteAll();
        setUpData();
    }

    @Test
    void getAuthors() {
        ResponseEntity<List<AuthorDTO>> result = authorController.getAuthors();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        List<AuthorDTO> body = result.getBody();
        assertThat(body.size()).isEqualTo(2);

        AuthorDTO firstAuthor = body.getFirst();
        assertThat(firstAuthor.getName()).isEqualTo("Jane Doe");

        List<AuthorBookDTO> firstAuthorBook = new ArrayList<>(firstAuthor.getBooks());
        assertThat(firstAuthorBook.getFirst().getTitle()).isEqualTo("title, The");
        assertThat(firstAuthorBook.getFirst().getRead()).isEqualTo(false);
        assertThat(firstAuthorBook.getFirst().getOwned()).isEqualTo(true);
        assertThat(firstAuthorBook.getFirst().getGenre()).isEqualTo("Fiction");

        AuthorDTO lastAuthor = body.getLast();
        assertThat(lastAuthor.getName()).isEqualTo("John Smith");

        List<AuthorBookDTO> lastAuthorBook = new ArrayList<>(lastAuthor.getBooks());
        assertThat(lastAuthorBook.getFirst().getTitle()).isEqualTo("A title again");
        assertThat(lastAuthorBook.getFirst().getRead()).isEqualTo(true);
        assertThat(lastAuthorBook.getFirst().getOwned()).isEqualTo(false);
        assertThat(lastAuthorBook.getFirst().getGenre()).isEqualTo("Non-Fiction");
    }

    @Test
    void getAuthor() {
        Author author = authorRepository.findByNameIs("Jane Doe");
        ResponseEntity<AuthorDTO> result = authorController.getAuthor(author.getId());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        AuthorDTO body = result.getBody();
        assertThat(body.getName()).isEqualTo("Jane Doe");

        List<AuthorBookDTO> firstAuthorBook = new ArrayList<>(body.getBooks());
        assertThat(firstAuthorBook.getFirst().getTitle()).isEqualTo("title, The");
        assertThat(firstAuthorBook.getFirst().getRead()).isEqualTo(false);
        assertThat(firstAuthorBook.getFirst().getOwned()).isEqualTo(true);
        assertThat(firstAuthorBook.getFirst().getGenre()).isEqualTo("Fiction");
    }

    @Test
    void saveAuthor_savesAuthor() {
        Author author = setupAuthor();

        ResponseEntity<AuthorDTO> result = authorController.saveAuthor(author);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody().getName()).isEqualTo("Saved author");

        List<AuthorBookDTO> book = new ArrayList<>(result.getBody().getBooks());
        assertThat(book.getFirst().getTitle()).isEqualTo("Saved author book");
    }

    @Test
    void saveAuthor_doesNotAddDuplicateBooks() {
        Author author = setupAuthor();

        ResponseEntity<AuthorDTO> result = authorController.saveAuthor(author);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody().getName()).isEqualTo("Saved author");

        List<AuthorBookDTO> book = new ArrayList<>(result.getBody().getBooks());
        assertThat(book.getFirst().getTitle()).isEqualTo("Saved author book");

        Author sameAuthorAgain = setupAuthor();
        ResponseEntity<AuthorDTO> sameAuthor = authorController.saveAuthor(sameAuthorAgain);
        assertThat(sameAuthor.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(sameAuthor.getBody().getBooks().size()).isEqualTo(1);
    }

    private Author setupAuthor() {
        Author author = new Author();
        author.setName("Saved author");

        Book book = new Book();
        book.setTitle("Saved author book");

        author.addBook(book);
        return author;
    }

    private void setUpData() {
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