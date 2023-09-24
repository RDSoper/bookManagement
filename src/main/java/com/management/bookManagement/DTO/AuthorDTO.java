package com.management.bookManagement.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class AuthorDTO {
    private String name;
    private List<AuthorBookDTO> books;
}
