package com.library;

import com.library.db.DBConnection;
import com.library.db.SchemaInitializer;
import com.library.menu.MainMenu;

public class Main {

    public static void main(String[] args) {
        DBConnection.getConnection();
        SchemaInitializer.init();

        MainMenu menu = new MainMenu();
        menu.show();

        DBConnection.close();
    }
}