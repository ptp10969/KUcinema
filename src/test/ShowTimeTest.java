package test;

import com.gn.Database.Database;
import com.gn.objects.ShowTime;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class ShowTimeTest {

    @Test
    public void createShowTimeAndReadShowTime() {
        try {
            Connection connection = Database.getConnection();
            ShowTime showTime = ShowTime.createShowTime(connection,"11.15",0, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            ArrayList<ShowTime> showTimes = ShowTime.readShowTime(connection,0);
            assertEquals(showTime.getTime(),showTimes.get(0).getTime());
            String sql = "DELETE FROM showtimes WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,showTime.getId());
            statement.executeUpdate();
            String sql2 = "DELETE FROM seats WHERE showtime_id = " + showTime.getId();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql2);
        } catch (Exception ex){
            ex.printStackTrace();
            fail();
        }
    }
}