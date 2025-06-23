package ru.company.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.library.entyties.Author;
import ru.company.library.services.AuthorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author){
        return ResponseEntity.ok(authorService.saveAuthor(author));
    }

    @GetMapping("/getByFio")
    public ResponseEntity<Author> getAuthorByFio(@RequestParam String fio){
        return ResponseEntity.ok(authorService.findAuthorByFio(fio));
    }
}
