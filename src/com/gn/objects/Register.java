package com.gn.objects;

import com.gn.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Register {
    private String user_Id;
    private String username ;
    private String name;
    private String email;
    private String password;;
    private String roles;


//    public ArrayList<String> login(String username, String password) throws Exception {
//        ArrayList<String> str = new ArrayList<>();
//        try {
//            Connection connection = Database.connect("localhost/se_db", "root", "");
//            String query = "SELECT * FROM users WHERE username = '" +username+"' AND password = '" +password+"'";
//
//            Statement psmt = connection.createStatement();
//            ResultSet rs = psmt.executeQuery(query);
//
//            str.add(rs.getString("username"));
//            str.add(rs.getString("password"));
//            str.add(rs.getString("name"));
//            connection.close();
//
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//        if(str != null)
//            return str;
//        else return null;
//    }

    public void addUser(String username, String name, String email, String password) throws Exception {
        try {
            Connection connection = Database.connect("localhost/se_db", "root", "");
            String query = "INSERT INTO users(username,name,email,password) VALUES ('" + username + "', '" + name +
                    "', '"+email+"','"+password+"') ";
            // เก็บไว้ใช้ต่อ
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(query);

            // ไม่ต้องเก็บ
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.execute();

            connection.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Register getUser(ArrayList str) throws Exception{
        Register user = new Register();
        user.setUser_Id((String) str.get(0));
        user.setUsername((String) str.get(1));
        user.setName((String) str.get(2));
        user.setEmail((String) str.get(3));
        user.setPassword((String) str.get(4));
        user.setRoles((String) str.get(5));
        System.out.println(user.getUsername());
        return user;
    }


    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
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
