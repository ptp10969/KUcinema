package com.gn.objects;

import com.gn.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Register {
    private String userId ;
    private String name;
    private String email;
    private String password;

    public void addUser(String userId, String name, String email, String password) throws Exception {
        try {
            Connection connection = Database.connect("localhost/se_db", "root", "");
            String query = "INSERT INTO users(user_id,name,email,password) VALUES ('" + userId + "', '" + name +
                    "', '"+email+"','"+password+"') ";
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(query);

            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.execute();

            connection.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
