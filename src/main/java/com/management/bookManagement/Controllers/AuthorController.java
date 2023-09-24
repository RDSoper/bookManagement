package com.management.bookManagement.Controllers;

import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.management.bookManagement.Utils.Mappers.mapAuthorToAuthorDTO;

import java.util.ArrayList;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("author")
public class AuthorController {

    AuthorService authorService;

    @GetMapping("/all")
    public List<AuthorDTO> getAuthors(){
        List<Author> authors =  authorService.getAuthors();
        List<AuthorDTO> authorDTOs = new ArrayList<>();

        for(Author author:authors){
            authorDTOs.add(mapAuthorToAuthorDTO(author));
        }

        return authorDTOs;
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthor(@PathVariable Long id){
        return mapAuthorToAuthorDTO(authorService.getAuthor(id));
    }
}
