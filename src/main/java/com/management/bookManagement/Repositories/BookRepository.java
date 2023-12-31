package com.management.bookManagement.Repositories;

import com.management.bookManagement.Entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAllByOrderByTitleAsc();
    Book findByTitle(String title);
}
