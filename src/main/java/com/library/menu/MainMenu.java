package com.library.menu;

import com.library.model.User;
import com.library.service.AuthService;

import java.util.Scanner;

public class MainMenu {

    private Scanner sc;
    private AuthService authService;

    public MainMenu() {
        this.sc = new Scanner(System.in);
        this.authService = new AuthService();
    }

    public void show() {
        while (true) {
            System.out.println("\n===== SMART LIBRARY MANAGEMENT =====");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                login();
            } else if (choice.equals("2")) {
                System.out.println("Bye.");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private void login() {
        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        User user = authService.login(email, password);

        if (user == null) {
            System.out.println("Login failed.");
            return;
        }

        System.out.println("Welcome, " + user.getName() + " [" + user.getRole() + "]");

        switch (user.getRole()) {
            case "ADMIN":
                new AdminMenu(sc).show();
                break;
            case "LIBRARIAN":
                new LibrarianMenu(sc).show();
                break;
            case "MEMBER":
                new MemberMenu(sc, user).show();
                break;
            default:
                System.out.println("Unknown role.");
        }
    }
}