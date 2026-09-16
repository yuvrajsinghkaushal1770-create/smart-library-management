package com.library.util;

import com.library.exception.InvalidInputException;

public class InputValidator {

    public static void validateEmail(String email) throws InvalidInputException {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new InvalidInputException("Invalid email: " + email);
        }
    }

    public static void validateName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name cannot be empty");
        }
    }

    public static void validatePassword(String pass) throws InvalidInputException {
        if (pass == null || pass.length() < 4) {
            throw new InvalidInputException("Password must be at least 4 characters");
        }
    }

    public static int parsePositiveInt(String value) throws InvalidInputException {
        try {
            int n = Integer.parseInt(value.trim());
            if (n <= 0) {
                throw new InvalidInputException("Number must be positive");
            }
            return n;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Not a valid number: " + value);
        }
    }
}