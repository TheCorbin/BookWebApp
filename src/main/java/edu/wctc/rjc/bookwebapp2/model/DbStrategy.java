/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.bookwebapp2.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author ryancorbin
 */
public interface DbStrategy {

    void closeConnection() throws SQLException;

    void deleteById(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException, Exception;
       
    void updateById(String tableName, List colDescriptors, List colValues, String whereField, Object whereValue) throws SQLException;
    
    void insertRecord(String tableName, List namesForFields, List valueForFields) throws SQLException, Exception;

    List<Map<String, Object>> findAllRecords(String tableName) throws SQLException;

    Map<String, Object> findOneRecord(String tableName, String primaryKeyField, Object primaryKeyValue) throws SQLException, Exception;
    
    void openConnection(String driverClass, String url, String userName, String password) throws Exception;
    
    void openConnection(DataSource ds) throws Exception;
    
}
