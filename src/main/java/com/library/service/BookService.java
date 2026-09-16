package com.library.service;

import com.library.db.BookDAO;
import com.library.db.LoanDAO;
import com.library.db.UserDAO;
import com.library.exception.BookNotAvailableException;
import com.library.exception.InvalidInputException;
import com.library.exception.UserNotFoundException;
import com.library.model.Book;
import com.library.model.BookStatus;
import com.library.model.Loan;
import com.library.model.User;
import com.library.util.FineCalculator;
import com.library.util.FileLogger;

import java.time.LocalDate;
import java.util.List;

public class BookService {

    private BookDAO bookDAO;
    private LoanDAO loanDAO;
    private UserDAO userDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
        this.loanDAO = new LoanDAO();
        this.userDAO = new UserDAO();
    }

    public boolean addBook(String title, String author, String isbn)
            throws InvalidInputException {

        if (title == null || title.trim().isEmpty()) {
            throw new InvalidInputException("Title cannot be empty");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new InvalidInputException("Author cannot be empty");
        }

        Book b = new Book(0, title, author, isbn);
        boolean ok = bookDAO.addBook(b);

        if (ok) {
            FileLogger.log("Book added: " + title);
        }
        return ok;
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public List<Book> searchByTitle(String keyword) {
        return bookDAO.searchByTitle(keyword);
    }

    public List<Book> searchByAuthor(String keyword) {
        return bookDAO.searchByAuthor(keyword);
    }

    public void issueBook(int userId, int bookId)
            throws UserNotFoundException, BookNotAvailableException {

        User user = userDAO.findByEmail(getUserEmailById(userId));
        if (user == null) {
            throw new UserNotFoundException("User not found with id " + userId);
        }

        Book book = bookDAO.findById(bookId);
        if (book == null) {
            throw new BookNotAvailableException("Book not found with id " + bookId);
        }
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new BookNotAvailableException("Book is not available: " + book.getTitle());
        }

        List<Loan> active = loanDAO.getActiveLoansByUser(userId);
        if (active.size() >= 3) {
            throw new BookNotAvailableException("User already has 3 books issued");
        }

        LocalDate today = LocalDate.now();
        LocalDate due = today.plusDays(14);

        Loan loan = new Loan(0, userId, bookId, today, due);
        loanDAO.createLoan(loan);
        bookDAO.updateStatus(bookId, BookStatus.ISSUED);

        FileLogger.log("Book issued: bookId=" + bookId + " to userId=" + userId);
    }

    public double returnBook(int loanId) throws InvalidInputException {

        Loan loan = loanDAO.findById(loanId);
        if (loan == null) {
            throw new InvalidInputException("Loan not found: id=" + loanId);
        }
        if (loan.isReturned()) {
            throw new InvalidInputException("Loan already returned");
        }

        LocalDate today = LocalDate.now();

        FineCalculator calc = new FineCalculator(loan.getDueDate(), today);
        Thread t = new Thread(calc);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        double fine = calc.getFine();
        loanDAO.closeLoan(loanId, today, fine);
        bookDAO.updateStatus(loan.getBookId(), BookStatus.AVAILABLE);

        FileLogger.log("Book returned: loanId=" + loanId + " fine=" + fine);
        return fine;
    }

    private String getUserEmailById(int id) {
        List<User> users = userDAO.getAllUsers();
        for (User u : users) {
            if (u.getId() == id) {
                return u.getEmail();
            }
        }
        return "";
    }
}