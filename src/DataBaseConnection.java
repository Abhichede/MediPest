import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

/**
 * Created by abmiro on 13/12/16.
 */
public class DataBaseConnection {
    Connection connection = null;
    PreparedStatement preparedStatement;
    public Connection  getConnection()
    {
        try {
            Class.forName("com.jdbc.mysql.Driver");
            System.out.println("Creating connection...");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_management","root","root");
        }catch (Exception exception){}
        return connection;

    }
}
