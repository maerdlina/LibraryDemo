package ru.company.library.entyties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Books")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "circulation")
    private String circulation; //тираж

    @Column(name = "price")
    private double price;

    @Column(name = "releaseYear")
    private Date releaseYear;

    @ManyToMany(mappedBy = "books", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Author> authors = new HashSet<>();
}
