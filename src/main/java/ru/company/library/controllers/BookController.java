package ru.company.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.company.library.entyties.Book;
import ru.company.library.services.BookService;

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
    public ResponseEntity<Book> getBookByFio(@RequestParam String name){
        return ResponseEntity.ok(bookService.findBookByName(name));
    }
}
