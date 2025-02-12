package com.management.bookManagement.Configuration;

import com.management.bookManagement.DTO.AuthorBookDTO;
import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.DTO.BookDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Set<Author>, Set<String>> bookAuthorConverter = new Converter<>() {
            @Override
            public Set<String> convert(MappingContext<Set<Author>, Set<String>> context) {
                Set<Author> authors = context.getSource();
                return mapAuthorToDto(authors);
            }
        };

        Converter<Author, AuthorDTO> authorBookConverter = new Converter<Author, AuthorDTO>() {
            @Override
            public AuthorDTO convert(MappingContext<Author, AuthorDTO> context) {
                Author author = context.getSource();
                AuthorDTO authorDTO = new AuthorDTO();
                authorDTO.setName(author.getName());
                authorDTO.setBooks(mapAuthorBooksToAuthorBooksDto(author.getBooks()));
                return authorDTO;
            }
        };

        modelMapper.addConverter(bookAuthorConverter);
        modelMapper.addConverter(authorBookConverter);

        modelMapper.typeMap(Book.class, BookDTO.class).addMappings(mapper -> {
            mapper.using(bookAuthorConverter).map(Book::getAuthors, BookDTO::setAuthors);
            mapper.map(Book::getTitle, (bookDTO, o) -> bookDTO.setTitle(handleTheInBookTitle((String) o)));
        });

        modelMapper.typeMap(Author.class, AuthorDTO.class).addMappings(mapper ->{
            mapper.using(authorBookConverter).map(Author::getBooks, AuthorDTO::setBooks);
        });


        return modelMapper;
    }

    private Set<String> mapAuthorToDto(Set<Author> authors) {
        Set<String> authorNames = new HashSet<>();
        if (authors != null) {
            for (Author author : authors) {
                authorNames.add(author.getName());
            }
        }
        return authorNames;
    }

    private Set<AuthorBookDTO> mapAuthorBooksToAuthorBooksDto(Set<Book> books) {
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
        return (title != null && !title.isEmpty() && title.toLowerCase().startsWith("the "))
                ? title.substring(4) + ", The"
                : title;
    }
}
