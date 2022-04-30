package saha.code.spring_data_jpa;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import saha.code.spring_data_jpa.dao.BookDao;
import saha.code.spring_data_jpa.domain.Author;
import saha.code.spring_data_jpa.domain.Book;
import saha.code.spring_data_jpa.repositories.BookRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = {"saha.code.spring_data_jpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {

    @Autowired
    BookDao bookDao;
    @Autowired
    BookRepository bookRepository;

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveNewBook(book);
        bookDao.deleteBookById(saved.getId());
        assertThrows(JpaObjectRetrievalFailureException.class, () ->{
        Book deleted = bookDao.getById(saved.getId());
        });

    }

    @Test
    void updateBookTest() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);

        book.setAuthorId(author.getId());
        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");
        bookDao.updateBook(saved);

        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);

        book.setAuthorId(author.getId());
        Book saved = bookDao.saveNewBook(book);
        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookByName() {
        Book book = bookDao.findBookByTitle("Clean Code");
        assertThat(book).isNotNull();
    }


    @Test
    void testGetBook() {
        Book book = bookDao.getById(3L);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void testFindBookByISBN(){
        Book book = new Book();
        book.setIsbn("1234"+ RandomString.make());
        book.setTitle("ISBN TEST");

        Book savedBook = bookDao.saveNewBook(book);
        Book fetched = bookDao.findByISBN(book.getIsbn());
        assertThat(fetched).isNotNull();
    }

    @Test
    public void testFindAllBooks(){
        List<Book> books = bookDao.findAll();
        books.forEach(book -> {
            System.out.println(book.getTitle()+ " : "+book.getPublisher());
        });
        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(0);
    }
//==============   Book Repository Test  =================================== //
    @Test
    void testEmptyResultException(){
        assertThrows(EmptyResultDataAccessException.class, () ->
        {
            Book book = bookRepository.readByTitle("test title");
        });
    }

    @Test
    void testNullParam (){
        assertNull(bookRepository.getByTitle(null));
    }

    @Test
    void testNoException(){
        assertNull(bookRepository.getByTitle("test title"));
    }

    @Test
    void testBookStream(){

        AtomicInteger count = new AtomicInteger();
        bookRepository.findAllByTitleNotNull().forEach(book -> {
            count.incrementAndGet();
        });

        System.out.println(count.get());
        assertThat(count.get()).isGreaterThan(4);
    }

    @Test
    void testBookFuture() throws ExecutionException, InterruptedException {
        Future<Book> bookFuture = bookRepository.queryBookByTitle("Clean Code");
        Book book = bookFuture.get();
        assertNotNull(book);
    }

    @Test
    void testBookQuery(){
        Book book = bookRepository.findBookByTitleWithQuery("Clean Code");
        assertThat(book).isNotNull();
    }

    @Test
    void testBookQueryNamed(){
        Book book = bookRepository.findBookByTitleWithQueryNamed("Clean Code");
        assertThat(book).isNotNull();
    }

    @Test
    void testBookQueryNative(){
        Book book = bookRepository.findBookByTitleNativeQuery("Clean Code");
        assertThat(book).isNotNull();
    }

    @Test
    void testBookJPANamed(){
        Book book = bookRepository.jpaNamed("Clean Code");
        assertThat(book).isNotNull();
    }

}
