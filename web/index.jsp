<%-- 
    Document   : index
    Created on : Jun 4, 2024, 3:20:33 PM
    Author     : htduy
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="dto.Item"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index Page</title>
        <style>
            a {
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <h3>
            <a href="GetItemsServlet">Home | </a>
            <a href="ViewCart.jsp">View Cart | </a>
            <a href="QCServlet">Xem QC | </a>
            <form action="GetItemsServlet">
                <input type="text" name="txtitemname" value="<%= request.getParameter("itemname") %>">
                <input type="submit" value="go">
            </form>
        </h3>
        <%
            ArrayList<Item> list = (ArrayList<Item>) request.getAttribute("ListItems");
            if (list != null) {
                for (Item it : list) {
        %>
        <div style="float:left; margin: 5%;">
            <img src="<%= it.getImageurl() %>" style="width: 100px; height: 100px;" alt="">
            <p>
                ID: <%= it.getItemid() %>
                <br>
                Name: <%= it.getItemname() %>
                <br>
                Price: <%= it.getPrice() %>
                <br>
                <a href="AddItemToCartServlet?itemid=<%= it.getItemid() %>">Buy Now</a>
            </p>
        </div>
        <%
                }
            }
        %>
    </body>
</html>
