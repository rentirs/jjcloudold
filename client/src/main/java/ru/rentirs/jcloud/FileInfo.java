package ru.rentirs.jcloud;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FileInfo {
    public enum FileType {
        FILE("F"), DIRECTORY("D");

        public String getName() {
            return name;
        }

        FileType(String name) {
            this.name = name;
        }

        private String name;
    }

    @Getter
    @Setter
    private String filename;

    private FileType type;
    @Getter
    @Setter
    private long size;
    @Getter
    @Setter
    private LocalDateTime lastModified;

    public FileInfo(Path path) {
        try {
            this.filename = path.getFileName().toString();
            this.size = Files.size(path);
            this.type = Files.isDirectory(path) ? FileType.DIRECTORY : FileType.FILE;
            if (this.type == FileType.DIRECTORY) {
                this.size = -1L;
            }
            this.lastModified = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.ofHours(0));
        } catch (IOException e) {
            throw new RuntimeException("Unable to create file info from path");
        }
    }
}
