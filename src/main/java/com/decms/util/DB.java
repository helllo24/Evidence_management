package com.decms.util;
import java.sql.*;
public class DB {
 private static final String URL="jdbc:mysql://localhost:3306/Evidence_management?useSSL=false&serverTimezone=UTC";
 private static final String USER="root";
 private static final String PASSWORD="root1234";
 static { try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch(Exception e){ throw new RuntimeException(e); } }
 public static Connection getConnection() throws SQLException { return DriverManager.getConnection(URL,USER,PASSWORD); }
}
