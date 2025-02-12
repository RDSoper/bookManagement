package com.management.bookManagement.Services;


import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Repositories.AuthorRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;
    ModelMapper modelMapper;

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public AuthorDTO getAuthor(Long id) {
        return modelMapper.map(authorRepository.findById(id).orElseThrow(), AuthorDTO.class);
    }

    @Override
    public Author getAuthorByName(String name) {
        return authorRepository.findByNameIs(name);
    }
}
