package test;

import com.gn.Database.Database;
import com.gn.objects.Seat;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SeatTest {

    @Test
    public void createSeatsAndReadSeats() {
        try {
            Connection connection = Database.getConnection();
            ArrayList<Seat> seats = Seat.createSeats(connection,0);
            ArrayList<Seat> seats_read = Seat.readSeat(connection,0);
            for (int i = 0 ; i < seats_read.size() ; i++){
                assertEquals(seats.get(i).getSeat_name(),seats_read.get(i).getSeat_name());
            }
            String sql = "DELETE FROM seats WHERE showtime_id = 0;";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception ex){
            ex.printStackTrace();
            fail();
        }
    }
}