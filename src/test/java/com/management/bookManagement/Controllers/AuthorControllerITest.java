package com.management.bookManagement.Controllers;

import com.management.bookManagement.DTO.AuthorBookDTO;
import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@Sql(scripts = "/AuthorController/AuthorControllerITestSetup.sql")
class AuthorControllerITest {

    @Autowired
    private AuthorController authorController;

    @Test
    void getAuthors() {
        ResponseEntity<List<AuthorDTO>> result = authorController.getAuthors();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        List<AuthorDTO> body = result.getBody();
        assertThat(body.size()).isEqualTo(2);

        var firstAuthor = body.getFirst();
        assertThat(firstAuthor.getName()).isEqualTo("Jane Doe");

        List<AuthorBookDTO> firstAuthorBook = firstAuthor.getBooks().stream().toList();
        assertThat(firstAuthorBook.getFirst().getTitle()).isEqualTo("title, The");
        assertThat(firstAuthorBook.getFirst().getRead()).isEqualTo(false);
        assertThat(firstAuthorBook.getFirst().getOwned()).isEqualTo(true);
        assertThat(firstAuthorBook.getFirst().getGenre()).isEqualTo("Fiction");

        AuthorDTO lastAuthor = body.getLast();
        assertThat(lastAuthor.getName()).isEqualTo("John Smith");

       var lastAuthorBook = lastAuthor.getBooks().stream().toList();
        assertThat(lastAuthorBook.getFirst().getTitle()).isEqualTo("A title again");
        assertThat(lastAuthorBook.getFirst().getRead()).isEqualTo(true);
        assertThat(lastAuthorBook.getFirst().getOwned()).isEqualTo(false);
        assertThat(lastAuthorBook.getFirst().getGenre()).isEqualTo("Non-Fiction");
    }

    @Test
    void getAuthor() {
        ResponseEntity<AuthorDTO> result = authorController.getAuthor(1L);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        AuthorDTO body = result.getBody();
        assertThat(body.getName()).isEqualTo("Jane Doe");

        List<AuthorBookDTO> firstAuthorBook = body.getBooks().stream().toList();
        assertThat(firstAuthorBook.getFirst().getTitle()).isEqualTo("title, The");
        assertThat(firstAuthorBook.getFirst().getRead()).isEqualTo(false);
        assertThat(firstAuthorBook.getFirst().getOwned()).isEqualTo(true);
        assertThat(firstAuthorBook.getFirst().getGenre()).isEqualTo("Fiction");
    }

    @Test
    void saveAuthor() {
        Author author = setupAuthor();

        ResponseEntity<AuthorDTO> result = authorController.saveAuthor(author);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        List<AuthorBookDTO> book = result.getBody().getBooks().stream().toList();
        assertThat(book.getFirst().getTitle()).isEqualTo("Saved author book");
    }

    private Author setupAuthor() {
        Author author = new Author();
        author.setName("Saved author");

        Book book = new Book();
        book.setTitle("Saved author book");
        author.setBooks(Set.of(book));
        book.setAuthors(Set.of(author));

        return author;
    }
}