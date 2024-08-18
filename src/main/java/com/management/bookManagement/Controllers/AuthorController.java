package com.management.bookManagement.Controllers;

import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;

import static com.management.bookManagement.Utils.Mappers.mapAuthorToAuthorDTO;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/")
public class AuthorController {

    AuthorService authorService;

    @GetMapping("authors")
    public List<AuthorDTO> getAuthors(){
        List<Author> authors =  authorService.getAuthors();
        List<AuthorDTO> authorDTOs = new ArrayList<>();

        for(Author author:authors){
            authorDTOs.add(mapAuthorToAuthorDTO(author));
        }

        return authorDTOs;
    }

    @GetMapping("author/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long id){
        return ResponseEntity.ok(mapAuthorToAuthorDTO(authorService.getAuthor(id)));
    }

    @PostMapping("author")
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        Author newAuthor = authorService.saveAuthor(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }
}
