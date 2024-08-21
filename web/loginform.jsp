<%-- 
    Document   : loginform
    Created on : Jun 7, 2024, 3:23:25 PM
    Author     : htduy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <div>
            <form action="LoginServlet">
                <input type="text" name="txtemail">
                <input type="text" name="txtpassword">
                
                <input type="submit" value="login" name="action">
            </form>
        </div>
        <div><%= (request.getAttribute("ERROR")!=null)? request.getAttribute("ERROR"):""%></div>
    </body>
</html>
