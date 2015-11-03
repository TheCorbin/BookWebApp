<%-- 
    Document   : updateAuthor
    Created on : Sep 26, 2015, 7:52:21 PM
    Author     : ryancorbin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Author</title>
    </head>
    <body>
        <h1>Update Author</h1>
        
        <form id="form1" name="form1" Method="POST" action="AuthorController?action=updateFinal&Id=${author.authorId}">
                    <p>Author ID: ${author.authorId}</p>
                    <c:choose>
                    <c:when test="${not empty author.bookSet}">
                                <select id="booksDropDown" name="bookId">
                                    <c:forEach var="book" items="${author.bookSet}" varStatus="rowCount">                                       
                                        <option value="${book.bookId}">${book.title}</option>
                                    </c:forEach>
                                </select>
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
                    
                    
<!--                    <select id="booksDropDown" name="bookId">
                                    <c:forEach var="book" items="${author.bookSet}" varStatus="rowCount">                                       
                                        <option value="${book.bookId}">${book.title}</option>
                                    </c:forEach>-->
                    </select>
                    <p>Author Name:</p>
                    <input type="text" name="authorName" value=${author.authorName} />
                    <p>Date Added:</p>
                    <input type="text" name="authorAdded" value=${date} />
                    
                    
                    <input class="btn btn-primary" type="submit" name="submit" value="Update" tabindex="8"/>
            <br>
        </form>
        <form id="form2" name="form1" Method="POST" action="AuthorController?action=cancel">
            <input class="btn btn-primary" type="submit" name="submit" value="Cancel"/>
        </form>         
    </body>
</html>
