package ru.company.library.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.company.library.entyties.Author;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {
    /**
     * Метод ищет автора по ФИО
     * @param fio Фамилия Имя Отчество
     * @return возвращает автора
     */
    Author findAuthorByFio(String fio);
}
