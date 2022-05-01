package saha.code.spring_data_jpa.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import saha.code.spring_data_jpa.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName);
    List<Author> findAllByLastNameIsContaining(String lastName);
    Page<Author> findAuthorByLastName(String lastName, Pageable pageable);
}
