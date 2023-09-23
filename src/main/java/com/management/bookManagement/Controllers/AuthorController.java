package com.management.bookManagement.Controllers;

import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("author")
public class AuthorController {

    AuthorService authorService;

    @GetMapping("/all")
    public List<Author> getAuthors(){
        return authorService.getAuthors();
    }
}
