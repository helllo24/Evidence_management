package com.decms.servlet;
import com.decms.util.*;import jakarta.servlet.*;import jakarta.servlet.http.*;import jakarta.servlet.annotation.*;import java.io.*;import java.sql.*;
@WebServlet("/dashboard") public class DashboardServlet extends HttpServlet {
 protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{if(!Auth.protect(r,s))return;try(Connection c=DB.getConnection();Statement st=c.createStatement()){
  for(String table:new String[]{"cases","suspects","evidence","reports"}){ResultSet rs=st.executeQuery("SELECT COUNT(*) FROM "+table);rs.next();r.setAttribute(table+"Count",rs.getInt(1));}
 }catch(Exception e){throw new ServletException(e);}r.getRequestDispatcher("/dashboard.jsp").forward(r,s);}
}
