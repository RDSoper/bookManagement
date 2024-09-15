package com.management.bookManagement.Controllers;

import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Services.AuthorService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/")
public class AuthorController {

    AuthorService authorService;

    ModelMapper modelMapper;

    @GetMapping("authors")
    public ResponseEntity<List<AuthorDTO>> getAuthors(){
        List<Author> authors =  authorService.getAuthors();

        List<AuthorDTO> authorDTOs = new ArrayList<>();
        for(Author author:authors){
            authorDTOs.add(modelMapper.map(author, AuthorDTO.class));
        }

        return ResponseEntity.ok(authorDTOs);
    }

    @GetMapping("author/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long id){
        Author author = authorService.getAuthor(id);
        return ResponseEntity.ok(modelMapper.map(author, AuthorDTO.class));
    }

    @PostMapping("author")
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody Author author) {
        Author newAuthor = authorService.saveAuthor(author);
        return new ResponseEntity<>(modelMapper.map(newAuthor, AuthorDTO.class), HttpStatus.CREATED);
    }
}
