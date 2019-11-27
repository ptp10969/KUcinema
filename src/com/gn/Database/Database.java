package com.gn.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;

    public Database() {
        connection = connect();
    }

    public Connection connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "localhost/se_db";
            String username = "root";
            String password = "";
            String database_url = "jdbc:mysql://" + url ;
            Connection conn = DriverManager.getConnection(database_url,username,password);
            return conn;
        } catch (Exception ex){
            System.out.println("connect error");
        }
        return null;
    }

    public static Connection getConnection(){
        try {
            if (connection == null || connection.isClosed()){
                new Database();
                System.out.println("connect *-*");
            }
        }  catch (SQLException ex){}
        return connection;
    }
}
