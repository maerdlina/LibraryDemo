package ru.company.library.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.library.entyties.Author;
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
        try {
            Author author = authorService.findAuthorByFio(fio);
            return ResponseEntity.ok(author);
        } catch (EntityNotFoundException ex) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "success");
            response.put("message", "Автор не найден");
            response.put("details", ex.getMessage());
            response.put("timestamp", Instant.now());
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/updateById")
    public ResponseEntity<?> updateAuthorById(@RequestParam("id") Long id,  // Явно указать имя параметра
                                              @RequestParam("birthDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth,
                                              @RequestParam("fio") String fio){
        try {
            Author author = authorService.updateAuthorById(id, dateOfBirth, fio);
            return ResponseEntity.ok(author);
        } catch (EntityNotFoundException ex) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "success");
            response.put("message", "Автор не найден");
            response.put("details", ex.getMessage());
            response.put("timestamp", Instant.now());
            return ResponseEntity.ok(response);
        }
    }

    // !!!BEFORE!!! delete author -> delete book with this author
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteAuthorById(@RequestParam Long id){
        try {
            return ResponseEntity.ok(authorService.deleteAuthorById(id));
        } catch (EntityNotFoundException ex) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "success");
            response.put("message", "Автор не найден");
            response.put("details", ex.getMessage());
            response.put("timestamp", Instant.now());
            return ResponseEntity.ok(response);
        }
    }
}