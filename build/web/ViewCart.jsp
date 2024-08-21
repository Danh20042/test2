<%-- 
    Document   : ViewCart
    Created on : Jun 4, 2024, 4:01:29 PM
    Author     : htduy
--%>

<%@page import="dto.Item"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
    </head>
    <body>
        <h3>
            <a href="GetItemsServlet">Home</a>
            <a href="ViewCart.jsp">View Cart</a>
            <a href="#">Register</a>
        </h3>

        <%
            String msg = request.getParameter("thanks");
            HashMap<Item, Integer> cart = (HashMap<Item, Integer>) session.getAttribute("cart");
            if (cart == null && msg != null) {
                out.print("<h1>" + msg + "</h1>");
            } else if (cart == null && msg == null) {
                out.print("Your Cart is empty");
            } else {
        %>

        <h1>Your Cart</h1>
        <table>
            <tr>
                <th>Mã SP</th>
                <th>Tên SP</th>
                <th>Hình Ảnh</th>
                <th>Giá Tiền</th>
                <th>Số Lượng</th>
                <th>Thao Tác</th>
            </tr>

            <%
                int total = 0;
                for (Item it : cart.keySet()) {
                    int quantity = cart.get(it);
                    total += (quantity * it.getPrice());
            %>
            <form action="ModifyCartServlet">
                <input type="hidden" name="txtitemid" value="<%= it.getItemid()%>">
                <tr>
                    <td><%= it.getItemid()%></td>
                    <td><%= it.getItemname()%></td>
                    <td><img src="<%= it.getImageurl()%>" style="width: 100px; height: 100px;" alt=""></td>
                    <td><%= it.getPrice()%></td>
                    <td><input type="number" value="<%= quantity%>" min="1" max="10" name="txtquantity"></td>
                    <td>
                        <input type="submit" value="remove" name="btnaction">
                        <input type="submit" value="update" name="btnaction">
                    </td>
                </tr>
            </form>
            <%
                }

            %>
        </table>
        <h4>Tiền Thanh Toán: <%= total%> VND</h4>
        <p>Ngày Đặt: <%= (new Date()).toString()%></p>
        <p><a href="SaveOrderServlet">Thanh Toán Đơn Hàng</a></p>
        <div style="background-color: pink; width: 200px; height: 100px; position: fixed; top: 0; right: 0">
            <%= (session.getAttribute("qc") != null) ? session.getAttribute("qc") : ""%>
        </div>
        <%}%>
    </body>
</html>