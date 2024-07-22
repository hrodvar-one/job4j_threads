package ru.job4j.io;

import java.io.IOException;
import java.io.InputStream;

public class FullContentStrategy implements ContentStrategy {
    @Override
    public String getContent(InputStream input) throws IOException {
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = input.read()) > 0) {
            output.append((char) data);
        }
        return output.toString();
    }
}
