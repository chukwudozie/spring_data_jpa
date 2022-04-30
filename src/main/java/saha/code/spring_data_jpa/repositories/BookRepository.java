package saha.code.spring_data_jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import saha.code.spring_data_jpa.domain.Book;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.title = ?1") // 1 in query is the positional parameter
    Book findBookByTitleWithQuery(String title);

    @Query(value = "SELECT * FROM book WHERE title = :title",nativeQuery = true) // for native query
    Book findBookByTitleNativeQuery(@Param("title") String title);

    Book jpaNamed(@Param("title")String title);

    @Query("SELECT b FROM Book b WHERE b.title = :title")// argument of the param must match the named parameter
    Book findBookByTitleWithQueryNamed(@Param("title") String title);
    Optional<Book> findBookByTitle(String title);
    Optional<Book> findBookByIsbn(String isbn);
    Book readByTitle(String title);
    @Nullable
    Book getByTitle( @Nullable  String title);
    Stream<Book> findAllByTitleNotNull();

    @Async
    Future<Book> queryBookByTitle(String title);
}
