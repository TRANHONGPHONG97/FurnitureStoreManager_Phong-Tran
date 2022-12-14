package com.cg.controller;


import com.cg.dao.CategoryDAO;
import com.cg.dao.ProductDAO;
import com.cg.model.Product;
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
import java.util.ArrayList;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 50)
@WebServlet(name = "product", urlPatterns = "/product")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    public ProductServlet() {
        super();
    }


    private String errors = "";

    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        if (this.getServletContext().getAttribute("listCategory") == null) {
            this.getServletContext().setAttribute("listCategory", categoryDAO.selectAllCategory());

        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html/charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
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
                    listProductPaging(req, resp);
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
                    insertProduct(request, response);
                    break;
                case "edit":
                    updateProduct(request, response);
                    break;
                default:
                    listProduct(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listProductPaging(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 3;
        String q = "";
        int category_id = -1;
        if (request.getParameter("q") != null) {
            q = request.getParameter("q");
        }
        if (request.getParameter("category_id") != null) {
            category_id = Integer.parseInt(request.getParameter("category_id"));
        }
        System.out.println(category_id + " tao la role");
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        List<Product> listProduct = productDAO.selectProductsPaging((page - 1) * recordsPerPage, recordsPerPage, q, category_id);
        int noOfRecords = productDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        System.out.println(listProduct);
        request.setAttribute("list", listProduct);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("q", q);
        request.setAttribute("category_id", category_id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/products/listProduct.jsp");
        dispatcher.forward(request, response);
    }

    private void showDeleteForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.deleteProduct(id);
        List<Product> list = productDAO.selectAllProduct();
        request.setAttribute("list", list);
        response.sendRedirect("/product");
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = new Product();
        req.setAttribute("product", product);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/products/create_product.jsp");
        dispatcher.forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product existingUser = productDAO.selectProduct(id);
        req.setAttribute("product", existingUser);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/products/edit_product.jsp");
        dispatcher.forward(req, resp);
    }

    private void listProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> list = productDAO.selectAllProduct();
        req.setAttribute("list", list);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/products/listProduct.jsp");
        dispatcher.forward(req, resp);
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {
        Product product = null;
        String quantity = null;
        try {

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/products/edit_product.jsp");
            int id = (Integer.parseInt(req.getParameter("id").trim()));
            String name = req.getParameter("name").trim();
            String image = req.getParameter("image").trim();
            String price = req.getParameter("price").trim();
            quantity = req.getParameter("quantity").trim();
            int category_id = Integer.parseInt(req.getParameter("category_id").trim());

            List<String> errors = new ArrayList<>();
            boolean isPrice = ValidateUtils.isPriceValid(String.valueOf(price));
            boolean isQuantity = ValidateUtils.isQuantityValid(String.valueOf(quantity));
            product = new Product(id, name, image, price, quantity, category_id);

            if (name.isEmpty() ||
                    image.isEmpty() ||
                    price.isEmpty() ||
                    quantity.isEmpty()) {
                errors.add("Vui l??ng nh???p ????? v?? ch??nh x??c th??ng tin!");
            }
            if (name.isEmpty()) {
                errors.add("T??n s???n ph???m kh??ng ???????c ????? tr???ng!");
            }
            if (image.isEmpty()) {
                errors.add("URL image kh??ng ???????c ????? tr???ng!");
            }
            if (price.isEmpty()) {
                errors.add("Gi?? kh??ng ???????c ????? tr???ng!");
            }
            if (quantity.isEmpty()) {
                errors.add("S??? l?????ng kh??ng ???????c ????? tr???ng!");
            }
            if (!isQuantity) {
                errors.add("S??? l?????ng kh??ng ????ng ?????nh d???ng (0 < S??? l?????ng < 1000)!");
            }
            if (!isPrice) {
                errors.add("Gi?? kh??ng ????ng ?????nh d???ng (0< Gi?? < 100.000.000.000)!");

            } else if (errors.size() == 0) {
                product = new Product(id, name, image, price, quantity, category_id);
                boolean success = false;
                success = productDAO.updateProduct(product);
                if (success) {
                    req.setAttribute("success", true);
                } else {
                    req.setAttribute("errors", true);
                    errors.add("D??? li???u kh??ng ????ng, vui l??ng ki???m tra l???i!");
                }
            }
            if (errors.size() > 0) {
                req.setAttribute("errors", errors);
                req.setAttribute("product", product);
            }
            dispatcher.forward(req, resp);
        } catch (NumberFormatException e) {

            int id = Integer.parseInt(req.getParameter("id"));
            Product existingUser = productDAO.selectProduct(id);
            req.setAttribute("product", existingUser);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/products/edit_product.jsp");
            dispatcher.forward(req, resp);
        }
    }


    private void insertProduct(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {

        Product product;
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/products/create_product.jsp");
            String name = req.getParameter("name").trim();
            String image = req.getParameter("image").trim();
            String price = (req.getParameter("price").trim());
            String quantity = (req.getParameter("quantity").trim());
            String category_id = String.valueOf(Integer.parseInt(req.getParameter("category_id").trim()));

            List<String> errors = new ArrayList<>();

            boolean isPrice = ValidateUtils.isPriceValid(String.valueOf(price));
            boolean isQuantity = ValidateUtils.isQuantityValid(String.valueOf(quantity));

            product = new Product(name, image, String.valueOf(price), quantity, category_id);

            if (name.isEmpty() ||
                    image.isEmpty() ||
                    price.isEmpty() ||
                    quantity.isEmpty() ||
                    category_id.isEmpty()) {
                errors.add("Vui l??ng nh??p ?????y ????? th??ng tin!");
            }
            if (name.isEmpty()) {
                errors.add("T??n s???n ph???m kh??ng ???????c ????? tr???ng!");
            }
            if (image.isEmpty()) {
                errors.add("URL ???nh kh??ng ???????c ????? tr???ng!");
            }
            if (price.isEmpty()) {
                errors.add("Gi?? kh??ng ???????c ????? tr???ng!");
            }
            if (!isPrice) {
                errors.add("Gi?? kh??ng ????ng ?????nh d???ng (0< Gi?? < 100.000.000.000)!");
            }
            if (quantity.isEmpty()) {
                errors.add("S??? l?????ng kh??ng ???????c ????? tr???ng!");
            }
            if (!isQuantity) {
                errors.add("S??? l?????ng kh??ng ????ng ?????nh d???ng (0 < S??? l?????ng < 1000)!");
            } else if (errors.size() == 0) {
                product = new Product(name, image, price, quantity, category_id);
                boolean success = false;
                success = productDAO.insertProduct(product);

                if (success) {
                    req.setAttribute("success", true);
                } else {
                    req.setAttribute("errors", true);
                    errors.add("D??? li???u kh??ng h???p l???, vui l??ng ki???m tra l???i! ");
                }
            }
            if (errors.size() > 0) {
                req.setAttribute("errors", errors);
                req.setAttribute("product", product);
            }
            dispatcher.forward(req, resp);
        }
    }


