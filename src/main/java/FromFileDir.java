import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;

public class FromFileDir {
    private final Path rootFolderPath;

    FromFileDir(String rootFolderPath) {
        this.rootFolderPath = Paths.get(rootFolderPath);
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
            System.out.println("id");
            System.out.println("Название файла =" + fondName);
            System.out.println("Путь до файла =" + pathToFolder.toAbsolutePath());
            System.out.println("Вес файлов =");
            System.out.println("Количество файлов: =" + filesCount);
            System.out.println("Автор =");
            System.out.println("Дата =");
            System.out.println("------------------");
        }
    }

    String getFondName(File file) {
        return file.toString().substring(rootFolderPath.toString().length()+1).replace("\\","_");
    }
}
