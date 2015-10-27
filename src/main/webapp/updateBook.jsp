<%-- 
    Document   : updateBook
    Created on : Oct 20, 2015, 1:09:37 PM
    Author     : ryancorbin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Book</title>
    </head>
    <body>
        <h1>Update Book</h1>
        
        <form id="form1" name="form1" Method="POST" action="BookController?action=updateFinal&BookId=${book.bookId}">
                    <p>Book ID: ${book.bookId}</p>
                    <p>Book Title:</p>
                    <input type="text" name="title" value=${book.title} />
                    <p>Book ISBN:</p>
                    <input type="text" name="isbn" value=${book.isbn} />
                    <p>Book Author:</p>
                    <Select id="bookDropDown" name="authorId">
                        <choose>
                                <option value="">None</option>
                                <c:forEach var="author" items="${authors}">
                                    <option name="authorId" value="${author.authorId}" <c:if test="${book.authorId.authorId == author.authorId}">Selected</c:if>>${author.authorName}</option>
                                </c:forEach>
                        </choose>
                    </Select>
                    
                    
                    
                    
                    <input class="btn btn-primary" type="submit" name="submit" value="Update"/>
            <br>
        </form>
        <form id="form2" name="form1" Method="POST" action="BookController?action=cancel">
            <input class="btn btn-primary" type="submit" name="submit" value="Cancel"/>
        </form>         
    </body>
</html>
