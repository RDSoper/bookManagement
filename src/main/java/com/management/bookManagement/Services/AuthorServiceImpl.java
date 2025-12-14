package com.management.bookManagement.Services;


import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Repositories.AuthorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
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
        Author presentAuthor = authorRepository.findByNameIs(author.getName());
        if (presentAuthor != null) {
            log.info("Author already exists, updating...");

            if (author.getBooks() != null) {
                for (Book book : author.getBooks()) {
                    presentAuthor.addBook(book);
                }
            }
            Author savedAuthor = authorRepository.save(presentAuthor);
            return modelMapper.map(savedAuthor, AuthorDTO.class);
        }

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
