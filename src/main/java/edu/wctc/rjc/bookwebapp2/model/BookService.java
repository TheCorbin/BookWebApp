/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.bookwebapp2.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ryancorbin
 */
public class BookService {

    private InterAuthorDAO dao;

    public BookService(InterAuthorDAO dao) {
      
        this.dao = dao;
    }
    
    public List<Author> allAuthors() throws Exception{
        return dao.getAllAuthors();
    }
    
    public Author getAuthor(String authorId) throws Exception{
        return dao.getOneAuthor(authorId);
    }
    
    public void deleteAuthor(Object primaryKeyValue) throws Exception {
        dao.deleteAuthor("author", "author_id", primaryKeyValue);
    }
    
    public void updateAuthor(String tableName, List colDescriptors, List colValues, String whereField, Object whereValue) throws Exception{
        dao.updateAuthor(tableName, colDescriptors, colValues, whereField, whereValue);
    }
    
    public void insertAuthor(String tableName, List colDescriptors, List colValues) throws Exception{
        dao.insertAuthor(tableName, colDescriptors, colValues);
    }
    
    
//    public static void main(String[] args) throws Exception {
//        BookService bookService = new BookService(new AuthorDAO(new MySqlDbStrategy(), "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "adminC"));
//            
//        List<Author> authors = bookService.allAuthors();
//            
//        for (Author a: authors){
//            System.out.println(a);
//        }
//        
//        ArrayList<String> colDescriptions = new ArrayList<>();
//        colDescriptions.add("author_name");
//        colDescriptions.add("date_added");
//        
//        ArrayList<String> colValues = new ArrayList<>();
//        colValues.add("Jeffrey_Tambor");
//        colValues.add("2005-05-05");
//        
//        System.out.println("");
//        
//        bookService.insertAuthor("Alex_Woodward");
//        
//        System.out.println("");
//        
//        authors = bookService.allAuthors();
//            
//        for (Author a: authors){
//            System.out.println(a);
//        }  
//    }
        
}
