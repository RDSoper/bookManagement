package com.management.bookManagement.Utils;

import com.management.bookManagement.DTO.AuthorBookDTO;
import com.management.bookManagement.DTO.AuthorDTO;
import com.management.bookManagement.DTO.BookDTO;
import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Mappers {

    public static BookDTO mapBookToBookDTO (Book book){

        BookDTO bookDTO = new BookDTO();
        List<String> authorNames = new ArrayList<>();

        bookDTO.setTitle(book.getTitle());
        bookDTO.setOwned(book.getOwned());
        bookDTO.setRead(book.getRead());
        bookDTO.setGenre(book.getGenre());

        for(Author author: book.getAuthor()){
            authorNames.add(author.getName());
        }

        bookDTO.setAuthor(authorNames);

        return bookDTO;
    }

    public static AuthorDTO mapAuthorToAuthorDTO (Author author){
        AuthorDTO authorDTO = new AuthorDTO();
        List<AuthorBookDTO> authorBookDTOs = new ArrayList<>();

        authorDTO.setName(author.getName());

        for(Book book: author.getBooks()){
            if(book.getTitle().split(" ")[0].equalsIgnoreCase("the")){
                handleTheInBookTitle(book);
            }
            AuthorBookDTO bookDTO = new AuthorBookDTO();
            System.out.println("Mapping "+ book.getTitle());
            bookDTO.setTitle(book.getTitle());
            bookDTO.setOwned(book.getOwned());
            bookDTO.setRead(book.getRead());
            bookDTO.setGenre(book.getGenre());
            authorBookDTOs.add(bookDTO);
        }

        authorDTO.setBooks(authorBookDTOs);

        return authorDTO;
    }

    private static void handleTheInBookTitle(Book book){
        String[] title = book.getTitle().split(" ");
        if(title[0].equalsIgnoreCase("the")){
            String newTitle = book.getTitle().substring(4) + ", " + title[0];
            book.setTitle(newTitle);
            System.out.println("Book title set to "+ book.getTitle());
        }
    }

}
