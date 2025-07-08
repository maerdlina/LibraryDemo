package ru.company.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.library.entyties.Author;
import ru.company.library.helper.ControllerUtils;
import ru.company.library.services.AuthorService;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    /**
     * Метод создания нового автора
     * @param author Объект с данными об авторе (в формате JSON)
     * @return Возвращает ответ о сохранении автора
     */
    @PostMapping
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(authorService.saveAuthor(author));
    }

    /**
     * Метод ищет автора по частичному совпадению ФИО (без учета регистра).
     * @param fio Часть или полные Фамилия Имя Отчество
     * @return Возвращает данные о найденном авторе или сообщение об ошибке
     */
    @GetMapping("/getByFio")
    public ResponseEntity<?> getAuthorByFio(@RequestParam String fio) {
        return ControllerUtils.handleEntityOperation(() ->
            authorService.findAuthorByFioContainingIgnoreCase(fio),
                "Автор не найден");
    }

    /**
     * Метод обновляет данные о авторе по его идентификатору
     * @param id Id автора
     * @param dateOfBirth Новая дата рождения автора
     * @param fio Новоые Фамилия Имя Отчество
     * @return Возвращает обновленные данные или сообщение об ошибке
     */
    @PutMapping("/updateById")
    public ResponseEntity<?> updateAuthorById(@RequestParam("id") Long id,  // Явно указать имя параметра
                                              @RequestParam("birthDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth,
                                              @RequestParam("fio") String fio){
        return ControllerUtils.handleEntityOperation(() ->
                        authorService.updateAuthorById(id, dateOfBirth, fio),
                "Автор не найден");
    }

    /**
     * Метод удаляет данных об авторе по его идентификатору
     * @param id Id автора
     * @return Возвращает статус ответа 200 или сообщение об ошибке
     */
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteAuthorById(@RequestParam Long id){
        return ControllerUtils.handleEntityOperation(() ->
                        authorService.deleteAuthorById(id),
                "Автор не найден");
    }
}