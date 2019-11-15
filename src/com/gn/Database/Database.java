package com.gn.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public static Connection connect(String url , String username ,String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String database_url = "jdbc:mysql://" + url ;
            Connection connection = DriverManager.getConnection(database_url,username,password);
            return connection;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
