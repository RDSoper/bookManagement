package com.management.bookManagement.DTO;


import com.management.bookManagement.Entities.Author;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class BookDTO {

    private String title;
    private List<String> author;
    private Boolean read;
    private Boolean owned;
    private String genre;
}
