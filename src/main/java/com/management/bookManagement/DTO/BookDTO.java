package com.management.bookManagement.DTO;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class BookDTO {

    private String title;
    private Set<String> authors;
    private Boolean read;
    private Boolean owned;
    private String genre;
}
