package ru.company.library.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.company.library.entyties.Author;

import java.util.Optional;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {
    /**
     * Метод ищет автора по ФИО
     * @param fio Фамилия Имя Отчество
     * @return возвращает автора по его ФИО
     */
    Optional<Author> findAuthorByFio(String fio);

    /**
     *
     * @param id Идентификтора автора
     * @return Возвращает автора по его идентификатору
     */
    Optional<Author> findAuthorById(Long id);

    /**
     *
     * @param fio Фамилия Имя Отчество (или часть)
     * @return Возвращает автора с нужнм ФИО или частью ФИО
     */
    Optional<Author> findAuthorByFioContainingIgnoreCase(String fio);
}
