<%-- 
    Document   : listAuthorsAjax
    Created on : Sep 21, 2015, 9:36:05 PM
    Author     : RCorbin
    Purpose    : display list of author records and (in the future) provide
                 a way to add/edit/delete records
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author List</title>
    </head>
    <body bgcolor="${ctxBgColor}" class="authorList">
        <h1>Author List</h1>
        
        <p>Current User: ${sessionScope.user}</p>
<!--        <form id="formInsert" method="POST" action="AuthorController?action=insert">
            <input class="btn btn-primary" type="submit" name="insert" value="insert" ${guestUserFeatures}/> 
        </form>-->
        <br>
        
        <table width="500" border="1" cellspacing="0" cellpadding="4">
            <tr style="background-color: black;color:white;">
                <th align="left" class="tableHead">ID</th>
                <th align="left" class="tableHead">Author Name</th>
                <th align="right" class="tableHead">Date Added</th>
                <th align="center" class="tableHead">Delete Author</th>
                <th align="center" class="tableHead">Update Author</th>
            </tr>
            <tbody id="authorTblBody">
                
            </tbody>
        </table>
        
<!--        <form id="changeBg" method="POST" action="AuthorController?action=list">
                <h1> Set the background color for EVERYONE using this app.</h1>
                <input type="radio" name="bgColor" value="red" checked> Red
                <br>
                <input type="radio" name="bgColor" value="blue"> Blue
                <br>
                <input type="radio" name="bgColor" value="green"> Green
                <br>
                <input type="radio" name="bgColor" value="pink"> Pink
                <input class="btn btn-primary" type="submit" name="update" value="Update">
        </form> -->
        
        
        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
        </c:if>
    </body>
</html>