package ru.company.library.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.company.library.entyties.Book;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    /**
     * Метод ищет данные о книге по названию
     * @param title Название книги
     * @return возвращает информацию о найденной книге по названию
     */
    Optional<Book> findBookByTitle(String title);

    /**
     * Метод ищет данные о книге по полному названию или части
     * @param title Название книги
     * @return возвращает информацию о найденной книге
     */
    Optional<Book> findBookByTitleContainingIgnoreCase(String title);

}
