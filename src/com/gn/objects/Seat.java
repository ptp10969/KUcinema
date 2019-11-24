package com.gn.objects;

import java.sql.*;
import java.util.ArrayList;

public class Seat {
    private int id;
    private String seat_name;
    private int reserve_by;
    private float price;
    private int showtime_id;
    private String status;

    public Seat(int id, String seat_name, int reserve_by, float price, int showtime_id) {
        this.id = id;
        this.seat_name = seat_name;
        this.reserve_by = reserve_by;
        this.price = price;
        this.showtime_id = showtime_id;
        if (reserve_by != 0){
            status = "not_empty";
        } else {
            status = "empty";
        }
    }

    public static ArrayList<Seat> createSeats(Connection connection , int showtime_id){
        ArrayList<Seat> seats = new ArrayList<>();
        for (int i = 0 ; i < 41 ; i++){
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

    public int getReserve_by() {
        return reserve_by;
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
            System.out.println("read seat Error");
            ex.printStackTrace();
        }
        return seats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setReserve_by(int reserve_by) {
        this.reserve_by = reserve_by;
    }

    public boolean reserve(Connection connection ,int user_id){
        String query = "UPDATE seats SET reserve_by = ? WHERE id = ? AND reserve_by = 0;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,user_id);
            preparedStatement.setInt(2,this.id);
            if (preparedStatement.executeUpdate() == 0){
                return false;
            }
        } catch (SQLException ex){
            return false;
        }
        return true;
    }

    public String getSeat_name() {
        return seat_name;
    }
}
