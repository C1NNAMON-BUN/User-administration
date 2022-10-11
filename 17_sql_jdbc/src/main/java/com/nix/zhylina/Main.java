package com.nix.zhylina;

import com.nix.zhylina.database.InitializeDatabase;

/**
 * <p><h2>Main</h2>
 * <b>The class in which the file is executed</b> <br>
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 26.01.2022 </p>
 */
public class Main {
    public static void main(String[] a) {
        InitializeDatabase initializeDatabase = new InitializeDatabase();
        initializeDatabase.dropTables();
        initializeDatabase.generateTables();
        initializeDatabase.initializeBase();
    }
}

