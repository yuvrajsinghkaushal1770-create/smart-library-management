package com.library.service;

import com.library.db.BookDAO;
import com.library.db.LoanDAO;
import com.library.db.UserDAO;
import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.User;
import com.library.util.FileLogger;

import java.io.FileWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    private BookDAO bookDAO;
    private LoanDAO loanDAO;
    private UserDAO userDAO;

    public ReportService() {
        this.bookDAO = new BookDAO();
        this.loanDAO = new LoanDAO();
        this.userDAO = new UserDAO();
    }

    public Map<String, Integer> mostBorrowedBooks() {
        Map<String, Integer> counts = new HashMap<>();
        List<Loan> loans = loanDAO.getAllLoans();
        List<Book> books = bookDAO.getAllBooks();

        for (Loan l : loans) {
            for (Book b : books) {
                if (b.getId() == l.getBookId()) {
                    String title = b.getTitle();
                    counts.put(title, counts.getOrDefault(title, 0) + 1);
                }
            }
        }
        return counts;
    }

    public void printOverdueLoans() {
        LocalDate today = LocalDate.now();
        List<Loan> loans = loanDAO.getAllLoans();

        System.out.println("--- Overdue Loans ---");
        boolean any = false;
        for (Loan l : loans) {
            if (!l.isReturned() && l.getDueDate().isBefore(today)) {
                System.out.println("Loan #" + l.getId()
                        + " | User: " + l.getUserId()
                        + " | Book: " + l.getBookId()
                        + " | Due: " + l.getDueDate());
                any = true;
            }
        }
        if (!any) {
            System.out.println("No overdue loans.");
        }
    }

    public void exportReportToCSV(String filename) {
        try {
            FileWriter fw = new FileWriter(filename);
            fw.write("Type,Detail,Count\n");

            Map<String, Integer> borrowed = mostBorrowedBooks();
            for (Map.Entry<String, Integer> e : borrowed.entrySet()) {
                fw.write("Book," + e.getKey() + "," + e.getValue() + "\n");
            }

            List<User> users = userDAO.getAllUsers();
            for (User u : users) {
                fw.write("User," + u.getName() + "," + u.getRole() + "\n");
            }

            fw.close();
            FileLogger.log("Report exported: " + filename);
            System.out.println("Report saved to " + filename);
        } catch (Exception e) {
            System.out.println("Export error: " + e.getMessage());
        }
    }
}