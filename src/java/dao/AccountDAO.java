/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Account;
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
public class AccountDAO {
//    ham nay de lay tat ca cac dong trong bang Account
//    tra ve ArrayList cac object Account

    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> list = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtil.makeConnection(); //1. tao ket noi app voi server (SQL)
            if (cn != null) {
                //2. Viet querry va execute
                String sql = "select [AccId],[Email],[FullName],[Password]\n"
                        + "from [dbo].[Accounts]";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if (rs != null) {
                    //3. Doc tung dong trong rs va convert no thanh object account
                    // add object nay vao arrayList
                    while (rs.next()) {
                        int id = rs.getInt("AccId");
                        String email = rs.getString("Email");
                        String fullName = rs.getString("FullName");
                        String password = rs.getString("Password");
                        Account a = new Account(id, fullName, email, password);
                        list.add(a);
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

    public int removeAccount(int accid) {
        int result = 0;
        Connection cn = null;
        try {
            cn = DBUtil.makeConnection(); //1. tao ket noi app voi server (SQL)
            if (cn != null) {
                //2) viet querry va execute
                String sql = "delete from [dbo].[Accounts]\n"
                        + "where [AccId]=?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, accid);
                result = pst.executeUpdate();
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
        return result;
    }

    public Account getAccount(String email) {
        Account rs = null;
        Connection cn = null;
        try {
            cn = DBUtil.makeConnection(); //1. tao ket noi app voi server (SQL)
            if (cn != null) {
                //2. Viet querry va execute
                String sql = "select [AccId],[Email],[FullName],[Password]\n"
                        + "from [dbo].[Accounts]\n"
                        + "where Email like ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    int id = table.getInt("AccId");
                    String fullname = table.getString("FullName");
                    String password = table.getString("Password");
                    rs = new Account(id, fullname, email, password);
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
        return rs;
    }
    
     //ham nay de check email, password co trong bang Account hay khong
    public Account getAccount(String email,String password){
        Account result=null;
        Connection cn=null;
        try{
            //make connection giua backend va sqlserver
            cn=DBUtil.makeConnection();
            if(cn!=null){
                //viet sql va exec cau sql
                String sql = "select AccId,Email,FullName,Password\n"
                        + "from dbo.Accounts\n"
                        + "where Email=? and Password=? COLLATE SQL_Latin1_General_CP1_CI_AS";
                PreparedStatement pst=cn.prepareStatement(sql);
                //lay emai,password cua input params gan vao 2 cho ?
                pst.setString(1, email);
                pst.setString(2, password);
                //run cau sql
                ResultSet table=pst.executeQuery();
                //doc data trong table
                if(table!=null && table.next()){
                    int m_accid=table.getInt("AccId");
                    String m_email=table.getString("Email");
                    String m_fullname=table.getString("FullName");
                    String m_password=table.getString("Password");
                    result=new Account(m_accid, m_fullname, m_email, m_password);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                if(cn!=null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }        
        return result;
    }

    public int insertAccount(String email, String fullname, String password) {
        int rs = 0;
        Connection cn = null;
        try {
            cn = DBUtil.makeConnection(); //1. tao ket noi app voi server (SQL)
            if (cn != null) {
                //2. Viet querry va execute
                String sql = "insert [dbo].[Accounts] (Email, FullName, Password) values (?,?,?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                pst.setString(2, fullname);
                pst.setString(3, password);
                rs = pst.executeUpdate();

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
        return rs;
    }

    public Account checkLogin(String email, String password) {
        Account rs = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet table = null;

        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                String sql = "SELECT [AccId], [Email], [FullName], [Password] "
                        + "FROM [dbo].[Accounts] "
                        + "WHERE Email = ? AND [Password] = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                pst.setString(2, password);
                table = pst.executeQuery();

                if (table != null && table.next()) {
                    int id = table.getInt("AccId");
                    String fullname = table.getString("FullName");
                    rs = new Account(id, fullname, email, password);
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
        return rs;
    }

}
