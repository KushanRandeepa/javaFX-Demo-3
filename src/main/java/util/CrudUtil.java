package util;

import DB.DbConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {

    public static <T> T execute(String SQL, Object... args) throws SQLException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(SQL);

        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }

        if (SQL.startsWith("SELECT") || SQL.startsWith("select")) {
            return (T) preparedStatement.executeQuery();
        } else {
            return (T) (Boolean) (preparedStatement.executeUpdate() > 0);
        }
    }
}
