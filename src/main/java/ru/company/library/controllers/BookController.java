package ru.company.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.library.entyties.Book;
import ru.company.library.helper.ControllerUtils;
import ru.company.library.services.AuthorService;
import ru.company.library.services.BookService;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    /**
     * Метод сохраняющий новые книги и связывающий их с авторами
     * @param book Объект с данными о книге (в формате JSON)
     * @param authorsId Список идентифиакторов авторов, к которым относится книга
     * @return Возвращает данные о сохраненной книге
     */
    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book, @RequestParam List<Long> authorsId){
        return ResponseEntity.ok(bookService.saveBook(book, authorsId));
    }

    /**
     * Метод ищущий книги по частичному совпадению названия (без учета регистра)
     * @param title Часть названия (или полное) книги для поиска
     * @return Возвращает данные о найденной книге или сообщение об ошибке
     */
    @GetMapping("/getBySubTitle")
    public ResponseEntity<?> getBookBySubTitle(@RequestParam String title){
        return ControllerUtils.handleEntityOperation(() ->
                bookService.findBookByTitleContainingIgnoreCase(title),
                "Книга не найдена"
        );
    }

    /**
     * Метод обновляющий данные о книге
     * @param id Идентификатор
     * @param title Новое название
     * @param authorsId Список новых идентифиакторов авторов
     * @param genre Новый жанр
     * @param circulation Новый тираж
     * @param price Новая цена
     * @param releaseYear Новая дата выпуска
     * @return Возвращает обновленные данные или сообщение об ошибке
     */
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

    /**
     * Метод удаляющий данные о книге по ее идентифиактору
     * @param id Идентифиактор книги
     * @return Возвращает ответ о результате удаления
     */
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteBookById(@RequestParam Long id){
        return ControllerUtils.handleEntityOperation(() ->
                        bookService.deleteBookById(id),
                "Книга не найдена"
        );
    }
}
