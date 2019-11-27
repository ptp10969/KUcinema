package com.gn.objects;

import java.sql.*;
import java.util.ArrayList;

public class ShowTime {
    private int id;
    private String time;
    private int program_id;
    private ArrayList<Seat> seats;

    public ShowTime(int id, String time , int program_id, ArrayList<Seat> seats) {
        this.program_id = program_id;
        this.id = id;
        this.time = time;
        this.seats = seats;
    }

    public static ShowTime createShowTime(Connection connection, String time , int program_id , Date date){
        ShowTime show_time = null ;
        try {
            String sql = "INSERT INTO showtimes(time,program_id,date)"
                    + "VALUES(?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, time);
            pstmt.setInt(2, program_id);
            pstmt.setDate(3,date);
            pstmt.executeUpdate();
            String query = "Select * from showtimes order by id desc";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.first();
            int id = resultSet.getInt(1);
            show_time = new ShowTime(id,time,program_id,Seat.createSeats(connection,id));
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return show_time;
    }

    public static ArrayList<ShowTime> readShowTime(Connection connection , int program_id){
        ArrayList<ShowTime> showTimes = new ArrayList<>();
        try {
            String query = "Select * from showtimes where program_id = " + program_id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String time = resultSet.getString(2);
                int showtime_program_id = resultSet.getInt(3);
                showTimes.add(new ShowTime(id,time,showtime_program_id,Seat.readSeat(connection,id)));
            }
        } catch (Exception ex){
            System.out.println("read showtime Error");
            ex.printStackTrace();
        }
        return showTimes;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
