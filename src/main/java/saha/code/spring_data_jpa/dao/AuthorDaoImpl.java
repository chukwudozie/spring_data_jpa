package saha.code.spring_data_jpa.dao;

import org.springframework.stereotype.Component;
import saha.code.spring_data_jpa.domain.Author;
import saha.code.spring_data_jpa.repositories.AuthorRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class AuthorDaoImpl implements AuthorDao {

     private final AuthorRepository authorRepository;

    public AuthorDaoImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.getById(id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        Author authorInDB = authorRepository.getById(author.getId());
        authorInDB.setFirstName(author.getFirstName());
        authorInDB.setLastName(author.getLastName());
        return authorRepository.save(authorInDB);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
//        return authorRepository.findAllByLastNameLike(lastName);
        return authorRepository.findAllByLastNameIsContaining(lastName);
    }
}
