package ru.company.library.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.library.entyties.Author;
import ru.company.library.entyties.Book;
import ru.company.library.services.BookService;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book){
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @GetMapping("/getByName")
    public ResponseEntity<?> getBookByName(@RequestParam String name){
//        return ResponseEntity.ok(bookService.findBookByName(name));
        try {
            Book book = bookService.findBookByName(name);
            return ResponseEntity.ok(book);
        } catch (EntityNotFoundException ex) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "success");
            response.put("message", "Книга не найдена");
            response.put("details", ex.getMessage());
            response.put("timestamp", Instant.now());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/getBySubName")
    public ResponseEntity<?> getBookBySubName(@RequestParam String name){
        return ResponseEntity.ok(bookService.findBookByNameContainingIgnoreCase(name));
    }
}
