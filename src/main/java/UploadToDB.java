import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UploadToDB {

    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String login = "postgres";
    private String password = "8426874123695a";
    private Connection connection;

    UploadToDB() {
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    void testDatabase() {
//        try {
//            try{
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery("SELECT * FROM archive_fonds.public.test_db");
//                while (resultSet.next()) {
//                    String fod = resultSet.getString(2);
//                    System.out.println(fod);
//                }
//                resultSet.close();
//                statement.close();
//            } finally {
//                connection.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @RequestMapping(method = RequestMethod.POST)
//    public String unloadFromDbWithFilter(Model model, @ModelAttribute("documentsFilter") DocumentsFilter documentsFilter) {
//        connectToDbAndSetList(
//                "SELECT сж.Номер, сж.ФОД, и.Исполнитель, сж.Дата_оцифровки, сж.Колво_файлов, сж.Колво_мб " +
//                        "FROM archive_fonds.public.Сводный_журнал сж " +
//                        "INNER JOIN archive_fonds.public.Исполнители и ON сж.Исполнитель = и.Код_исполнителя " +
//                        "WHERE (сж.Дата_оцифровки BETWEEN " + documentsFilter.getDateFrom() + " AND " + documentsFilter.getDateTo() + ") " +
//                        "AND и.Исполнитель LIKE '%" + documentsFilter.getExecutor()+"%'");
//        model.addAttribute("digitDocsFromDb", digitDocsFromDb);
//        return "unloadJournal";
//    }

    //    private void connectToDbAndSetList(String SQL) {
//        digitDocsFromDb.clear();
//        DecimalFormat form = new DecimalFormat("0.00");
//        try {
//            Statement statement = connection.prepareStatement(SQL);
//            ResultSet resultSet = statement.executeQuery(SQL);
//            while (resultSet.next()) {
//                String[] names = resultSet.getString(2).split("_");
//                digitDocsFromDb.add(new String[]{
//                        String.valueOf(resultSet.getInt(1)),
//                        newName,
//                        resultSet.getString(3),
//                        String.valueOf(resultSet.getDate(4)),
//                        String.valueOf(resultSet.getInt(5)),
//                        form.format(resultSet.getDouble(6))});
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public Pair<Integer, Date> queryDB(String fod) {
        String queryDB = "select * " +
                "from test_table " +
                "where name like " + "'" + fod + "%'";
        return readDB(queryDB);
    }

    private Pair<Integer, Date> readDB(String SQL){
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement(SQL).executeQuery();
            resultSet.next();
            return new ImmutablePair<Integer, Date>(resultSet.getInt("executor"), resultSet.getDate("date"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
