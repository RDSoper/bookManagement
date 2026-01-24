package com.management.bookManagement.Controllers;

import com.management.bookManagement.DTO.BookDTO;
import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Services.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    BookServiceImpl bookService;

    BookDTO book1;
    BookDTO book2;
    BookDTO book3;

    @BeforeEach
    public void setup() {
        book1 = createBook("book1");
        book2 = createBook("book2");
        book3 = createBook("book3");
    }

    @Test
    void getBooks() throws Exception {
        when(bookService.getBooks()).thenReturn(List.of(book1, book2, book3));
        String response = getFile("bookController/bookControllerResponses/getBooks.json");

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(response));
    }

    @Test
    void getBook() throws Exception {
        when(bookService.getBook(1L)).thenReturn(book1);
        String response = getFile("bookController/bookControllerResponses/getBook.json");

        mvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(response));
    }

    @Test
    void saveBook() throws Exception {
        String book = getFile("bookController/bookControllerBodies/saveBook.json");
        String response = getFile("bookController/bookControllerResponses/saveBook.json");
        when(bookService.saveBook(any(Book.class))).thenReturn(book1);

        mvc.perform(
                post("/")
                    .contentType("application/json")
                    .content(book))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(response));
    }

    @Test
    void deleteBook() throws Exception {
        mvc.perform(delete("/1"))
                .andExpect(status().isNoContent());
    }

    private BookDTO createBook(String title) {
        Set<String> author = new HashSet<>();
        author.add(title + " author");

        BookDTO book = new BookDTO();
        book.setTitle(title);
        book.setAuthors(author);

        return book;
    }

    private String getFile(String filename) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            return new String(inputStream.readAllBytes());
        }
    }

}