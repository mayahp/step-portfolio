package com.google.sps.servlets;

import com.google.sps.data.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/auth")
public class LoginServlet extends HttpServlet {

    int USER_ERROR_CODE = -1;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        User currentUser;

        UserService userService = UserServiceFactory.getUserService();
        if (userService.isUserLoggedIn()) {
            String userEmail = userService.getCurrentUser().getEmail();
            String urlToRedirectAfterUserLogsOut = "/";
            String logoutUrl = userService.createLogoutURL(urlToRedirectAfterUserLogsOut);

            // Finds the index of the @ character.
            int index = userEmail.indexOf("@");
            String username = userEmail.substring(0, index);
            long timestamp = System.currentTimeMillis();
            currentUser = new User(timestamp, username, logoutUrl);
        } else {
            String urlToRedirectAfterUserLogsIn = "/";
            String loginUrl = userService.createLoginURL(urlToRedirectAfterUserLogsIn);

            currentUser = new User(USER_ERROR_CODE, "", loginUrl);
        }
        response.getWriter().println(gson.toJson(currentUser));
    }
}