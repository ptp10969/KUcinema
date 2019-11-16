package com.gn.objects;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Seat {
    private int id;
    private String seat_name;
    private int reserve_by;
    private float price;
    private int showtime_id;

    public Seat(int id, String seat_name, int reserve_by, float price, int showtime_id) {
        this.id = id;
        this.seat_name = seat_name;
        this.reserve_by = reserve_by;
        this.price = price;
        this.showtime_id = showtime_id;
    }

    public static ArrayList<Seat> createSeats(Connection connection , int showtime_id){
        ArrayList<Seat> seats = new ArrayList<>();
        for (int i = 0 ; i < 40 ; i++){
            String name;
            float price = 100;
            if (i < 12){
                name = "A" + ((i % 12) + 1);
            } else if ( i < 24){
                name = "B" + ((i % 12) + 1);
            } else if ( i < 36){
                name = "C" + ((i % 12) + 1);
            } else {
                name = "D" + ((i % 12) + 1);
                price = 250;
            }
            try {
                String sql = "INSERT INTO seats(name,price,showtime_id)"
                        + "VALUES(?,?,?)";
                PreparedStatement pstmt = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, name);
                pstmt.setFloat(2, price);
                pstmt.setInt(3, showtime_id);
                pstmt.executeUpdate();
                String query = "Select * from seats order by id desc";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.first();
                int id = resultSet.getInt(1);
                String seat_name = resultSet.getString(2);
                int reserve_by = resultSet.getInt(3);
                float seat_price = resultSet.getFloat(4);
                int seat_showtime_id = resultSet.getInt(5);
                seats.add(new Seat(id,seat_name,reserve_by,seat_price,seat_showtime_id));
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return seats;
    }

    public int getId() {
        return id;
    }

    public static ArrayList<Seat> readSeat(Connection connection , int showtime_id){
        ArrayList<Seat> seats = new ArrayList<>();
        try {
            String query = "Select * from seats where showtime_id = " + showtime_id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int reserve_by = resultSet.getInt(3);
                float price = resultSet.getFloat(4);
                int seat_showtime_id = resultSet.getInt(5);
                seats.add(new Seat(id,name,reserve_by,price,seat_showtime_id));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return seats;
    }
}
