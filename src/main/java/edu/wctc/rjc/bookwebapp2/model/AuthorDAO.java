/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.bookwebapp2.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ryancorbin
 */
public class AuthorDAO implements InterAuthorDAO{
    private String driverClass;
    private String url;
    private String userName;
    private String passWord;
    private DbStrategy db;

    public AuthorDAO(DbStrategy db, String driverClass, String url, String userName, String passWord) {
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.passWord = passWord;
        this.db = db;
    }
    
    
    @Override
   public final List<Author> getAllAuthors() throws Exception{ 
        List<Author> authorList = new ArrayList();
        
        db.openConnection(driverClass, url, userName, passWord);
        List<Map<String, Object>> rawList = db.findAllRecords("author");
        
        for(Map<String,Object> rawRec : rawList){
            Object obj = rawRec.get("author_id");
            Integer authorId = obj == null ? 0 : Integer.parseInt(obj.toString());
            
            obj = rawRec.get("author_name");
            String authorName = obj == null ? "" : obj.toString();
            
            obj = rawRec.get("date_added");
            Date dateAdded = Date.valueOf(obj.toString());
            
            Author author = new Author(authorId, authorName, dateAdded); 
            authorList.add(author);
        }
        
        db.closeConnection();
        
        return authorList;
   }
   
   @Override
   public final Author getOneAuthor(String authorId) throws Exception{
       db.openConnection(driverClass, url, userName, passWord);
       Map<String, Object> rawRec = db.findOneRecord("author", "author_id", authorId);
       
       Object obj = rawRec.get("author_id");
       Integer authorID = obj == null ? 0 : Integer.parseInt(obj.toString());
       
       obj = rawRec.get("author_name");
       String authorName = obj == null ? "" : obj.toString();
       
       obj = rawRec.get("date_added");
       Date dateAdded = Date.valueOf(obj.toString());
       
       Author author = new Author(authorID, authorName, dateAdded);
       
       db.closeConnection();
       return author;    
   }
   
   
    @Override
   public final void deleteAuthor(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws Exception{
       db.openConnection(driverClass, url, userName, passWord);
       db.deleteById(tableName, primaryKeyFieldName, primaryKeyValue);
       db.closeConnection();
   }  
   
   @Override
   public final void updateAuthor(String tableName, List colDescriptors, List colValues, String authorField, Object newAuthorValue) throws Exception{
       db.openConnection(driverClass, url, userName, passWord);
       db.updateById(tableName, colDescriptors, colValues, authorField, newAuthorValue);
       db.closeConnection();
   }
   
   @Override
   public final void insertAuthor(String tableName, List colDescriptors, List colValues) throws Exception{
       db.openConnection(driverClass, url, userName, passWord);
       
       db.insertRecord(tableName, colDescriptors, colValues);
       db.closeConnection();
   }
   
            
//    public static void main(String[] args) throws Exception {
//      AuthorDAO authorDao = new AuthorDAO(new MySqlDbStrategy(), "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "adminC");
//      List<Author> authors = authorDao.getAllAuthors();  
//      
//      for (Author a: authors){
//          System.out.println(a);
//      }
//      
////      authorDao.deleteAuthor("author", "author_id", 9);
////      authorDao.insertAuthor("author", "Steve_Timberlake");
////        authorDao.updateAuthor("author", authors, authors, null, authorDao);
//      
//      authors = authorDao.getAllAuthors();
//      
//      for (Author a: authors){
//          System.out.println(a);
//      }
//    }
    
}
