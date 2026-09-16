package com.library.service;

import com.library.db.UserDAO;
import com.library.model.User;
import com.library.util.FileLogger;

public class AuthService {

    private UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public User login(String email, String password) {
        User user = userDAO.findByEmail(email);

        if (user == null) {
            FileLogger.log("Login failed - no user: " + email);
            return null;
        }

        if (!user.getPassword().equals(password)) {
            FileLogger.log("Login failed - wrong password: " + email);
            return null;
        }

        FileLogger.log("Login success: " + email + " (" + user.getRole() + ")");
        return user;
    }
}