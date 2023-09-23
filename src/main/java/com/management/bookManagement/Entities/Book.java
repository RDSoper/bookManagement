package com.management.bookManagement.Entities;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;


    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
    private List<Author> author;

    @Column(name = "read")
    private Boolean read;

    @Column(name = "owned")
    private Boolean owned;

    @Column(name = "genre")
    private String genre;


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
        Book other = (Book) obj;
        return id != null && id.equals(other.getId());
    }
}
