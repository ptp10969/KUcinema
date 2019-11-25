package com.gn.objects;

import com.gn.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Register {
    private String id;
    private String userId ;
    private String name;
    private String email;
    private String password;;
    private String roles;


    public ArrayList<String> login(String userId, String password) throws Exception {
        ArrayList<String> str = new ArrayList<>();
        try {
            Connection connection = Database.connect("localhost/se_db", "root", "");
            String query = "SELECT * FROM users WHERE user_id = '" +userId+"' AND password = '" +password+"'";

            Statement psmt = connection.createStatement();
            ResultSet rs = psmt.executeQuery(query);

            str.add(rs.getString("user_id"));
            str.add(rs.getString("name"));

            connection.close();

        } catch (Exception ex){
            ex.printStackTrace();
        }

        if(str != null)
            return str;
        else return null;
    }

    public Register getUser(String userId) throws Exception{
        try {
            Connection connection = Database.connect("localhost/se_db", "root", "");
            String query = "SELECT * FROM users WHERE user_id = '" +userId+"'";

            Statement psmt = connection.createStatement();
            ResultSet rs = psmt.executeQuery(query);

        } catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
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
