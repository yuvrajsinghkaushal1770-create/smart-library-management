package com.library.db;

import com.library.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User findByEmail(String email) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = mapRow(rs);
                rs.close();
                ps.close();
                return u;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("findByEmail error: " + e.getMessage());
        }
        return null;
    }

    public boolean addUser(User u) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users (name, email, password, role, phone, employee_code) VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRole());

            if (u instanceof Member) {
                ps.setString(5, ((Member) u).getPhone());
                ps.setString(6, null);
            } else if (u instanceof Librarian) {
                ps.setString(5, null);
                ps.setString(6, ((Librarian) u).getEmployeeCode());
            } else {
                ps.setString(5, null);
                ps.setString(6, null);
            }

            int rows = ps.executeUpdate();
            ps.close();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("addUser error: " + e.getMessage());
        }
        return false;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");

            while (rs.next()) {
                list.add(mapRow(rs));
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("getAllUsers error: " + e.getMessage());
        }
        return list;
    }

    public boolean deleteUser(int id) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE id = ?");
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            ps.close();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("deleteUser error: " + e.getMessage());
        }
        return false;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        String role = rs.getString("role");
        User u;

        if ("MEMBER".equals(role)) {
            Member m = new Member();
            m.setPhone(rs.getString("phone"));
            u = m;
        } else if ("LIBRARIAN".equals(role)) {
            Librarian l = new Librarian();
            l.setEmployeeCode(rs.getString("employee_code"));
            u = l;
        } else {
            u = new User();
        }

        u.setId(rs.getInt("id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setRole(role);
        return u;
    }
}