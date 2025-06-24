package ru.company.library.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.company.library.entyties.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    /**
     * Метод ищет данные о книге по названию
     * @param name Название книги
     * @return возвращает информацию о найденной книге по названию
     */
    Book findBookByName(String name);

    /**
     * Метод ищет данные о книге по полному названию или части
     * @param name Название книги
     * @return возвращает информацию о найденной книге
     */
    Book findBookByNameContainingIgnoreCase(String name);
}
