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
public interface InterAuthorDAO {

    void deleteAuthor(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws Exception;
    void updateAuthor(String tableName, List colDescriptors, List colValues, String authorField, Object newAuthorValue) throws Exception;
    void insertAuthor(String tableName, List colDescriptors, List colValues) throws Exception;
    List<Author> getAllAuthors() throws Exception;
    Author getOneAuthor(String authorId) throws Exception;
    
}
