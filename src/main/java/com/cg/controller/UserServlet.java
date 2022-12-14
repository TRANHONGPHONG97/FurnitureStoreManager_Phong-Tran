package com.cg.controller;

import com.cg.dao.IRoleDao;
import com.cg.dao.RoleDao;
import com.cg.dao.UserDao;
import com.cg.model.User;
import com.cg.utils.ValidateUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


@WebServlet(name = "user", urlPatterns = "/user_manager")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 50)
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String errors = "";
    private String success = "";
    UserDao userDao;
    private IRoleDao iRoleDao;
    RequestDispatcher dispatcher;

    @Override
    public void init() throws ServletException {
        userDao = new UserDao();
        iRoleDao = new RoleDao();
        if (this.getServletContext().getAttribute("listRole") == null) {
            this.getServletContext().setAttribute("listRole", iRoleDao.selectAllRole());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showCreateForm(req, resp);
                    break;
                case "edit":
                    showEditForm(req, resp);
                    break;
                case "delete":
                    showDeleteForm(req, resp);
                    break;
                default:
                    listUserPaging(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertUser(request, response);
                    break;
                case "edit":
                    updateUser(request, response);
                    break;
                default:
                    listUser(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listUserPaging(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 8;
        String q = "";
        int idrole = -1;
        if (request.getParameter("q") != null) {
            q = request.getParameter("q");
        }
        if (request.getParameter("idrole") != null) {
            idrole = Integer.parseInt(request.getParameter("idrole"));
        }
        System.out.println(idrole + " tao la role");
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        List<User> listUser = userDao.selectUsersPaging((page - 1) * recordsPerPage, recordsPerPage, q, idrole);
        int noOfRecords = userDao.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("list", listUser);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("q", q);
        request.setAttribute("idrole", idrole);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/listUser.jsp");
        dispatcher.forward(request, response);
    }

    private void showDeleteForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDao.deleteUser(id);
        List<User> list = userDao.selectAllUsers();
        request.setAttribute("list", list);
        response.sendRedirect("/user_manager");
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        req.setAttribute("user", user);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/create.jsp");
        dispatcher.forward(req, resp);
    }
    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        User existingUser = userDao.selectUser(id);
        req.setAttribute("user", existingUser);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/edit.jsp");
        dispatcher.forward(req, resp);
    }

    private void listUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> list = userDao.selectAllUsers();
        req.setAttribute("list", list);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/listUser.jsp");
        dispatcher.forward(req, resp);
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/edit.jsp");
        int id = Integer.parseInt(req.getParameter("id"));
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        int idRole = Integer.parseInt(req.getParameter("idrole"));

        List<String> errors = new ArrayList<>();
        boolean isPhone = ValidateUtils.isPhoneValid(phone);
        boolean isEmail = ValidateUtils.isEmailValid(email);
        boolean isPassword = ValidateUtils.isPasswordValid(password);
        boolean isUserName = ValidateUtils.isUserNameValid(userName);
        User user = new User(id, userName, password, phone, email, idRole);

        if (userName.isEmpty() ||
                password.isEmpty() ||
                phone.isEmpty() ||
                email.isEmpty()) {
            errors.add("Vui l??ng nh???p ?????y ????? v?? ch??nh x??c th??ng tin!");
        }
        if (userName.isEmpty()) {
            errors.add("UserName kh??ng ???????c ????? tr???ng!");
        }
        if (!isUserName) {
            errors.add("UserName kh??ng ????ng ?????nh d???ng! (B???t ?????u b???t ch??? in hoa, ch??? ch???a ch??? c??i)");
        }
        if (password.isEmpty()) {
            errors.add("Password kh??ng ???????c ????? tr???ng!");
        }
        if (!isPassword) {
            errors.add("Password kh??ng ????ng ?????nh d???ng! (Ph???i b???t ?????u b???ng ch??? in hoa, ch??? ch???a ch??? v?? s???, t???i thi???u 8-24 k?? t???)");
        }
        if (phone.isEmpty()) {
            errors.add("Phone kh??ng ???????c ????? tr???ng!");
        }
        if (!isPhone) {
            errors.add("Phone kh??ng ????ng ?????nh d???ng! (Ph???i b???o g???m 10 ch??? s???, b???t ?????u b???ng 84 ho???c 0 (V?? d???: 0987654321 ho???c 8498765432))");
        }
        if (email.isEmpty()) {
            errors.add("Email kh??ng ???????c ????? tr???ng!");
        }
        if (!isEmail) {
            errors.add("Email kh??ng ????ng d???nh d???ng! (V?? d???: phong@gmail.com)");
        }
        if (userDao.existsByUser(userName)) {
            errors.add("User Name n??y ???? t???n t???i!");

        } else if (errors.size() == 0) {
            user = new User(id, userName, password, phone, email, idRole);
            boolean success = false;
            success = userDao.updateUser(user);
            if (success) {
                req.setAttribute("success", true);
            } else {
                req.setAttribute("errors", true);
                errors.add("D??? li???u kh??ng ????ng, vui l??ng ki???m tra l???i!");
            }
        }
        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
            req.setAttribute("user", user);
        }
        dispatcher.forward(req, resp);
    }

    private void insertUser(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {

//        System.out.println("tao l?? post");
//        String userName = request.getParameter("userName");
//        String password = request.getParameter("password");
//        String phone = request.getParameter("phone");
//        String email = request.getParameter("email");
//        int idrole = Integer.parseInt(request.getParameter("idrole"));
//
//        User user = new User(userName, password, phone, email, idrole);
//        userDao.insertUser(user);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/create_product.jsp");
//        dispatcher.forward(request, response);
////        response.sendRedirect("/user_manager");

//            User user = new User();
//            boolean flag = true;
//            Map<String, String> hashMap = new HashMap<String, String>();
//
//            try {
//                user.setIdUser(Integer.parseInt(request.getParameter("idUser")));
//                String userName = request.getParameter("userName");
//                user.setUserName(userName);
//                String phone = request.getParameter("phone");
//                user.setPhone(phone);
//                String email = request.getParameter("email");
//                user.setEmail(email);
//                user.setPassword(request.getParameter("password"));
//                user.setIdrole(Integer.parseInt(request.getParameter("idrole")));
//                ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//                Validator validator = validatorFactory.getValidator();
//                Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
//
//                if (!constraintViolations.isEmpty()) {
//
//                    errors = "";
//                    // constraintViolations is has error
//                    for (ConstraintViolation<User> constraintViolation : constraintViolations) {
//                        errors += "" + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage()
//                                + "";
//                    }
//                    errors += "";
//                    request.setAttribute("user", user);
//                    request.setAttribute("errors", errors);
//                List<Role> roleList = iRoleDao.selectAllRole();
//                request.setAttribute("listRole", roleList);
//                    request.getRequestDispatcher("/WEB-INF/view/create.jsp").forward(request, response);
//                } else {
//                    if (userDao.selectUserByEmail(email) != null) {
//                        flag = false;
//                        hashMap.put("email", "Email exits in database");
//                        System.out.println(this.getClass() + " Email exits in database");
//                    }
//                    if (userDao.selectUserByUserName(userName) != null) {
//                        flag = false;
//                        hashMap.put("user", "UserName exits in database");
//                        System.out.println(this.getClass() + " User Name exits in database");
//                    }
//                    if (userDao.selectUserByPhone(phone) != null) {
//                        flag = false;
//                        hashMap.put("phone", "Phone exits in database");
//                        System.out.println(this.getClass() + " User Name exits in database");
//                    }
//                    if (iRoleDao.selectRole(user.getIdrole()) == null) {
//                        flag = false;
//                        hashMap.put("user", "Country value invalid");
//                        System.out.println(this.getClass() + " Country invalid");
//                    }
//                    if (flag) {
//
//                        userDao.insertUser(user);
//                        User u = new User();
//                        request.setAttribute("user", u);
//                        request.getRequestDispatcher("WEB-INF/view/create.jsp").forward(request, response);
//
//                    } else {
//                        errors = "";
//                        hashMap.forEach(new BiConsumer<String, String>() {
//                            @Override
//                            public void accept(String keyError, String valueError) {
//                                errors += "" + valueError
//                                        + "";
//                            }
//                        });
//                        errors += "";
//
//                        request.setAttribute("user", user);
//                        request.setAttribute("errors", errors);
//                        request.getRequestDispatcher("/WEB-INF/view/create.jsp").forward(request, response);
//                    }
//                }
//            } catch (NumberFormatException ex) {
//                errors = "";
//                errors += "" + "Input format not right"
//                        + "";
//                errors += "";
//                request.setAttribute("user", user);
//                request.setAttribute("errors", errors);
//                request.getRequestDispatcher("/WEB-INF/view/create.jsp").forward(request, response);
//            } catch (Exception ex) {
//            }
//        }

        User user;
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/create.jsp");
        String userName = req.getParameter("userName").trim();
        String password = req.getParameter("password").trim();
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String idrole = String.valueOf(Integer.parseInt(req.getParameter("idrole").trim()));

        List<String> errors = new ArrayList<>();

        boolean isPhone = ValidateUtils.isPhoneValid(phone);
        boolean isEmail = ValidateUtils.isEmailValid(email);
        boolean isPassword = ValidateUtils.isPasswordValid(password);
        boolean isUserName = ValidateUtils.isUserNameValid(userName);

        user = new User(userName, password, phone, email, idrole);
        if (userName.isEmpty() ||
                password.isEmpty() ||
                phone.isEmpty() ||
                email.isEmpty() ||
                idrole.isEmpty()) {
            errors.add("Vui l??ng nh???p ?????y ????? v?? ch??nh x??c th??ng tin!");
        }
        if (userName.isEmpty()) {
            errors.add("UserName kh??ng ???????c ????? tr???ng!");
        }
        if (!isUserName) {
            errors.add("UserName kh??ng ????ng ?????nh d???ng! (B???t ?????u b???t ch??? in hoa, ch??? ch???a ch??? c??i)");
        }
        if (password.isEmpty()) {
            errors.add("Password kh??ng ???????c ????? tr???ng!");
        }
        if (!isPassword) {
            errors.add("Password kh??ng ????ng ?????nh d???ng! (Ph???i b???t ?????u b???ng ch??? in hoa, ch??? ch???a ch??? v?? s???, t???i thi???u 8-24 k?? t???)");
        }

        if (phone.isEmpty()) {
            errors.add("Phone kh??ng ???????c ????? tr???ng!");
        }
        if (!isPhone) {
            errors.add("Phone kh??ng ????ng ?????nh d???ng! (Ph???i b???o g???m 10 ch??? s???, b???t ?????u b???ng 84 ho???c 0 (V?? d???: 0987654321 ho???c 8498765432))");
        }
        if (email.isEmpty()) {
            errors.add("Email kh??ng ???????c ????? tr???ng!");
        }
        if (!isEmail) {
            errors.add("Email kh??ng ????ng d???nh d???ng! (V?? d???: phong@gmail.com)");
        }

        if (userDao.existsByEmail(email)) {
            errors.add("Email n??y ???? t???n t???i!");
        }
        if (userDao.existsByUser(userName)) {
            errors.add("User Name n??y ???? t???n t???i!");
        }
        if (userDao.existsByPhone(phone)) {
            errors.add("Phone n??y ???? t???n t???i!");

        } else if (errors.size() == 0) {
            user = new User(userName, password, phone, email, idrole);
            boolean success = false;
            success = userDao.insertUser(user);

            if (success) {
                req.setAttribute("success", true);
            } else {
                req.setAttribute("errors", true);
                errors.add("D??? li???u kh??ng h???p l???, vui l??ng ki???m tra l???i! ");
            }
        }
        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
            req.setAttribute("user", user);
        }
        dispatcher.forward(req, resp);
    }
}
