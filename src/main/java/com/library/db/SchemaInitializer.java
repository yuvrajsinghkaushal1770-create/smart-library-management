package com.library.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

public class SchemaInitializer {

    public static void init() {
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return;

            BufferedReader reader = new BufferedReader(new FileReader("schema/schema.sql"));
            StringBuilder sql = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("--") || line.trim().isEmpty()) {
                    continue;
                }
                sql.append(line).append(" ");
            }
            reader.close();

            String[] statements = sql.toString().split(";");

            Statement stmt = con.createStatement();
            for (String s : statements) {
                if (s.trim().isEmpty()) continue;
                stmt.execute(s.trim());
            }
            stmt.close();

            System.out.println("Schema ready.");
        } catch (Exception e) {
            System.out.println("Schema init error: " + e.getMessage());
        }
    }
}