package com.management.bookManagement.Repositories;


import com.management.bookManagement.Entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
