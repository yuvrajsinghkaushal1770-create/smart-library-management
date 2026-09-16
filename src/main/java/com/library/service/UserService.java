package com.library.service;

import com.library.db.UserDAO;
import com.library.exception.InvalidInputException;
import com.library.model.Librarian;
import com.library.model.Member;
import com.library.model.User;
import com.library.util.FileLogger;
import com.library.util.InputValidator;

import java.util.List;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean addMember(String name, String email, String password, String phone)
            throws InvalidInputException {

        InputValidator.validateName(name);
        InputValidator.validateEmail(email);
        InputValidator.validatePassword(password);

        Member m = new Member(0, name, email, password, phone);
        boolean ok = userDAO.addUser(m);

        if (ok) {
            FileLogger.log("Member added: " + email);
        }
        return ok;
    }

    public boolean addLibrarian(String name, String email, String password, String empCode)
            throws InvalidInputException {

        InputValidator.validateName(name);
        InputValidator.validateEmail(email);
        InputValidator.validatePassword(password);

        Librarian l = new Librarian(0, name, email, password, empCode);
        boolean ok = userDAO.addUser(l);

        if (ok) {
            FileLogger.log("Librarian added: " + email);
        }
        return ok;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean deleteUser(int id) {
        boolean ok = userDAO.deleteUser(id);
        if (ok) {
            FileLogger.log("User deleted: id=" + id);
        }
        return ok;
    }
}