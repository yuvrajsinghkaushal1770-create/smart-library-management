package com.library.util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileLogger {

    private static final String LOG_FILE = "logs/app.log";

    public static void log(String message) {
        try {
            java.io.File dir = new java.io.File("logs");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileWriter fw = new FileWriter(LOG_FILE, true);
            fw.write(LocalDateTime.now() + " : " + message + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Logger error: " + e.getMessage());
        }
    }
}