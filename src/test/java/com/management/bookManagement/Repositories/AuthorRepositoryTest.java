package com.management.bookManagement.Repositories;

import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setup() {
        Book book = new Book(null, "Book1", new HashSet<>(), true, false, "A genre");
        Author author = new Author(null, "author1", new HashSet<>());
        author.getBooks().add(book);
        book.getAuthors().add(author);
        authorRepository.save(author);
        authorRepository.save(new Author(null, "author2", new HashSet<>()));
        authorRepository.save(new Author(null, "author3", new HashSet<>()));
        authorRepository.save(new Author(null, "author4", new HashSet<>()));
    }

    @AfterEach
    void tearDown() {
        authorRepository.deleteAll();
    }

    @Test
    void testRepositoryGetsAuthorByName() {
        Author result = authorRepository.findByNameIs("author1");

        assertThat(result.getName()).isEqualTo("author1");
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getBooks().size()).isEqualTo(1);

        Book book = result.getBooks().stream().toList().getFirst();
        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("Book1");
        assertThat(book.getAuthors()).extracting(Author::getName).isEqualTo(List.of("author1"));
        assertThat(book.getRead()).isTrue();
        assertThat(book.getOwned()).isFalse();
        assertThat(book.getGenre()).isEqualTo("A genre");



    }


}