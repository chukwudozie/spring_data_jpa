package saha.code.spring_data_jpa.dao;

import org.springframework.stereotype.Component;
import saha.code.spring_data_jpa.domain.Book;
import saha.code.spring_data_jpa.repositories.BookRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class BookDaoImpl implements BookDao{

    private final BookRepository bookRepository;

    public BookDaoImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.getById(id);
    }

    @Override
    public Book saveNewBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book updateBook(Book book) {
        Book bookInDb = bookRepository.getById(book.getId());
        bookInDb.setTitle(book.getTitle());
        bookInDb.setIsbn(book.getIsbn());
        bookInDb.setPublisher(book.getPublisher());
        bookInDb.setAuthorId(book.getAuthorId());
        return bookRepository.save(bookInDb);

    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findBookByTitle(title)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Book findByISBN(String isbn) {
        return bookRepository.findBookByIsbn(isbn)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
