package com.management.bookManagement.Configuration;

import com.management.bookManagement.DTO.AuthorBookDTO;
import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.DTO.BookDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Set<Author>, Set<String>> bookAuthorToAuthorNameConverter = context -> {
            Set<Author> authors = context.getSource();
            return mapAuthorToAuthorDto(authors);
        };

        Converter<Set<Book>, Set<AuthorBookDTO>> authorBooksConverter = context -> {
            Set<Book> books = context.getSource();
            return mapBookToAuthorBookDTOConverter(books);
        };

        Converter<String, String> bookTitleConverter = context -> {
            String title = context.getSource();
            return handleTheInBookTitle(title);
        };

        modelMapper.addConverter(bookAuthorToAuthorNameConverter);
        modelMapper.addConverter(authorBooksConverter);
        modelMapper.addConverter(bookTitleConverter);

        modelMapper.typeMap(Book.class, BookDTO.class).addMappings(mapper -> {
            mapper.using(bookAuthorToAuthorNameConverter).map(Book::getAuthors, BookDTO::setAuthors);
            mapper.using(bookTitleConverter).map(Book::getTitle, BookDTO::setTitle);
        });

        modelMapper.typeMap(Author.class, AuthorDTO.class).addMappings(mapper ->
            mapper.using(authorBooksConverter).map(Author::getBooks, AuthorDTO::setBooks));

        return modelMapper;
    }

    private Set<String> mapAuthorToAuthorDto(Set<Author> authors) {
        Set<String> authorNames = new HashSet<>();
        if (authors != null) {
            for (Author author : authors) {
                authorNames.add(author.getName());
            }
        }
        return authorNames;
    }

    private Set<AuthorBookDTO> mapBookToAuthorBookDTOConverter(Set<Book> books) {
        Set<AuthorBookDTO> authorBooks = new HashSet<>();
        if(books != null) {
            for(Book book: books) {
                AuthorBookDTO authorBookDTO = new AuthorBookDTO();
                authorBookDTO.setTitle(handleTheInBookTitle(book.getTitle()));
                authorBookDTO.setRead(book.getRead());
                authorBookDTO.setOwned(book.getOwned());
                authorBookDTO.setGenre(book.getGenre());
                authorBooks.add(authorBookDTO);
            }
            return authorBooks;
        }
        return authorBooks;
    }

    private String handleTheInBookTitle(String title) {
        return (title != null && !title.isBlank() && title.toLowerCase().startsWith("the "))
                ? title.substring(4) + ", The"
                : title;
    }
}
