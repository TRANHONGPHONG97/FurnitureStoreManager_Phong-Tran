package com.cg.controller;

import com.cg.dao.IRoleDao;
import com.cg.dao.RoleDao;
import com.cg.model.Role;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "RoleServlet", urlPatterns = "/role")
public class RoleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IRoleDao iRoleDao;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteRole(request, response);
                    break;
                default:
                    listRole(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
    private void listRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Role> listRole = iRoleDao.selectAllRole();
        request.setAttribute("listRole", listRole);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/roles/listCategory.jsp");
        dispatcher.forward(request, response);
    }
    private void deleteRole(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        iRoleDao.deleteRole(id);
        List<Role> listRole = iRoleDao.selectAllRole();
        request.setAttribute("listRole", listRole);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/roles/listCategory.jsp");
        dispatcher.forward(request, response);
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
       Role existingRole = iRoleDao.selectRole(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/roles/edit_product.jsp");
        request.setAttribute("role", existingRole);
        dispatcher.forward(request, response);
    }
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/roles/create_product.jsp");
        dispatcher.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertRole(request, response);
                    break;
                case "edit":
                    updateRole(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
    private void updateRole(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        Role role = new Role(id, name);
        iRoleDao.updateRole(role);
        response.sendRedirect("/role");
    }
    private void insertRole(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String name = request.getParameter("name");
        Role role = new Role();
      role.setName(name);
        iRoleDao.insertRole(role);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/roles/create_product.jsp");
        dispatcher.forward(request, response);
    }
    @Override
    public void init() throws ServletException {
        iRoleDao = new RoleDao();
    }
}
