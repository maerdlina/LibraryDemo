package ru.company.library.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.library.entyties.Author;
import ru.company.library.entyties.Book;
import ru.company.library.helper.ControllerUtils;
import ru.company.library.services.BookService;

import java.time.Instant;
import java.util.Date;
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

//    @GetMapping("/getByName")
//    public ResponseEntity<?> getBookByName(@RequestParam String name){
////        return ResponseEntity.ok(bookService.findBookByName(name));
//        try {
//            Book book = bookService.findBookByName(name);
//            return ResponseEntity.ok(book);
//        } catch (EntityNotFoundException ex) {
//            Map<String, Object> response = new LinkedHashMap<>();
//            response.put("status", "success");
//            response.put("message", "Книга не найдена");
//            response.put("details", ex.getMessage());
//            response.put("timestamp", Instant.now());
//            return ResponseEntity.ok(response);
//        }
//    }

    @GetMapping("/getBySubName")
    public ResponseEntity<?> getBookBySubName(@RequestParam String name){
        return ControllerUtils.handleEntityOperation(() ->
                bookService.findBookByNameContainingIgnoreCase(name),
                "Книга не найдена"
        );
    }

    @PutMapping("/updateById")
    public ResponseEntity<?> updateBookById(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("authorId") Long authorId,
            @RequestParam("genre") String genre,
            @RequestParam("circulation") String circulation,
            @RequestParam("price") double price,
            @RequestParam("releaseYear") @DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseYear
    ) {
        try {
            Book book = bookService.updateBookById(
                    id, name, authorId, genre, circulation, price, releaseYear
            );
            return ResponseEntity.ok(book);
        } catch (EntityNotFoundException ex) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "success");
            response.put("message", "Ошибка обновления");
            response.put("details", ex.getMessage());
            response.put("timestamp", Instant.now());
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteBookById(@RequestParam Long id){
        return ControllerUtils.handleEntityOperation(() ->
                        bookService.deleteBookById(id),
                "Книга не найдена"
        );
    }
}
