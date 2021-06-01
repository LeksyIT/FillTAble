package Db;

import java.sql.*;

public class DbImporter {

    private final Connection connection;

    public DbImporter() {
        connection = DbConfig.getConnection();
    }

    public void appendToMainDB(String file_name, Integer executor, Date date, Integer filesCount, Double size) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(
                    "INSERT INTO postgres.public.\"Сводный журнал\" VALUES(DEFAULT,?,?,?,?,?)");
            prepareStatement.setString(1, file_name);
            prepareStatement.setInt(2, executor);
            prepareStatement.setDate(5, date);
            prepareStatement.setInt(3, filesCount);
            prepareStatement.setDouble(4, size);
            prepareStatement.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.out.println("Ошибка добавления");
        }
    }
    public void appendToExDB(String file_name, Integer executor, Date date, Integer filesCount, Double size) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(
                    "INSERT INTO postgres.public.\"Сводный журнал ошибок\" VALUES(DEFAULT,?,?,?,?,?)");
            prepareStatement.setString(1, file_name);
            prepareStatement.setInt(2, executor);
            prepareStatement.setDate(5, date);
            prepareStatement.setInt(3, filesCount);
            prepareStatement.setDouble(4, size);
            prepareStatement.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.out.println("Ошибка добавления");
        }
    }
}
