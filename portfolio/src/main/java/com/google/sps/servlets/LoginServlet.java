package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/auth")
public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        UserService userService = UserServiceFactory.getUserService();
        if (userService.isUserLoggedIn()) {
            String userEmail = userService.getCurrentUser().getEmail();
            String urlToRedirectAfterUserLogsOut = "/";
            String logoutUrl = userService.createLogoutURL(urlToRedirectAfterUserLogsOut);

            response.getWriter().println("<p>Hello " + userEmail + "!</p>");
            response.getWriter().println("<p>Logout <a href=\"" + logoutUrl + "\">here</a>.</p>");
        } else {
            String urlToRedirectAfterUserLogsIn = "/";
            String loginUrl = userService.createLoginURL(urlToRedirectAfterUserLogsIn);

            response.getWriter().println("<p>Log in to post a comment.</p>");
            response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
        }
    }
}