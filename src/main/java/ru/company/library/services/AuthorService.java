package ru.company.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.library.entyties.Author;
import ru.company.library.repos.AuthorRepo;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepo authorRepo;

    public Author saveAuthor(Author author){
        return authorRepo.save(author);
    }

    public Author findAuthorByFio(String fio){
        return authorRepo.findAuthorByFio(fio);
    }
}
