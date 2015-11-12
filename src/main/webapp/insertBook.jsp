<%-- 
    Document   : insertBook
    Created on : Oct 26, 2015, 9:37:32 PM
    Author     : ryancorbin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert Book</title>
    </head>
    <body>
        <h1>Insert Book</h1>
        
        
        <form id="form1" name="form1" Method="POST" action="BookController?action=insertFinal">
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
                    <p>Book Title:</p>
                    <input type="text" name="title" placeholder="Insert Book Title Here"/>
                    <p>ISBN:</p>
                    <input type="text" name="isbn" Placeholder="Place ISBN Here"/>
                    <p>Book Author:</p>
                    <Select id="bookDropDown" name="authorId">
                        <choose>
                            <option value="">None</option>
                            <c:forEach var="author" items="${authors}" varStatus="rowCount">                                       
                                <option value="${author.authorId}" <c:if test="${rowCount.count == 1}">selected</c:if>>
                                ${author.authorName}</option>
                            </c:forEach>
                        </choose>
                    </Select>
                    </br>
                    <input class="btn btn-primary" type="submit" name="submit" value="Insert" tabindex="8"/>
            <br>
            </sec:authorize>
        </form>
        <form id="form2" name="form1" Method="POST" action="BookController?action=cancel">
            <input class="btn btn-primary" type="submit" name="submit" value="Cancel"/>
        </form>  
        <br>
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize> 
        
    </body>
</html>
