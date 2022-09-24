package com.cg.controller;

import com.cg.dao.UserDao;
import com.cg.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        String userName = req.getParameter("userName");
        String pass = req.getParameter("pass");
        try {
            User account = userDao.login(userName,pass);

            if (account == null){
                req.setAttribute("message", "Wrong account, please re-enter!");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
                dispatcher.forward(req,resp);
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("acc",account);
                resp.sendRedirect("/product");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
