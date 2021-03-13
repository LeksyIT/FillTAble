import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;

import Db.DbExporter;

public class Filler {
    private final Path rootFolderPath;
    private DbExporter dbExporter;
    private final long MB = 1024*1024;

    Filler(String rootFolderPath) {
        this.rootFolderPath = Paths.get(rootFolderPath);
        dbExporter = new DbExporter();
    }

    void processRootFolder() {
        try (Stream<Path> paths = Files.find(rootFolderPath, Integer.MAX_VALUE, (path, attributes) -> attributes.isDirectory())) {
            paths.forEach(this::processFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void processFolder(Path pathToFolder) {
        File folder = pathToFolder.toFile();
        int filesCount = folder.listFiles(file -> !file.isDirectory()).length;
        if (filesCount != 0 && !pathToFolder.equals(rootFolderPath)) {
            String fondName = getFondName(folder);
            System.out.println(fondName );
//            double file_size = FileUtils.sizeOfDirectory(folder)/MB;
//            file_size = Math.round(file_size * 100.0) / 100.0;
//            Pair<Integer, Date> executorAndDate = uploadToDB.queryDB(fondName);
//            System.out.println("id");
//            System.out.println("Название файла =" + fondName);
//            System.out.println("Путь до файла =" + pathToFolder.toAbsolutePath());
//            System.out.println("Вес файлов =" + file_size);
//            System.out.println("Количество файлов: =" + filesCount);
//            System.out.println("Автор =" + executorAndDate.getLeft());
//            System.out.println("Дата =" + executorAndDate.getRight());
//            System.out.println("------------------");
        }
    }

    String getFondName(File file) {
        return file.toString().substring(rootFolderPath.toString().length()+1).replace("\\","_");
    }
}
