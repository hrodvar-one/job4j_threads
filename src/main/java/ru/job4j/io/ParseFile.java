package ru.job4j.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Integer> filter) throws IOException {
        FileInputStream input = new FileInputStream(file);
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = input.read()) > 0) {
            if (filter.test(data)) {
                output.append((char) data);
            }
        }
        input.close();
        return output.toString();
    }

    public String getContent() throws IOException {
        return getContent(data -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(data -> data < 0x80);
    }
}
