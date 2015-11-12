<%-- 
    Document   : updateAuthor
    Created on : Sep 26, 2015, 7:52:21 PM
    Author     : ryancorbin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
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
                        <select id="booksDropDown" name="bookId">
                                    <option value="noBooks">No Books Available</option>
                        </select>
                    </c:otherwise>
                </c:choose>
                    
                    
<!--                  <select id="booksDropDown" name="bookId">
                    <c:forEach var="book" items="${author.bookSet}" varStatus="rowCount">                                       
                                        <option value="${book.bookId}">${book.title}</option>
                                    </c:forEach>
                    </select>-->
                    <p>Author Name:</p>
                    <input type="text" name="authorName" value=${author.authorName} />
                    <p>Date Added:</p>
                    <input type="text" name="authorAdded" value=${date} />
                    
                    
                    <input class="btn btn-primary" type="submit" name="submit" value="Update" tabindex="8"/>
            <br>
            </sec:authorize> 
        </form>
        <form id="form2" name="form1" Method="POST" action="AuthorController?action=cancel">
            <input class="btn btn-primary" type="submit" name="submit" value="Cancel"/>
        </form>  
        <br>
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>   
    </body>
</html>
