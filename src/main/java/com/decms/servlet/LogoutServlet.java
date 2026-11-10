package com.decms.servlet;
import jakarta.servlet.annotation.*;import jakarta.servlet.http.*;import java.io.*;
@WebServlet("/logout") public class LogoutServlet extends HttpServlet {protected void doGet(HttpServletRequest r,HttpServletResponse s)throws IOException{r.getSession().invalidate();s.sendRedirect(r.getContextPath()+"/login");}}
