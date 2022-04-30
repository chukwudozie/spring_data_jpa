package saha.code.spring_data_jpa.dao;

import saha.code.spring_data_jpa.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);

    List<Author> findAll();

    List<Author> listAuthorByLastNameLike(String lastName);
}
