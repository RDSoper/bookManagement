package com.management.bookManagement;

import com.management.bookManagement.Entities.Book;
import com.management.bookManagement.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookManagementApplication implements CommandLineRunner {

	@Autowired
	BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookManagementApplication.class, args);
	}

	//TODO: Delete this once the db is made persistent.
	@Override
	public void run(String... args) {
		Book[] books = new Book[]{
				new Book(1L,"A title", "afg", false, true, "Fiction"),
				new Book(2L,"A Great Title", "Author", true, true, "Fiction"),
				new Book(3L,"A Better Title", "Excellent author", false, true, "Fiction"),
				new Book(4L,"A Awesome Title", "afg", false, false, "Non-Fiction"),
				new Book(5L,"A Boring Title", "afg", false, true, "Fiction")

		};
		for(Book book:books){
			bookRepository.save(book);
		}
	}
}
