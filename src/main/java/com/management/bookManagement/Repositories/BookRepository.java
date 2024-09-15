package com.management.bookManagement.Repositories;

import com.management.bookManagement.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByOrderByTitleAsc();
    Book findByTitle(String title);
}
