package com.decms.servlet;
import com.decms.util.DB; import jakarta.servlet.*; import jakarta.servlet.http.*; import jakarta.servlet.annotation.*; import java.io.*; import java.sql.*;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
 protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{r.getRequestDispatcher("/login.jsp").forward(r,s);}
 protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{
  String u=r.getParameter("username"),p=r.getParameter("password");
  try(Connection c=DB.getConnection();PreparedStatement ps=c.prepareStatement("SELECT full_name FROM users WHERE username=? AND password=?")){
   ps.setString(1,u);ps.setString(2,p);try(ResultSet rs=ps.executeQuery()){if(rs.next()){r.getSession().setAttribute("username",u);r.getSession().setAttribute("fullName",rs.getString(1));s.sendRedirect(r.getContextPath()+"");return;}}
  }catch(Exception e){throw new ServletException(e);} r.setAttribute("error","Invalid username or password");r.getRequestDispatcher("/login.jsp").forward(r,s);
 }
}
