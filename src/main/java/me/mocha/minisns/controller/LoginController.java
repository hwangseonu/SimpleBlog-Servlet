package me.mocha.minisns.controller;

import me.mocha.minisns.exception.ApplicationException;
import me.mocha.minisns.model.dto.UserDTO;
import me.mocha.minisns.model.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            UserDTO user = userService.getUser(username);

            if (user.verify(password)) {
                req.getSession().setAttribute("user", user);
                res.sendRedirect(req.getContextPath() + "/");
            } else {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
                req.setAttribute("message", "login failed!");
                dispatcher.forward(req, res);
            }
        } catch (ApplicationException ex) {
            res.sendError(ex.getStatus());
        }
    }
}