<%-- 
    Document   : about
    Created on : Oct 5, 2015, 11:14:56 AM
    Author     : ryancorbin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actions Page</title>
    </head>
    <body>
        <h1>What would you like to do?</h1>
        
        <form id="form1" name="form1" Method="POST" action="AuthorController">
                    <h3>List Authors:</h3>
                    <input class="btn btn-primary" type="submit" name="action" value="list"/>
                    
                    <h3>Insert a New Author:</h3>
                    <input class="btn btn-primary" type="submit" name="action" value="insert"/>
                    
            <br><!--
        </form>-->
    </body>
</html>
