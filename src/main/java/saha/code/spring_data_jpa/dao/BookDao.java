package saha.code.spring_data_jpa.dao;

import org.springframework.data.domain.Pageable;
import saha.code.spring_data_jpa.domain.Book;

import java.util.List;

public interface BookDao {

    List<Book> findAllBooksSortByTitle(Pageable pageable);
    List<Book> findAllBooks(Pageable pageable);
    List<Book> findAllBooks(int pageSize, int offset);

    List<Book> findAllBooks();
    Book getById(Long id);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    Book findBookByTitle(String title);

    Book findByISBN(String isbn);

    List<Book> findAll();

    void deleteBookById(Long id);
}
