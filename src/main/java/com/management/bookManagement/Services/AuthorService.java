package com.management.bookManagement.Services;

import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.Entities.Author;

import java.util.List;


public interface AuthorService {

    List<AuthorDTO> getAuthors();
    AuthorDTO saveAuthor(Author author);
    AuthorDTO getAuthor(Long id);
    Author getAuthorByName(String name);
}
