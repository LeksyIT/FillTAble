
import org.apache.commons.io.FileUtils;
import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Stream;

public class ToDB {
    private final Path rootFolderPath;

    private List<String[]> digitDocsFromDb = new ArrayList<>();

    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String login = "postgres";
    private String password = "8426874123695a";
    private Connection connection;

    ToDB(String rootFolderPath) {
        this.rootFolderPath = Paths.get(rootFolderPath);
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    void processRootFolder() {
//        try (Stream<Path> paths = Files.find(rootFolderPath, Integer.MAX_VALUE, (path, attributes) -> attributes.isDirectory())) {
//            paths.forEach(this::processFolder);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    void processFolder(Path pathToFolder) {
//        File folder = pathToFolder.toFile();
//        int filesCount = folder.listFiles(file -> !file.isDirectory()).length;
//        if (filesCount != 0 && !pathToFolder.equals(rootFolderPath)) {
//            System.out.println(pathToFolder.toAbsolutePath());
//            System.out.println("Количество файлов:" + filesCount);
//            String fondName = getFondName(folder);
//            System.out.println(fondName);
//            System.out.println("------------------");
//        }
//    }
//
//    String getFondName(File file) {
//        return file.toString().substring(rootFolderPath.toString().length()).replace("\\","_");
//    }

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
    public void query(){
        String queryDB = "select *" +
                "from test_table" +
                "where name like '3_1_1%'";
        readDB(queryDB);
    }
    private void readDB(String SQL) {
        digitDocsFromDb.clear();
        DecimalFormat form = new DecimalFormat("0.00");
        try {
            Statement statement = connection.prepareStatement(SQL);
            System.out.println(statement.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
