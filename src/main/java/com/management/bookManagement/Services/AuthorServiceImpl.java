package com.management.bookManagement.Services;


import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Repositories.AuthorRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;
    ModelMapper modelMapper;

    @Override
    public List<AuthorDTO> getAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        for(Author author:authors){
            authorDTOs.add(modelMapper.map(author, AuthorDTO.class));
        }
        return authorDTOs;
    }

    @Override
    public AuthorDTO saveAuthor(Author author) {
        Author savedAuthor = authorRepository.save(author);
        return modelMapper.map(savedAuthor, AuthorDTO.class);
    }

    @Override
    public AuthorDTO getAuthor(Long id) {
        return modelMapper.map(authorRepository.findById(id).orElseThrow(), AuthorDTO.class);
    }

    @Override
    public AuthorDTO getAuthorByName(String name) {
        return modelMapper.map(authorRepository.findByNameIs(name), AuthorDTO.class);
    }
}
