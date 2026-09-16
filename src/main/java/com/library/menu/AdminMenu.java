package com.library.menu;

import com.library.exception.InvalidInputException;
import com.library.model.User;
import com.library.service.ReportService;
import com.library.service.UserService;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private Scanner sc;
    private UserService userService;
    private ReportService reportService;

    public AdminMenu(Scanner sc) {
        this.sc = sc;
        this.userService = new UserService();
        this.reportService = new ReportService();
    }

    public void show() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Member");
            System.out.println("2. Add Librarian");
            System.out.println("3. View All Users");
            System.out.println("4. Delete User");
            System.out.println("5. Export Report");
            System.out.println("6. Logout");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1": addMember(); break;
                    case "2": addLibrarian(); break;
                    case "3": viewUsers(); break;
                    case "4": deleteUser(); break;
                    case "5": reportService.exportReportToCSV("report.csv"); break;
                    case "6": return;
                    default: System.out.println("Invalid choice.");
                }
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addMember() throws InvalidInputException {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Password: ");
        String pass = sc.nextLine().trim();
        System.out.print("Phone: ");
        String phone = sc.nextLine().trim();

        boolean ok = userService.addMember(name, email, pass, phone);
        System.out.println(ok ? "Member added." : "Failed to add member.");
    }

    private void addLibrarian() throws InvalidInputException {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Password: ");
        String pass = sc.nextLine().trim();
        System.out.print("Employee Code: ");
        String code = sc.nextLine().trim();

        boolean ok = userService.addLibrarian(name, email, pass, code);
        System.out.println(ok ? "Librarian added." : "Failed to add librarian.");
    }

    private void viewUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users.");
            return;
        }
        for (User u : users) {
            System.out.println(u);
        }
    }

    private void deleteUser() throws InvalidInputException {
        System.out.print("User ID to delete: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        boolean ok = userService.deleteUser(id);
        System.out.println(ok ? "User deleted." : "Delete failed.");
    }
}