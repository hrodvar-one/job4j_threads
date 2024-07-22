package ru.job4j.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(ContentStrategy strategy) throws IOException {
        try (InputStream input = new FileInputStream(file)) {
            return strategy.getContent(input);
        }
    }
}
