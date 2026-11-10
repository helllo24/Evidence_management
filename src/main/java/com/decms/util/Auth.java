package com.decms.util;
import jakarta.servlet.http.*;
public class Auth {
 public static boolean loggedIn(HttpServletRequest req){ return req.getSession(false)!=null && req.getSession(false).getAttribute("username")!=null; }
 public static boolean protect(HttpServletRequest req,HttpServletResponse res) throws java.io.IOException {
  if(!loggedIn(req)){res.sendRedirect(req.getContextPath()+"/login");return false;} return true;
 }
}
