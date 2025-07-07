package ru.company.library.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.library.entyties.Author;
import ru.company.library.entyties.Book;
import ru.company.library.repos.AuthorRepo;
import ru.company.library.repos.BookRepo;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;
    //CRUD

    /**
     * Метод сохраняющий нового автора в БД
     * @param author Объект автора
     * @return Возвращает сохраненный объект автора
     */
    public Author saveAuthor(Author author){
        return authorRepo.save(author);
    } //CREATE

    /**
     * Находит автора по его идентификатору.
     *
     * @param id Идентификатор автора
     * @return Найденный объект автора
     * @throws EntityNotFoundException Если автор с указанным ID не найден
     */
    public Author findAuthorById(Long id){
        return authorRepo.findAuthorById(id)
                .orElseThrow(() -> new EntityNotFoundException("Автор с ФИО " + id + " не найден"));
    }

    /**
     * Находит авторов по частичному совпадению ФИО (без учета регистра).
     *
     * @param fio Часть ФИО для поиска
     * @return Найденный объект автора
     * @throws EntityNotFoundException Если автор с указанным ФИО не найден
     */
    public Author findAuthorByFioContainingIgnoreCase(String fio){ //READ
        return authorRepo.findAuthorByFioContainingIgnoreCase(fio)
                .orElseThrow(() -> new EntityNotFoundException("Автор с ФИО " + fio + " не найден"));
    }

    /**
     * Обновляет данные автора по его идентификатору.
     *
     * @param id Идентификатор автора для обновления
     * @param birthDate Новая дата рождения автора
     * @param fio Новое ФИО автора
     * @return Обновленный объект автора
     * @throws EntityNotFoundException Если автор с указанным ID не найден
     */
    public Author updateAuthorById(Long id, Date birthDate, String fio){ ///UPDATE
        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Автор с id " + id + " не найден"));
        author.setBirthDate(birthDate);
        author.setFio(fio);
        return authorRepo.save(author);
    }

    /**
     * Удаляет автора по его идентификатору, предварительно удалив все связи с книгами.
     *
     * @param id Идентификатор автора для удаления
     * @return Сообщение об успешном удалении
     * @throws EntityNotFoundException Если автор с указанным ID не найден
     */
    @Transactional
    public String deleteAuthorById(Long id){
        Author author = authorRepo.findAuthorById(id)
                .orElseThrow(()-> new EntityNotFoundException("Автор с " + id + " не найден"));

        for(Book book: author.getBooks()){
            book.getAuthors().remove(author);
            bookRepo.save(book);
        }

        authorRepo.delete(author);
        return "Автор с ID " + id + "успешно удален!";
    }

}
