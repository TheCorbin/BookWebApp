package edu.wctc.rjc.bookwebapp2.repository;

import edu.wctc.rjc.bookwebapp2.entity.Author;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author jlombardo
 */
public interface AuthorRepository extends JpaRepository<Author, Integer>, Serializable {
    
    @Query("Select a From Author a JOIN FETCH a.bookSet WHERE a.authorId = (:id)")
    public Author findByIdAndFetchBooksEagerly(@Param("id") Integer id);
    
    @Query("Select a.authorName FROM Author a")
    public Object[] findAllWithNameOnly();
    
}
