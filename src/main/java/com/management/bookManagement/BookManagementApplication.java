package com.management.bookManagement;

import com.management.bookManagement.Entities.Author;
import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Repositories.AuthorRepository;
import com.management.bookManagement.Repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

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
		List<Author> ana = new ArrayList<>();
		List<Book> booklist = new ArrayList<>();

		Book boook = new Book(6L, "A", null, true, false, "Genre");
		booklist.add(boook);

		Author russell = new Author(1L, "Guillermo Del Toro", booklist);
		Author anotherRussell = new Author(2L, "Chuck Hogan", booklist);
		ana.add(russell);
		ana.add(anotherRussell);
		boook.setAuthor(ana);

		Book[] books = new Book[]{
				new Book(1L,"The Strain", null, true, true, "Fiction"),
				new Book(2L,"The Fall", null, true, true, "Fiction"),
				new Book(3L,"The Night Eternal", null, true, true, "Fiction"),
				new Book(4L,"A Awesome Title", null, false, false, "Non-Fiction"),
				new Book(5L,"A Boring Title", null, false, true, "Fiction")

		};


		authorRepository.save(russell);
		for(Book book:books){
			book.setAuthor(ana);
			russell.getBooks().add(book);
			bookRepository.save(book);
			authorRepository.save(russell);
		}

	}
}
