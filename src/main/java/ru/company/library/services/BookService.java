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
public class BookService {
    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;

    public Book saveBook(Book author){
        return bookRepo.save(author);
    }

    public Book findBookByTitleContainingIgnoreCase(String title){
        return bookRepo.findBookByTitleContainingIgnoreCase(title)
            .orElseThrow(() -> new EntityNotFoundException("Книга с названием " + title + " не найдена"));
    }

    public Book updateBookById(
            Long id,
            String title,
            Long authorId,
            String genre,
            String circulation,
            double price,
            Date releaseYear
    ) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Книга с id " + id + " не найдена"));

        Author author = authorRepo.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Автор с id " + authorId + " не найден"));

        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setCirculation(circulation);
        book.setPrice(price);
        book.setReleaseYear(releaseYear);

        return bookRepo.save(book);
    }


    @Transactional
    public String deleteBookById(Long id) {
        if (bookRepo.existsById(id)) {
            bookRepo.deleteById(id);
            return "Книга с ID " + id + " успешно удалена";
        }
        throw new EntityNotFoundException("Книга с ID " + id + " не найдена");
    }

}
