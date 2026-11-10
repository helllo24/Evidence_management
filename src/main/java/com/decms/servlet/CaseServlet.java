
package com.decms.servlet;

import com.decms.util.Auth;
import com.decms.util.DB;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/cases")
public class CaseServlet extends HttpServlet {

 @Override
 protected void doGet(HttpServletRequest request,
                      HttpServletResponse response)
         throws ServletException, IOException {

  if (!Auth.protect(request, response)) {
   return;
  }

  String action = request.getParameter("action");

  try (Connection con = DB.getConnection()) {

   // EDIT CASE
   if ("edit".equals(action)) {

    int id = Integer.parseInt(request.getParameter("id"));

    String sql = "SELECT * FROM cases WHERE id = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {

     ps.setInt(1, id);

     try (ResultSet rs = ps.executeQuery()) {

      if (rs.next()) {

       request.setAttribute("caseData", rs);

       request.getRequestDispatcher(
               "/case-form.jsp"
       ).forward(request, response);

       return;
      }
     }
    }

    response.sendRedirect("cases");
    return;
   }

   // LIST CASES
   List<Map<String, Object>> rows = new ArrayList<>();

   String sql = "SELECT * FROM cases ORDER BY id DESC";

   try (Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql)) {

    while (rs.next()) {

     Map<String, Object> row = new HashMap<>();

     row.put("id", rs.getInt("id"));
     row.put("case_no", rs.getString("case_no"));
     row.put("title", rs.getString("title"));
     row.put("description", rs.getString("description"));
     row.put("category", rs.getString("category"));
     row.put("status", rs.getString("status"));
     row.put("priority", rs.getString("priority"));
     row.put("investigator", rs.getString("investigator"));

     rows.add(row);
    }
   }

   request.setAttribute("rows", rows);

   request.getRequestDispatcher(
           "/cases.jsp"
   ).forward(request, response);

  } catch (Exception e) {
   throw new ServletException("Database error in CaseServlet", e);
  }
 }

 @Override
 protected void doPost(HttpServletRequest request,
                       HttpServletResponse response)
         throws ServletException, IOException {

  if (!Auth.protect(request, response)) {
   return;
  }

  String action = request.getParameter("action");

  try (Connection con = DB.getConnection()) {

   // DELETE CASE
   if ("delete".equals(action)) {

    int id = Integer.parseInt(request.getParameter("id"));

    String sql = "DELETE FROM cases WHERE id = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
     ps.setInt(1, id);
     ps.executeUpdate();
    }

    response.sendRedirect("cases");
    return;
   }

   // UPDATE CASE
   if ("edit".equals(action)) {

    int id = Integer.parseInt(request.getParameter("id"));

    String sql = """
                    UPDATE cases
                    SET case_no=?, title=?, description=?, category=?,
                        status=?, priority=?, investigator=?
                    WHERE id=?
                    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {

     fillStatement(ps, request);

     ps.setInt(8, id);

     ps.executeUpdate();
    }

    response.sendRedirect("cases");
    return;
   }

   // CREATE CASE
   String sql = """
                INSERT INTO cases
                (case_no, title, description, category,
                 status, priority, investigator)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

   try (PreparedStatement ps = con.prepareStatement(sql)) {

    fillStatement(ps, request);

    ps.executeUpdate();
   }

   response.sendRedirect("cases");

  } catch (Exception e) {
   throw new ServletException("Database error in CaseServlet POST", e);
  }
 }

 private void fillStatement(PreparedStatement ps,
                            HttpServletRequest request)
         throws Exception {

  ps.setString(1, request.getParameter("case_no"));
  ps.setString(2, request.getParameter("title"));
  ps.setString(3, request.getParameter("description"));
  ps.setString(4, request.getParameter("category"));
  ps.setString(5, request.getParameter("status"));
  ps.setString(6, request.getParameter("priority"));
  ps.setString(7, request.getParameter("investigator"));
 }
}
