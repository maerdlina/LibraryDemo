package ru.company.library.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.library.entyties.Author;
import ru.company.library.entyties.Book;
import ru.company.library.repos.AuthorRepo;
import ru.company.library.repos.BookRepo;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;
    //CRUD

    public Author saveAuthor(Author author){
        return authorRepo.save(author);
    } //CREATE

    public Author findAuthorById(Long id){
        return authorRepo.findAuthorById(id)
                .orElseThrow(() -> new EntityNotFoundException("Автор с ФИО " + id + " не найден"));
    }

    public Author findAuthorByFioContainingIgnoreCase(String fio){ //READ
        return authorRepo.findAuthorByFioContainingIgnoreCase(fio)
                .orElseThrow(() -> new EntityNotFoundException("Автор с ФИО " + fio + " не найден"));
    }

    public Author updateAuthorById(Long id, Date birthDate, String fio){ ///UPDATE
        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Автор с id " + id + " не найден"));
        author.setBirthDate(birthDate);
        author.setFio(fio);
        return authorRepo.save(author);
    }

    @Transactional
    public String deleteAuthorById(Long id){
        Author author = authorRepo.findAuthorById(id)
                .orElseThrow(()-> new EntityNotFoundException("Автор с " + id + " не найден"));

        for(Book book: author.getBooks()){
            book.getAuthors().remove(author);
            bookRepo.save(book);
        }

        authorRepo.delete(author);
        return "Автор с ID " + id + "успешно удален!";
    }

}
