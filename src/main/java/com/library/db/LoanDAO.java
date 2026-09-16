package com.library.db;

import com.library.model.Loan;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    public boolean createLoan(Loan loan) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO loans (user_id, book_id, issue_date, due_date, return_date, fine) VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setInt(1, loan.getUserId());
            ps.setInt(2, loan.getBookId());
            ps.setDate(3, Date.valueOf(loan.getIssueDate()));
            ps.setDate(4, Date.valueOf(loan.getDueDate()));
            ps.setDate(5, null);
            ps.setDouble(6, 0.0);

            int rows = ps.executeUpdate();
            ps.close();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("createLoan error: " + e.getMessage());
        }
        return false;
    }

    public Loan findById(int id) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM loans WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Loan l = mapRow(rs);
                rs.close();
                ps.close();
                return l;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("findById error: " + e.getMessage());
        }
        return null;
    }

    public List<Loan> getActiveLoansByUser(int userId) {
        List<Loan> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM loans WHERE user_id = ? AND return_date IS NULL"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("getActiveLoansByUser error: " + e.getMessage());
        }
        return list;
    }

    public boolean closeLoan(int loanId, LocalDate returnDate, double fine) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "UPDATE loans SET return_date = ?, fine = ? WHERE id = ?"
            );
            ps.setDate(1, Date.valueOf(returnDate));
            ps.setDouble(2, fine);
            ps.setInt(3, loanId);
            int rows = ps.executeUpdate();
            ps.close();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("closeLoan error: " + e.getMessage());
        }
        return false;
    }

    public List<Loan> getAllLoans() {
        List<Loan> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM loans");
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("getAllLoans error: " + e.getMessage());
        }
        return list;
    }

    private Loan mapRow(ResultSet rs) throws SQLException {
        Loan l = new Loan();
        l.setId(rs.getInt("id"));
        l.setUserId(rs.getInt("user_id"));
        l.setBookId(rs.getInt("book_id"));
        l.setIssueDate(rs.getDate("issue_date").toLocalDate());
        l.setDueDate(rs.getDate("due_date").toLocalDate());

        Date ret = rs.getDate("return_date");
        if (ret != null) {
            l.setReturnDate(ret.toLocalDate());
        }
        l.setFine(rs.getDouble("fine"));
        return l;
    }
}