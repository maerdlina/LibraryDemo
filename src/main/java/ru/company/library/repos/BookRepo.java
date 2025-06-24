package ru.company.library.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.company.library.entyties.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    /**
     * Метод ищет автора по ФИО
     * @param name Фамилия Имя Отчество
     * @return возвращает найденного автора
     */
    Book findBookByName(String name);
}
