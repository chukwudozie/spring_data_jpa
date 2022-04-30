package saha.code.spring_data_jpa.dao;

import saha.code.spring_data_jpa.domain.Book;

import java.util.List;

public interface BookDao {
    Book getById(Long id);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    Book findBookByTitle(String title);

    Book findByISBN(String isbn);

    List<Book> findAll();

    void deleteBookById(Long id);
}
