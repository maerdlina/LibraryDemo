package ru.company.library.entyties;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Authors")
public class Author {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "fio")
    private String fio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthDate")
    private Date birthDate;

    @ManyToMany
    @JoinTable(
            name="author_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<>();;
}
