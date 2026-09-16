package com.library.menu;

import com.library.exception.BookNotAvailableException;
import com.library.exception.InvalidInputException;
import com.library.exception.UserNotFoundException;
import com.library.model.Book;
import com.library.service.BookService;
import com.library.service.ReportService;

import java.util.List;
import java.util.Scanner;

public class LibrarianMenu {

    private Scanner sc;
    private BookService bookService;
    private ReportService reportService;

    public LibrarianMenu(Scanner sc) {
        this.sc = sc;
        this.bookService = new BookService();
        this.reportService = new ReportService();
    }

    public void show() {
        while (true) {
            System.out.println("\n--- Librarian Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search by Title");
            System.out.println("4. Search by Author");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. Overdue Report");
            System.out.println("8. Logout");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1": addBook(); break;
                    case "2": viewBooks(); break;
                    case "3": searchTitle(); break;
                    case "4": searchAuthor(); break;
                    case "5": issueBook(); break;
                    case "6": returnBook(); break;
                    case "7": reportService.printOverdueLoans(); break;
                    case "8": return;
                    default: System.out.println("Invalid choice.");
                }
            } catch (InvalidInputException | BookNotAvailableException | UserNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addBook() throws InvalidInputException {
        System.out.print("Title: ");
        String title = sc.nextLine().trim();
        System.out.print("Author: ");
        String author = sc.nextLine().trim();
        System.out.print("ISBN: ");
        String isbn = sc.nextLine().trim();

        boolean ok = bookService.addBook(title, author, isbn);
        System.out.println(ok ? "Book added." : "Add failed.");
    }

    private void viewBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books.");
            return;
        }
        for (Book b : books) {
            System.out.println(b);
        }
    }

    private void searchTitle() {
        System.out.print("Keyword: ");
        String kw = sc.nextLine().trim();
        List<Book> results = bookService.searchByTitle(kw);
        if (results.isEmpty()) {
            System.out.println("No matches.");
        } else {
            for (Book b : results) System.out.println(b);
        }
    }

    private void searchAuthor() {
        System.out.print("Keyword: ");
        String kw = sc.nextLine().trim();
        List<Book> results = bookService.searchByAuthor(kw);
        if (results.isEmpty()) {
            System.out.println("No matches.");
        } else {
            for (Book b : results) System.out.println(b);
        }
    }

    private void issueBook() throws UserNotFoundException, BookNotAvailableException {
        System.out.print("User ID: ");
        int userId = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Book ID: ");
        int bookId = Integer.parseInt(sc.nextLine().trim());

        bookService.issueBook(userId, bookId);
        System.out.println("Book issued.");
    }

    private void returnBook() throws InvalidInputException {
        System.out.print("Loan ID: ");
        int loanId = Integer.parseInt(sc.nextLine().trim());

        double fine = bookService.returnBook(loanId);
        System.out.println("Returned. Fine = " + fine);
    }
}