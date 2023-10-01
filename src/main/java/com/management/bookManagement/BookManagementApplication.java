package com.management.bookManagement;

import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Repositories.AuthorRepository;
import com.management.bookManagement.Repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;


@SpringBootApplication
@AllArgsConstructor
public class BookManagementApplication implements CommandLineRunner{

	BookRepository bookRepository;

	AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookManagementApplication.class, args);
	}

	//TODO: Delete this once the db is made persistent.
	@Override
	public void run(String... args) {
		Author russell = new Author(1L, "Guillermo Del Toro", new HashSet<>());
		Book[] books = new Book[]{
				new Book(1L,"The Strain", new HashSet<>(), true, true, "Fiction"),
				new Book(2L,"The Fall", new HashSet<>(), true, true, "Fiction"),
				new Book(3L,"The Night Eternal", new HashSet<>(), true, true, "Fiction"),
				new Book(4L,"A Awesome Title", new HashSet<>(), false, false, "Non-Fiction"),
				new Book(5L,"A Boring Title", new HashSet<>(), false, true, "Fiction")

		};


		authorRepository.save(russell);
		for(Book book:books){
			book.addAuthor(russell);
			russell.addBook(book);
			bookRepository.save(book);
		}
		authorRepository.save(russell);

	}
}
