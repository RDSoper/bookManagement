package com.management.bookManagement;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@AllArgsConstructor
public class BookManagementApplication{
	public static void main(String[] args) {
		SpringApplication.run(BookManagementApplication.class, args);
	}
}
