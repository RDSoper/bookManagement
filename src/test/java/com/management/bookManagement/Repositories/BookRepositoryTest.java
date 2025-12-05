package com.management.bookManagement.Repositories;

import com.management.bookManagement.Entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setup() {
        Book book1 = new Book(null, "A book that should be first", null, null, null, "Should come first");
        Book book2 = new Book(null, "The book that should be third", null, null, null, "Should come third");
        Book book3 = new Book(null, "Something that should not be first", null, null, null, "Should come second");

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
    }

    @Test
    void testGetAllBooks_OrdersBooksByTitle(){
        List<Book> results = bookRepository.findAllByOrderByTitleAsc();

        assertThat(results).extracting(Book::getTitle).isEqualTo(List.of("A book that should be first", "Something that should not be first", "The book that should be third"));
    }

    @Test
    void testFindByTitle_findsTheRightBook(){
        Book book = bookRepository.findByTitle("The book that should be third");
        System.out.println(book.getAuthors());
        assertEquals(book.getTitle(), "The book that should be third");
    }

}

