package com.management.bookManagement.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class AuthorDTO {
    private String name;
    private Set<AuthorBookDTO> books;
}
