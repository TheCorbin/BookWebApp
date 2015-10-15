<%-- 
    Document   : insertAuthor
    Created on : Sep 28, 2015, 4:33:05 PM
    Author     : ryancorbin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert Author</title>
    </head>
    <body>
        <h1>Insert Author</h1>
        
        
        <form id="form1" name="form1" Method="POST" action="AuthorController?action=insertFinal">
                    <p>Author Name:</p>
                    <input type="text" name="authorName" placeholder="Insert Author Name Here"/>
                    <p>Date Added:</p>
                    <input type="text" name="authorAdded" value=${date} />
                    <input class="btn btn-primary" type="submit" name="submit" value="Insert" tabindex="8"/>
            <br>
        </form>
        <form id="form2" name="form1" Method="POST" action="AuthorController?action=cancel">
            <input class="btn btn-primary" type="submit" name="submit" value="Cancel"/>
        </form>    
    </body>
</html>
