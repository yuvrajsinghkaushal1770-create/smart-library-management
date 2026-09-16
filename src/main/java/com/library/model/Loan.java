package com.library.model;

import java.time.LocalDate;

public class Loan {

    private int id;
    private int userId;
    private int bookId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;

    public Loan() {}

    public Loan(int id, int userId, int bookId, LocalDate issueDate, LocalDate dueDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.fine = 0.0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public double getFine() { return fine; }
    public void setFine(double fine) { this.fine = fine; }

    public boolean isReturned() {
        return returnDate != null;
    }

    @Override
    public String toString() {
        return "Loan#" + id + " | User:" + userId + " | Book:" + bookId +
               " | Issued:" + issueDate + " | Due:" + dueDate +
               " | Returned:" + (returnDate == null ? "No" : returnDate) +
               " | Fine:" + fine;
    }
}