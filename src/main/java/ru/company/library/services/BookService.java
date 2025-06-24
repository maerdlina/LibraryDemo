package ru.company.library.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.library.entyties.Book;
import ru.company.library.repos.BookRepo;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;

    public Book saveBook(Book author){
        return bookRepo.save(author);
    }

    public Book findBookByName(String name){
        return bookRepo.findBookByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Книга с названием " + name + " не найден")
                );
    }
    public Book findBookByNameContainingIgnoreCase(String name){
        return bookRepo.findBookByNameContainingIgnoreCase(name)
            .orElseThrow(() -> new EntityNotFoundException("Автор с названием " + name + " не найдена"));
    }
}
