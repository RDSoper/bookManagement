package com.management.bookManagement.Repositories;


import com.management.bookManagement.Entities.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
