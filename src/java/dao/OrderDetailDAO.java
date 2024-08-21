/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Account;
import dto.OrderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import mylib.DBUtil;

/**
 *
 * @author htduy
 */
public class OrderDetailDAO {

    public ArrayList<OrderDetail> getOrderDetails(int orderId) {
        ArrayList<OrderDetail> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtil.makeConnection(); //1. tao ket noi app voi server (SQL)
            if (cn != null) {
                //2. Viet querry va execute
                String sql = "select [DetailID],[ItemID],[OrderID],[Quantity]\n"
                        + "from [dbo].[OrderDetails]\n"
                        + "where [OrderID] = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderId);
                ResultSet rs = pst.executeQuery();
                if(rs!=null){
                    while(rs.next()){
                        int detailId = rs.getInt("DetailID");
                        int itemId = rs.getInt("ItemID");
                        int quantity = rs.getInt("Quantity");
                        OrderDetail detail = new OrderDetail(detailId, orderId, itemId, quantity);
                        list.add(detail);
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
}
