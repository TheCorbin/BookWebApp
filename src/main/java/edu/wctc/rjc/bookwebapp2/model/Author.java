/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.bookwebapp2.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ryancorbin
 */
public class Author {
    private Integer authorID;
    private String authorName;
    private Date dateAdded;

    public Author() {
    }

    public Author(Integer authorID) {
        this.authorID = authorID;
    }

    public Author(Integer authorID, String authorName, Date dateAdded) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.dateAdded = dateAdded;
    }

    public Integer getAuthorID() {
        return authorID;
    }

    public void setAuthorID(Integer authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.authorID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (!Objects.equals(this.authorID, other.authorID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "author{" + "authorID=" + authorID + ", authorName=" + authorName + ", dateAdded=" + dateAdded + '}';
    }
    
    
    
    
    
}
