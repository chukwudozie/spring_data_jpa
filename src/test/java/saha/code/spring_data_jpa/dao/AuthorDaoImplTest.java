package saha.code.spring_data_jpa.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import saha.code.spring_data_jpa.domain.Author;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = {"saha.code.spring_data_jpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoImplTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void findAllAuthorsByLastName(){
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0,10));
//        authors.forEach(author -> {
//            System.out.println(author.getFirstName()+ " "+author.getLastName());
//        });
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void findAllAuthorsByLastNameSortFirstNameDesc(){
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0,10, Sort.by(Sort.Order.desc("firstName"))));
        authors.forEach(author -> {
            System.out.println(author.getFirstName()+ " "+author.getLastName());
        });
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);

    }

    @Test
    void findAllAuthorsByLastNameSortFirstNameAsc(){
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0,10, Sort.by(Sort.Order.asc("firstName"))));
        authors.forEach(author -> {
            System.out.println(author.getFirstName()+ " "+author.getLastName());
        });
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);

    }

    @Test
    void findAllAuthorsByLastNameAllRecs(){
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0,100 ));
        authors.forEach(author -> {
            System.out.println(author.getFirstName()+ " "+author.getLastName());
        });
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(40);

    }
}