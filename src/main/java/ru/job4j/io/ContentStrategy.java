package ru.job4j.io;

import java.io.IOException;
import java.io.InputStream;

public interface ContentStrategy {
    String getContent(InputStream input) throws IOException;
}
