package com.management.bookManagement.Configuration;

import com.management.bookManagement.DTO.AuthorBookDTO;
import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.DTO.BookDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import org.junit.jupiter.api.Test;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = {ModelMapperConfig.class})
class ModelMapperConfigTest {

    @Autowired
    ModelMapper modelMapper;

    @Test
    void testTitleHandledCorrectly() {
        Book book1 = new Book(1L, "The best book ever", new HashSet<>(), true, true, "");
        Book book2 = new Book(1L, "Best, the best and nothing but the best book ever", new HashSet<>(), true, true, "");

        BookDTO book1Result = modelMapper.map(book1, BookDTO.class);
        BookDTO book2Result = modelMapper.map(book2, BookDTO.class);

        assertThat(book1Result.getTitle()).isEqualTo("best book ever, The");
        assertThat(book2Result.getTitle()).isEqualTo("Best, the best and nothing but the best book ever");
    }

    @Test
    void testBookMapsAuthorCorrectly() {

        Book book =  new Book(1L, "The Title is incredible", new HashSet<>(), true, true, "Genre");
        Author author = new Author(1L, "Author1", new HashSet<>());
        book.getAuthors().add(author);
        author.getBooks().add(book);

        BookDTO result = modelMapper.map(book, BookDTO.class);

        assertThat(result.getAuthors()).isEqualTo(Set.of("Author1"));
    }

    @Test
    void testAuthorMapsBooksCorrectly() {
        Author author = new Author(1L, "Great author", new HashSet<>());
        Book book1 =  new Book(1L, "The Title is incredible", new HashSet<>(), true, true, "Genre");
        book1.getAuthors().add(author);
        author.getBooks().add(book1);

        AuthorDTO result = modelMapper.map(author, AuthorDTO.class);

        List<AuthorBookDTO> books = result.getBooks().stream().toList();
        assertThat(books.size()).isEqualTo(1);

        AuthorBookDTO authorBookDTO1 = books.getFirst();

        assertThat(authorBookDTO1.getGenre()).isEqualTo("Genre");
        assertThat(authorBookDTO1.getRead()).isEqualTo(true);
        assertThat(authorBookDTO1.getOwned()).isEqualTo(true);
        assertThat(authorBookDTO1.getTitle()).isEqualTo("Title is incredible, The");
    }

    @Test
    void testMapperCanHandleNullValuesForAuthor() {
        Book book =  new Book(1L, "The Title is incredible", null, true, true, "Genre");
        BookDTO result = modelMapper.map(book, BookDTO.class);

        assertThat(result.getAuthors()).isEmpty();
    }

    @Test
    void testMapperCanHandleNullValuesForBook() {
        Author author = new Author(1L, "Great author", null);
        AuthorDTO result = modelMapper.map(author, AuthorDTO.class);

        List<AuthorBookDTO> books = result.getBooks().stream().toList();

        assertThat(books).isEmpty();
    }
}