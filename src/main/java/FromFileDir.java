import java.io.*;
import java.nio.file.*;
import java.util.Date;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

public class FromFileDir {
    private final Path rootFolderPath;
    private UploadToDB uploadToDB;

    FromFileDir(String rootFolderPath) {
        this.rootFolderPath = Paths.get(rootFolderPath);
        uploadToDB = new UploadToDB();
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
            Pair<Integer, Date> executorAndDate = uploadToDB.queryDB(fondName);
            System.out.println("id");
            System.out.println("Название файла =" + fondName);
            System.out.println("Путь до файла =" + pathToFolder.toAbsolutePath());
            System.out.println("Вес файлов =" + FileUtils.sizeOfDirectory(folder)/1048576);
            System.out.println("Количество файлов: =" + filesCount);
            System.out.println("Автор =" + executorAndDate.getLeft());
            System.out.println("Дата =" + executorAndDate.getRight());
            System.out.println("------------------");
        }
    }

    String getFondName(File file) {
        return file.toString().substring(rootFolderPath.toString().length()+1).replace("\\","_");
    }
}
