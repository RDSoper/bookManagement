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
        List<Author> authors = authorRepository.findAll();
        return authors;
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthor(Long id) {
        return authorRepository.findById(id).get();
    }
}
