package com.management.bookManagement.Services;

import com.management.bookManagement.Entities.Author;

import java.util.List;


public interface AuthorService {

    List<Author> getAuthors();
    Author saveAuthor(Author author);
    Author getAuthor(Long id);
}
