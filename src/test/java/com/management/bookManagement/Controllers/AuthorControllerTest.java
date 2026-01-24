package com.management.bookManagement.Controllers;

import com.management.bookManagement.DTO.AuthorBookDTO;
import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Services.AuthorServiceImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    AuthorServiceImpl authorService;

    AuthorDTO author1;
    AuthorDTO author2;
    AuthorDTO author3;

    @BeforeEach
    public void setup() {
        author1 = setupAuthor("author1");
        author2 = setupAuthor("author2");
        author3 = setupAuthor("author3");

    }

    @Test
    void getAuthors() throws Exception {
        when(authorService.getAuthors()).thenReturn(List.of(author1, author2, author3));
        String expectedJson = getFile("AuthorController/authorControllerResponses/getAuthors.json");

        mvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void getAuthor() throws Exception {
        when(authorService.getAuthor(1L)).thenReturn(author1);
        String expectedJson = getFile("AuthorController/authorControllerResponses/getAuthor.json");

        mvc.perform(get("/author/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void saveAuthor() throws Exception {
        when(authorService.saveAuthor(any(Author.class))).thenReturn(author1);
        String expectedJson = getFile("AuthorController/authorControllerResponses/getAuthor.json");
        String author = getFile("AuthorController/authorControllerBodies/saveAuthor.json");

        mvc.perform(post("/author")
                        .contentType("application/json")
                        .content(author))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson));
    }

    private AuthorDTO setupAuthor(String name) {
        AuthorBookDTO book = new AuthorBookDTO();
        book.setTitle(name + " book");

        Set<AuthorBookDTO> books = new HashSet<>();
        books.add(book);

        AuthorDTO author = new AuthorDTO();
        author.setName(name);
        author.setBooks(books);

        return author;
    }

    private String getFile(String filename) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            return new String(inputStream.readAllBytes());
        }
    }
}