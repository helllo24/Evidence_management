package com.decms.servlet;
import com.decms.util.*;import jakarta.servlet.*;import jakarta.servlet.http.*;import jakarta.servlet.annotation.*;import java.io.*;import java.sql.*;
@WebServlet("/suspects") public class SuspectServlet extends HttpServlet{
 protected void service(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{if(!Auth.protect(r,s))return;super.service(r,s);}
 protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{try(Connection c=DB.getConnection();Statement st=c.createStatement();ResultSet rs=st.executeQuery("SELECT su.*,ca.case_no FROM suspects su JOIN cases ca ON ca.id=su.case_id ORDER BY su.id DESC")){r.setAttribute("rows",rs);r.getRequestDispatcher("/suspects.jsp").forward(r,s);}catch(Exception e){throw new ServletException(e);}}
 protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{String a=r.getParameter("action");try(Connection c=DB.getConnection()){
 if("delete".equals(a)){PreparedStatement p=c.prepareStatement("DELETE FROM suspects WHERE id=?");p.setInt(1,Integer.parseInt(r.getParameter("id")));p.executeUpdate();}
 else{PreparedStatement p=c.prepareStatement("INSERT INTO suspects(case_id,full_name,age,gender,contact,address,notes) VALUES(?,?,?,?,?,?,?)");p.setInt(1,Integer.parseInt(r.getParameter("case_id")));p.setString(2,r.getParameter("full_name"));String age=r.getParameter("age");if(age==null||age.isEmpty())p.setNull(3,Types.INTEGER);else p.setInt(3,Integer.parseInt(age));p.setString(4,r.getParameter("gender"));p.setString(5,r.getParameter("contact"));p.setString(6,r.getParameter("address"));p.setString(7,r.getParameter("notes"));p.executeUpdate();}
 }catch(Exception e){throw new ServletException(e);}s.sendRedirect(r.getContextPath()+"/suspects");}
}
