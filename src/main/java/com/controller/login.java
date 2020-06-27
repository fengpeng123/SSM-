package com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Controller
public class UserController {

    @RequestMapping("/Login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Context-Type", "text/html;charset=utf-8");

        PrintWriter output = response.getWriter();
        String uname = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        ResultSet rs;
        Statement statement = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306?serverTimezone=GMT&characterEncoding=utf-8", "root", "Guozhaojie610");
            String sql = "select * from new_schema.login where username='" + uname + "' and password='" + pwd + "'";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            if (rs.next()) {
                request.setAttribute("username", uname);
                response.sendRedirect("index.html");
            } else {
                output.println("账号或密码错误");
                response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}