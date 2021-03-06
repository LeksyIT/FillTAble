package Db;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import java.sql.*;

public class DbExporter {
    private final Connection connection;

    public DbExporter() {
        connection = DbConfig.getConnection();
    }

    public Pair<Integer, Date> queryDB(String fod) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select * from test_table where name like ?");
            preparedStatement.setString(1, fod + '%');
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return readDB(preparedStatement);
    }

    private Pair<Integer, Date> readDB(PreparedStatement preparedStatement) {
        if (preparedStatement == null) {
            return null;
        }
        ResultSet resultSet;
        try {
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new ImmutablePair<>(resultSet.getInt("executor"), resultSet.getDate("date"));
            }
            else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
