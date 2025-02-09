package com.management.bookManagement.DTO;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BookAuthorDTO {
    private String title;
    private String author;
    private Boolean read;
    private Boolean owned;
    private String genre;
}
