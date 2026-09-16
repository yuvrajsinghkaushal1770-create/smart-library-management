package com.library.menu;

import com.library.model.Book;
import com.library.model.User;
import com.library.service.BookService;

import java.util.List;
import java.util.Scanner;

public class MemberMenu {

    private Scanner sc;
    private User currentUser;
    private BookService bookService;

    public MemberMenu(Scanner sc, User user) {
        this.sc = sc;
        this.currentUser = user;
        this.bookService = new BookService();
    }

    public void show() {
        while (true) {
            System.out.println("\n--- Member Menu ---");
            System.out.println("1. Search Books by Title");
            System.out.println("2. Search Books by Author");
            System.out.println("3. View All Books");
            System.out.println("4. Logout");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": searchTitle(); break;
                case "2": searchAuthor(); break;
                case "3": viewAll(); break;
                case "4": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void searchTitle() {
        System.out.print("Keyword: ");
        String kw = sc.nextLine().trim();
        List<Book> results = bookService.searchByTitle(kw);
        printResults(results);
    }

    private void searchAuthor() {
        System.out.print("Keyword: ");
        String kw = sc.nextLine().trim();
        List<Book> results = bookService.searchByAuthor(kw);
        printResults(results);
    }

    private void viewAll() {
        printResults(bookService.getAllBooks());
    }

    private void printResults(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        for (Book b : books) {
            System.out.println(b);
        }
    }
}