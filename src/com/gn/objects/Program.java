package com.gn.objects;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Program {
    private int id;
    private Movie movie;
    private Date date;
    private ArrayList<ShowTime> showTimes;

    public Program(int id , Movie movie , Date date) {
        this.id = id;
        this.date = date;
        this.movie = movie;
        this.showTimes = new ArrayList<>();
    }

    public Program(int id, Movie movie, Date date, ArrayList<ShowTime> showTimes) {
        this.id = id;
        this.movie = movie;
        this.date = date;
        this.showTimes = showTimes;
    }

    public static Program create(Connection connection , Movie movie , Date date){
        Program program = null;
        try {
            String sql = "INSERT INTO programs(movie_id,date)"
                    + "VALUES(?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, movie.getId());
            pstmt.setDate(2, date);
            pstmt.executeUpdate();
            String query = "Select * from programs order by id desc";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.first();
            int id = resultSet.getInt(1);
            program = new Program(id,movie,date);
        } catch (Exception ex){ }
        return program;
    }

    public void addShowTime(Connection connection , String showtime){
        showTimes.add(ShowTime.createShowTime(connection,showtime,id));
    }

    public static ArrayList<Program> readProgram(Connection connection , HashMap<String,Movie> movies){
        ArrayList<Program> programs = new ArrayList<>();
        try {
            String query = "Select * from programs";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                int movie_id = resultSet.getInt(2);
                Date date = resultSet.getDate(3);
                programs.add(new Program(id,movies.get(Integer.toString(movie_id)),date,ShowTime.readShowTime(connection,id)));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return programs;
    }

    public int getId() {
        return id;
    }
}
