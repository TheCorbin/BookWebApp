<%-- 
    Document   : insertAuthor
    Created on : Sep 28, 2015, 4:33:05 PM
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
        <title>Insert Author</title>
    </head>
    <body>
        <h1>Insert Author</h1>
        
        
        <form id="form1" name="form1" Method="POST" action="AuthorController?action=insertFinal">
                <sec:authorize access="hasAnyRole('ROLE_MGR')">
                    <p>Author Name:</p>
                    <input type="text" name="authorName" placeholder="Insert Author Name Here"/>
                    <p>Date Added:</p>
                    <input type="text" name="authorAdded" value=${date} />
                    
                    <p>Optional: Add First Book</p>
                    <input type="text" name="bookTitle" placeholder="Place Book Title Here"/>
                    <input type="text" name="bookISBN" placeholder="Place Book ISBN Here" />
                    
                    <br>
                    
                    <input class="btn btn-primary" type="submit" name="submit" value="Insert" tabindex="8"/>
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
