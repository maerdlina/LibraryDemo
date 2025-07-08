package ru.company.library.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.library.entyties.Author;
import ru.company.library.entyties.Book;
import ru.company.library.helper.ControllerUtils;
import ru.company.library.services.AuthorService;
import ru.company.library.services.BookService;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book, @RequestParam List<Long> authorsId){
        return ResponseEntity.ok(bookService.saveBook(book, authorsId));
    }

    @GetMapping("/getBySubTitle")
    public ResponseEntity<?> getBookBySubTitle(@RequestParam String title){
        return ControllerUtils.handleEntityOperation(() ->
                bookService.findBookByTitleContainingIgnoreCase(title),
                "Книга не найдена"
        );
    }

    @PutMapping("/updateById")
    public ResponseEntity<?> updateBookById(
            @RequestParam("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("authorId") List<Long> authorsId,
            @RequestParam("genre") String genre,
            @RequestParam("circulation") String circulation,
            @RequestParam("price") double price,
            @RequestParam("releaseYear") @DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseYear
    ) {
        return ControllerUtils.handleEntityOperation(() ->
                        bookService.updateBookById(
                                id, title, authorsId, genre, circulation, price, releaseYear
                        ),
                "Книга не найдена"
        );
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteBookById(@RequestParam Long id){
        return ControllerUtils.handleEntityOperation(() ->
                        bookService.deleteBookById(id),
                "Книга не найдена"
        );
    }
}
