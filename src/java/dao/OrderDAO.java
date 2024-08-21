/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Account;
import dto.Item;
import dto.Order;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import mylib.DBUtil;

/**
 *
 * @author htduy
 */
public class OrderDAO {

    // ham nay de lay all orders trong DB theo status
    public ArrayList<Order> getAllOrders(int status) {
        ArrayList<Order> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtil.makeConnection(); //1. tao ket noi app voi server (SQL)
            if (cn != null) {
                //2. Viet querry va execute
                String sql = "select OrderID,OrderDate,Status,Total,AccID\n"
                        + "from dbo.Orders\n"
                        + "where Status=?\n"
                        + "Order by OrderDate desc";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, status);
                ResultSet rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int orderId = rs.getInt("orderId");
                        Date d = rs.getDate("OrderDate");
                        int total = rs.getInt("Total");
                        int accId = rs.getInt("AccID");
                        Order ord = new Order(orderId, d, status, total, accId);
                        list.add(ord);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // ham nay de insert vao Order va OrderDetail
    public int saveOrder(int accid, HashMap<Item, Integer> cart) {
        int result = 0;
        // tinh tong tien trong cart
        int total = 0;
        for (Item t : cart.keySet()) {
            total += t.getPrice() * cart.get(t);
        }
        Connection cn = null;
        try {
            cn = DBUtil.makeConnection(); //1. tao ket noi app voi server (SQL)
            if (cn != null) {
                cn.setAutoCommit(false);
                //  chen 1 dong vao bang order
                String sql = "INSERT orders VALUES (?,?,?,?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setDate(1, new Date(System.currentTimeMillis()));
                pst.setInt(2, 1); // 1: pending
                pst.setInt(3, total); // tong tien
                pst.setInt(4, accid); // account ID
                result = pst.executeUpdate();

                // lay orderId vua chen trong order
                if (result > 0) {
                    sql = "SELECT TOP 1 OrderID FROM Orders order by OrderID DESC";
                    pst = cn.prepareStatement(sql);
                    ResultSet table = pst.executeQuery();
                    if (table != null && table.next()) {
                        int orderid = table.getInt("OrderID");
                        // chen cac dong tu cart vao orderDetail
                        for (Item t : cart.keySet()) {
                            sql = "INSERT [dbo].[OrderDetails] VALUES (?,?,?)";
                            pst=cn.prepareStatement(sql);
                            
                            pst.setInt(1, t.getItemid());
                            pst.setInt(2, orderid);
                            pst.setInt(3, cart.get(t));
                            result = pst.executeUpdate();
                        }
                        cn.commit();    
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.setAutoCommit(true);
                    cn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
