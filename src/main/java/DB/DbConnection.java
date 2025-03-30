package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static DbConnection instance;
    private   Connection connection ;

    private DbConnection() throws SQLException {
        this.connection  = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "Kushan23");

    }

    public  Connection getConnection(){
        return connection;
    }
    public static DbConnection getInstance() throws SQLException {
        if(instance==null){
            return instance=new DbConnection();
        }
        return instance;
    }
}
