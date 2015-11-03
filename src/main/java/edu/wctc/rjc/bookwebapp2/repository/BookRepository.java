package edu.wctc.rjc.bookwebapp2.repository;

import edu.wctc.rjc.bookwebapp2.entity.Book;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jlombardo
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {
    
}
