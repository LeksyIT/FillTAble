import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;
import java.sql.Date;

import Db.DbExporter;
import Db.DbImporter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Filler {
    private final Path rootFolderPath;
    private DbExporter dbExporter;
    private DbImporter dbImporter;
    private final long MB = 1024 * 1024;

    Filler(String rootFolderPath) {
        this.rootFolderPath = Paths.get(rootFolderPath);
        dbExporter = new DbExporter();
        dbImporter = new DbImporter();
    }

    void processRootFolder() {
        try (Stream<Path> paths = Files.find(rootFolderPath, Integer.MAX_VALUE, (path, attributes) -> attributes.isDirectory())) {
            paths.forEach(this::processFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processFolder(Path pathToFolder) {
        File folder = pathToFolder.toFile();
        int filesCount = folder.listFiles(file -> !file.isDirectory()).length;
        if (filesCount != 0 && !pathToFolder.equals(rootFolderPath)) {
            String fondName = getFondName(folder);
            double file_size = FileUtils.sizeOfDirectory(folder) / MB;
            file_size = Math.round(file_size * 100.0) / 100.0;
            Pair<Integer, Date> executorAndDate = dbExporter.queryDB(fondName);
            if (checker(fondName, file_size, executorAndDate)) {
                dbImporter.appendToMainDB(fondName, executorAndDate.getLeft(), executorAndDate.getRight(), filesCount, file_size);
                System.out.println("Название файла = " + fondName);
                System.out.println("Путь до файла = " + pathToFolder.toAbsolutePath());
                System.out.println("Вес файлов = " + file_size);
                System.out.println("Количество файлов: = " + filesCount);
                System.out.println("Автор = " + executorAndDate.getLeft());
                System.out.println("Дата = " + executorAndDate.getRight());
                System.out.println("------------------");
            } else {
                executorAndDate = executorAndDateChecker(executorAndDate);
                System.out.println("C ФОД #" + fondName + " Что-то не так");
                dbImporter.appendToExDB(fondName, executorAndDate.getLeft(), executorAndDate.getRight(), filesCount, file_size);
            }
        }
    }

    private boolean checker(String fondName, double file_size, Pair<Integer, Date> executorAndDate) {
        if (executorAndDate == null || (executorAndDate.getLeft() == null && executorAndDate.getRight() == null)) {
            return false;
        } else if (executorAndDate.getLeft() == null) {
            return false;
        } else if (executorAndDate.getRight() == null) {
            return false;
        } else return !fondName.equals("") && !(file_size <= 0);
    }

    ;

    private Pair<Integer, Date> executorAndDateChecker(Pair<Integer, Date> executorAndDate) {
        if (executorAndDate == null || (executorAndDate.getLeft() == null && executorAndDate.getRight() == null)) {
            return executorAndDate = new ImmutablePair<>(0, new Date(1, 1, 1970));
        } else if (executorAndDate.getLeft() == null) {
            return executorAndDate = new ImmutablePair<>(0, executorAndDate.getRight());
        } else if (executorAndDate.getRight() == null)
            return executorAndDate = new ImmutablePair<>(executorAndDate.getLeft(), new Date(1, 1, 1970));
        else {
            return executorAndDate;
        }
    }

    private String getFondName(File file) {
        return file.toString().substring(rootFolderPath.toString().length() + 1).replace("\\", "_");
    }
}
