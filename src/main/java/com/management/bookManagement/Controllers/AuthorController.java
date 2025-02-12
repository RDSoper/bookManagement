package com.management.bookManagement.Controllers;

import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Services.AuthorService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/")
public class AuthorController {

    AuthorService authorService;

    @GetMapping("authors")
    public ResponseEntity<List<AuthorDTO>> getAuthors(){
        List<AuthorDTO> authors =  authorService.getAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("author/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long id){
        AuthorDTO author = authorService.getAuthor(id);
        return ResponseEntity.ok(author);
    }

    @PostMapping("author")
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody Author author) {
        AuthorDTO savedAuthor = authorService.saveAuthor(author);
        return ResponseEntity.ok(savedAuthor);
    }
}
