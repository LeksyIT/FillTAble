package Db;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.*;

public class DbImporter {

    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String login = "postgres";
    private String password = "8426874123695a";
    private Connection connection;

    DbImporter() {
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void addToDB(String SQL,String file_name, Integer executor, Date date, Integer filesCount, Double size) {
        try{
        PreparedStatement prepareStatement = connection.prepareStatement(
                "INSERT INTO postgres.public.\"Сводный журнал\" VALUES(DEFAULT,?,?,?,?,?)");
            prepareStatement.setString(1, file_name);
            prepareStatement.setInt(2, executor);
            prepareStatement.setDate(3, date);
            prepareStatement.setInt(4, filesCount);
            prepareStatement.setDouble(5, size);
            prepareStatement.executeUpdate();
        }
        catch (java.sql.SQLException e) {
            System.out.println();
        }
    }
}
