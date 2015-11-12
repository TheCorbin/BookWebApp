<%-- 
    Document   : listBooks
    Created on : Oct 20, 2015, 12:32:22 PM
    Author     : ryancorbin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book List</title>
    </head>
    <body>
        <h1>Book List</h1>
        
        <p>Current User: ${sessionScope.user}</p>
        <form id="formInsert" method="POST" action="BookController?action=insert">
            <input class="btn btn-primary" type="submit" name="insert" value="insert" ${guestUserFeatures}/> 
        </form>
        <br>
        
        <table width="500" border="1" cellspacing="0" cellpadding="4">
            <tr style="background-color: black;color:white;">
                <th align="left" class="tableHead">Book ID</th>
                <th align="left" class="tableHead">Title</th>
                <th align="right" class="tableHead">ISBN</th>
                <th align="center" class="tableHead">Author</th>
                <th align="center" class="tableHead">Delete Book</th>
                <th align="center" class="tableHead">Update Book</th>
                
            </tr>
        <c:forEach var="a" items="${books}" varStatus="rowCount">
            <c:choose>
                <c:when test="${rowCount.count % 2 == 0}">
                    <tr style="background-color: white;">
                </c:when>
                <c:otherwise>
                    <tr style="background-color: #ccffff;">
                </c:otherwise>
            </c:choose>
            <td align="left">${a.bookId}</td>
            <td align="left">${a.title}</td>
            <td align="right">${a.isbn}</td>
            <td aligh="left">${a.authorId.authorName}</td>
            <td>
                <sec:authorize access="hasAnyRole('ROLE_MGR')">
                <form id="formDelete" method="POST" action="BookController?action=delete&Id=${a.bookId}">
                    <input class="btn btn-primary" type="submit" name="delete" value="delete"/> 
                </form>
                </sec:authorize>
            </td>
             <td>
                <form id="formUpdate" method="POST" action="BookController?action=update&Id=${a.bookId}">
                <input class="btn btn-primary" type="submit" name="update" value="update"/> 
                </form>
            </td>
        </tr>
        </c:forEach>
        </table>
        <br>
        <form id="formInsert" method="POST" action="BookController?action=main">
            <input class="btn btn-primary" type="submit" name="main" value="main"/> 
        </form>
        <br>
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>   
        
        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
        </c:if>
    </body>
</html>

