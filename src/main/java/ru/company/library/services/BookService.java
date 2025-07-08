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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;

    /**
     * Сохраняет новую книгу в базе данных и связывает ее с указанными авторами.
     *
     * @param book Объект книги для сохранения
     * @param authorsId Список идентификаторов авторов книги
     * @return Сохраненный объект книги
     * @throws EntityNotFoundException Если хотя бы один автор не найден
     */
    public Book saveBook(Book book, List<Long> authorsId){
        Set<Author> authors = new HashSet<>();
        for(Long authorId: authorsId){
            Author author = authorRepo.findAuthorById(authorId)
                    .orElseThrow(() -> new EntityNotFoundException("Автор с " + authorId + " не найден"));
            authors.add(author);
            author.getBooks().add(book);
        }
        book.setAuthors(authors);
        return bookRepo.save(book);
    }

    /**
     * Находит книги по частичному совпадению названия (без учета регистра).
     *
     * @param title Часть названия для поиска
     * @return Найденный объект книги
     * @throws EntityNotFoundException Если книга с указанным названием не найдена
     */
    public Book findBookByTitleContainingIgnoreCase(String title){
        return bookRepo.findBookByTitleContainingIgnoreCase(title)
            .orElseThrow(() -> new EntityNotFoundException("Книга с названием " + title + " не найдена"));
    }

    /**
     * Обновляет данные книги по ее идентификатору и связывает с новыми авторами.
     *
     * @param id Идентификатор книги для обновления
     * @param title Новое название книги
     * @param authorsId Список новых идентификаторов авторов
     * @param genre Новый жанр
     * @param circulation Новый тираж
     * @param price Новая цена
     * @param releaseYear Новый год выпуска
     * @return Обновленный объект книги
     * @throws EntityNotFoundException Если книга или хотя бы один автор не найдены
     */
    public Book updateBookById(
            Long id,
            String title,
            List<Long> authorsId,
            String genre,
            String circulation,
            double price,
            Date releaseYear
    ) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Книга с id " + id + " не найдена"));

        Set<Author> authors = new HashSet<>();
        for(Long authorId: authorsId){
            Author author = authorRepo.findAuthorById(authorId)
                    .orElseThrow(() -> new EntityNotFoundException("Автор с " + authorId + " не найден"));
            authors.add(author);
            author.getBooks().add(book);
        }

        book.setTitle(title);
        book.setAuthors(authors);
        book.setGenre(genre);
        book.setCirculation(circulation);
        book.setPrice(price);
        book.setReleaseYear(releaseYear);

        return bookRepo.save(book);
    }

    /**
     * Удаляет книгу по ее идентификатору.
     *
     * @param id Идентификатор книги для удаления
     * @return Сообщение об успешном удалении
     * @throws EntityNotFoundException Если книга с указанным ID не найдена
     */
    @Transactional
    public String deleteBookById(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Книга с " + id + " не найдена"));

        for(Author author: book.getAuthors()){
            author.getBooks().remove(book);
            authorRepo.save(author);
        }

        bookRepo.delete(book);
        return "Книга с ID " + id + "успешно удалена!";
    }

}
