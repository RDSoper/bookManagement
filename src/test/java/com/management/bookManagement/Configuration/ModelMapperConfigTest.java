package com.management.bookManagement.Configuration;

import com.management.bookManagement.DTO.BookDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import org.junit.jupiter.api.Test;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ModelMapperConfigTest {

    @Autowired
    ModelMapper modelMapper;

    @Test
    void testBookAuthorIsAuthorNameOnly() {

        Book book =  new Book(1L, "The Title is incredible", new HashSet<>(), true, true, "Genre");
        Author author = new Author(1L, "Author1", new HashSet<>());
        book.getAuthors().add(author);
        author.getBooks().add(book);

        BookDTO result = modelMapper.map(book, BookDTO.class);
        modelMapper.validate();

        assertThat(result.getAuthors()).isEqualTo(Set.of("Author1"));
    }
}