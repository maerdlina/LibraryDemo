package ru.company.library.repos;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.company.library.entyties.Book;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    /**
     * Метод ищет данные о книге по названию
     * @param name Название книги
     * @return возвращает информацию о найденной книге по названию
     */
    Optional<Book> findBookByName(String name);

    /**
     * Метод ищет данные о книге по полному названию или части
     * @param name Название книги
     * @return возвращает информацию о найденной книге
     */
    Optional<Book> findBookByNameContainingIgnoreCase(String name);

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Book b WHERE b.name = :name")
//    int deleteByName(@Param("name") String name);
}
