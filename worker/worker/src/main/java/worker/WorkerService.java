package worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerService {
    private final int LINES_LENGTH = 240;
    private WorkerConfiguration workerConfiguration;
    @Autowired
    WorkerService(WorkerConfiguration configuration) {
        this.workerConfiguration = configuration;
    }

    public String getExtension(String path) {
        return com.google.common.io.Files.getFileExtension(path);
    }

    public boolean isSubstring(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        return s1.contains(s2);
    }
    public List<FileRecord> traverse(String file_name) {
        List<FileRecord> files = new ArrayList<>();
        Path startPath = Paths.get(this.workerConfiguration.getBase_directory());
        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if(!isSubstring(file.toString(), file_name)){
                        return FileVisitResult.CONTINUE;
                    }
                    try {
                        String content = "";
                        if(isText(file)){
                            content = Files.readString(file);
                        }
                        else {
                            content = "Content could not be loaded. File not recognized as a text file.";
                        }
                        String extension = getExtension(file.toString());
                        String lines = content.length() <= LINES_LENGTH ? content : content.substring(0, LINES_LENGTH);
                        Long size = (Long) attrs.size();
                        files.add(new FileRecord(file.toString(), extension, size, content, lines));
                    } catch (IOException e) {
                        System.out.println(e.toString());
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    System.out.println("Access Denied: " + file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    System.out.println("Leaving Directory: " + dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println("Error while trying to parse the tre" + e.toString());
        }
        return files;
    }


    public boolean isText(Path path) {
        String type = null;
        try {
            type = Files.probeContentType(path);
        } catch (IOException ignored) {
        }
        return type != null && type.startsWith("text");
    }
}

