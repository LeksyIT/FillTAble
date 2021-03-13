import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UnloadToDB {

    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String login = "postgres";
    private String password = "8426874123695a";
    private Connection connection;

    UnloadToDB() {
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Pair<Integer, Date> queryDB(String fod) {
        String queryDB = "select * " +
                "from test_table " +
                "where name like " + "'" + fod + "%'";
        System.out.println(queryDB);
        return readDB(queryDB);
    }

    private Pair<Integer, Date> readDB(String SQL) {
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement(SQL).executeQuery();
            resultSet.next();
            return new ImmutablePair<>(resultSet.getInt("executor"), resultSet.getDate("date"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
