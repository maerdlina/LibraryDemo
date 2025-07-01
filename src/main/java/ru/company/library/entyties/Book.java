package ru.company.library.entyties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Книги")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "название")
    private String name;

//    @OneToOne
//    private Author author;

    private String genre;

    private String circulation; //тираж

    private double price;

    private Date releaseYear;
}
