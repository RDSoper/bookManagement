package com.management.bookManagement.Services;


import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Repositories.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;

    @Override
    public List<Author> getAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }
}
