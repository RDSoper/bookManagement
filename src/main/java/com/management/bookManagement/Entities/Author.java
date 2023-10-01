package com.management.bookManagement.Entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "fk_author"),
            inverseJoinColumns = @JoinColumn(name = "fk_book"))
    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Book> books  = new HashSet<>();


    public void addBook(Book book) {
        if(this.books == null ){
            this.books = new HashSet<>();
        }
        this.books.add(book);
        book.getAuthors().add(this);
    }

    public void removeBook(Book book){
        this.books.remove(book);
        book.getAuthors().remove(this);
    }

    // The hashcode and equals methods were guided by this article
    // https://thorben-janssen.com/ultimate-guide-to-implementing-equals-and-hashcode-with-hibernate/
    @Override
    public int hashCode() {
        return 13;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Author other = (Author) obj;
        return id != null && id.equals(other.getId());
    }
}
