package com.management.bookManagement.Services;

import com.management.bookManagement.Configuration.ModelMapperConfig;
import com.management.bookManagement.DTO.AuthorBookDTO;
import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(ModelMapperConfig.class)
class AuthorServiceImplTest {

    @MockitoBean
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;

    private AuthorServiceImpl authorService;

    Author author1;
    Author author2;
    Author author3;

    Book book1;
    Book book2;
    Book book3;

    @BeforeEach
    void setUp() {
        authorService = new AuthorServiceImpl(authorRepository, modelMapper);

        author1 = new Author(1L, "author1", new HashSet<>());
        author2 = new Author(2L, "author2", new HashSet<>());
        author3 = new Author(3L, "author3", new HashSet<>());

        book1 = new Book(1L, "book1", new HashSet<>(), true, true, "" );
        book2 = new Book(2L, "book2", new HashSet<>(), true, true, "" );
        book3 = new Book(3L, "book3", new HashSet<>(), true, true, "" );

        author1.addBook(book1);
        author1.addBook(book2);

        book2.addAuthor(author1);
        book1.addAuthor(author1);

        author2.addBook(book2);
        author2.addBook(book3);

        book3.addAuthor(author2);
        book2.addAuthor(author2);

        author3.addBook(book1);
        author3.addBook(book3);

        book1.addAuthor(author3);
        book3.addAuthor(author3);
    }

    @Test
    void testGetAuthors_returnsAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(author1, author2, author3));

        List<AuthorDTO> authors = authorService.getAuthors();

        assertThat(authors.size()).isEqualTo(3);
        assertThat(authors).extracting(AuthorDTO::getName).isEqualTo(List.of("author1", "author2", "author3"));
        assertThat(authors.getFirst().getBooks()).extracting(AuthorBookDTO::getTitle).containsExactlyInAnyOrderElementsOf(List.of("book1", "book2"));
        assertThat(authors.get(1).getBooks()).extracting(AuthorBookDTO::getTitle).containsExactlyInAnyOrderElementsOf(List.of("book2", "book3"));
        assertThat(authors.getLast().getBooks()).extracting(AuthorBookDTO::getTitle).containsExactlyInAnyOrderElementsOf(List.of("book1", "book3"));

    }

    @Test
    void testSaveAuthor_savesAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author1);

        AuthorDTO result = authorService.saveAuthor(author1);

        assertThat(result.getName()).isEqualTo("author1");
        assertThat(result.getBooks()).extracting(AuthorBookDTO::getTitle).containsExactlyInAnyOrderElementsOf(List.of("book1", "book2"));
    }

    @Test
    void testGetAuthor_getsAuthor() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author1));

        AuthorDTO result = authorService.getAuthor(1L);
        assertThat(result.getName()).isEqualTo("author1");
        assertThat(result.getBooks()).extracting(AuthorBookDTO::getTitle).containsExactlyInAnyOrderElementsOf(List.of("book1", "book2"));
    }
    @Test
    void testGetAuthorByName_getsAuthorByName() {
        when(authorRepository.findByNameIs(anyString())).thenReturn(author1);

        AuthorDTO result = authorService.getAuthorByName("author1");

        assertThat(result.getName()).isEqualTo("author1");
        assertThat(result.getBooks()).extracting(AuthorBookDTO::getTitle).containsExactlyInAnyOrderElementsOf(List.of("book1", "book2"));
    }

    @Test
    void testGetAuthorByNameThrowsError_whenNoAuthorFound() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> authorService.getAuthor(any()));
    }
}