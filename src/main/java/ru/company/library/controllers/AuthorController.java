package ru.company.library.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.library.entyties.Author;
import ru.company.library.helper.ControllerUtils;
import ru.company.library.services.AuthorService;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(authorService.saveAuthor(author));
    }

    @GetMapping("/getByFio")
    public ResponseEntity<?> getAuthorByFio(@RequestParam String fio) {
        return ControllerUtils.handleEntityOperation(() ->
            authorService.findAuthorByFioContainingIgnoreCase(fio),
                "Автор не найден");
    }

    @PutMapping("/updateById")
    public ResponseEntity<?> updateAuthorById(@RequestParam("id") Long id,  // Явно указать имя параметра
                                              @RequestParam("birthDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth,
                                              @RequestParam("fio") String fio){
        return ControllerUtils.handleEntityOperation(() ->
                        authorService.updateAuthorById(id, dateOfBirth, fio),
                "Автор не найден");
    }

    // !!!BEFORE!!! delete author -> delete book with this author
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteAuthorById(@RequestParam Long id){
        return ControllerUtils.handleEntityOperation(() ->
                        authorService.deleteAuthorById(id),
                "Автор не найден");
    }
}