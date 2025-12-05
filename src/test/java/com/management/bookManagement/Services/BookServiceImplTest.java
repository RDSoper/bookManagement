package com.management.bookManagement.Services;

import com.management.bookManagement.Configuration.ModelMapperConfig;

import com.management.bookManagement.DTO.BookDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Repositories.BookRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(ModelMapperConfig.class)
class BookServiceImplTest {

    BookServiceImpl bookService;

    @MockitoBean
    BookRepository bookRepository;
    @Autowired
    ModelMapper modelMapper;

    Author author1;
    Author author2;
    Author author3;

    Book book1;
    Book book2;
    Book book3;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, modelMapper);

        book1 = new Book(1L, "The book1", new HashSet<>(), true, true, "");
        book2 = new Book(2L, "book2", new HashSet<>(), true, true, "");
        book3 = new Book(3L, "book3", new HashSet<>(), true, true, "");

        author1 = new Author(1L, "author1", new HashSet<>());
        author2 = new Author(2L, "author2", new HashSet<>());
        author3 = new Author(3L, "author3", new HashSet<>());

        author1.addBook(book1);
        author2.addBook(book2);
        author3.addBook(book3);

        book1.addAuthor(author1);
        book2.addAuthor(author2);
        book3.addAuthor(author3);
    }

    @Test
    void testGetAllBooks_getsAllBooks() {
        when(bookRepository.findAllByOrderByTitleAsc()).thenReturn(List.of(book1, book2, book3));

        List<BookDTO> books = bookService.getAllBooks();

        assertThat(books.size()).isEqualTo(3);
        assertThat(books).extracting(BookDTO::getTitle).isEqualTo(List.of("book1, The", "book2", "book3"));
        assertThat(books.getFirst().getAuthors()).containsExactly("author1");
        assertThat(books.get(1).getAuthors()).containsExactly("author2");
        assertThat(books.getLast().getAuthors()).containsExactly("author3");
    }

    @Test
    void testSaveBook_savesBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        BookDTO result = bookService.saveBook(book1);

        verify(bookRepository, times(1)).save(any());
        assertThat(result.getTitle()).isEqualTo("book1, The");
        assertThat(result.getAuthors()).containsExactly("author1");
    }

    @Test
    void testGetBookById_getsBookById() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book1));

        BookDTO result = bookService.getBook(1L);

        assertThat(result.getTitle()).isEqualTo("book1, The");
        assertThat(result.getAuthors()).containsExactly("author1");
    }

    @Test
    void testGetBookById_throwsException_whenNoBookFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> bookService.getBook(1L));
    }

    @Test
    void testDeleteBook_removesBookFromAuthor() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book1));

        bookService.deleteBook(1L);

        assertThat(author1.getBooks()).isEmpty();
    }

    @Test
    void testDeleteBook_throwsException_whenNoBookFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> bookService.deleteBook(1L));
    }

    @Test
    void testGetBookByName_getBookByName() {
        when(bookRepository.findByTitle(anyString())).thenReturn(book1);

        Book result = bookService.getBook(book1.getTitle());

        assertThat(result.getTitle()).isEqualTo("book1, The");
    }
}