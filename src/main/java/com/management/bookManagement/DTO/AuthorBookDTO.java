package com.management.bookManagement.DTO;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AuthorBookDTO {

    private String title;
    private Boolean read;
    private Boolean owned;
    private String genre;
}
