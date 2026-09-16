package com.library.db;

import com.library.model.Book;
import com.library.model.BookStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public boolean addBook(Book b) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO books (title, author, isbn, status) VALUES (?, ?, ?, ?)"
            );
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getIsbn());
            ps.setString(4, b.getStatus().name());
            int rows = ps.executeUpdate();
            ps.close();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("addBook error: " + e.getMessage());
        }
        return false;
    }

    public Book findById(int id) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM books WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book b = mapRow(rs);
                rs.close();
                ps.close();
                return b;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("findById error: " + e.getMessage());
        }
        return null;
    }

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("getAllBooks error: " + e.getMessage());
        }
        return list;
    }

    public List<Book> searchByTitle(String keyword) {
        List<Book> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM books WHERE title LIKE ?");
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("searchByTitle error: " + e.getMessage());
        }
        return list;
    }

    public List<Book> searchByAuthor(String keyword) {
        List<Book> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM books WHERE author LIKE ?");
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("searchByAuthor error: " + e.getMessage());
        }
        return list;
    }

    public boolean updateStatus(int bookId, BookStatus status) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE books SET status = ? WHERE id = ?");
            ps.setString(1, status.name());
            ps.setInt(2, bookId);
            int rows = ps.executeUpdate();
            ps.close();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("updateStatus error: " + e.getMessage());
        }
        return false;
    }

    private Book mapRow(ResultSet rs) throws SQLException {
        Book b = new Book();
        b.setId(rs.getInt("id"));
        b.setTitle(rs.getString("title"));
        b.setAuthor(rs.getString("author"));
        b.setIsbn(rs.getString("isbn"));
        b.setStatus(BookStatus.valueOf(rs.getString("status")));
        return b;
    }
}