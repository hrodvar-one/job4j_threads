package ru.job4j.thread;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private static final String DOWNLOAD_DIR = "downloads";
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var fileName = getFileNameFromURL(url);
        var file = new File(DOWNLOAD_DIR, fileName);

        createDownloadDir();

        file = getUniqueFile(file);

        long totalBytesRead = 0;
        long startTime = System.currentTimeMillis();

        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                if (totalBytesRead >= speed) {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    if (elapsedTime < 1000) {
                        long sleepTime = 1000 - elapsedTime;
                        Thread.sleep(sleepTime);
                        System.out.println("Была остановка на " + sleepTime + " мс");
                    }

                    totalBytesRead = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileNameFromURL(String url) {
        String fileName = "";
        try {
            URL urlObj = new URL(url);
            fileName = new File(urlObj.getPath()).getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private void createDownloadDir() {
        File dir = new File(DOWNLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private File getUniqueFile(File file) {
        int count = 0;
        String name = file.getName();
        String baseName = name;
        String extension = "";

        int dotIndex = name.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < name.length() - 1) {
            baseName = name.substring(0, dotIndex);
            extension = name.substring(dotIndex);
        }

        while (file.exists()) {
            count++;
            String newName = baseName + "_" + count + extension;
            file = new File(DOWNLOAD_DIR, newName);
        }

        return file;
    }

    public static boolean isValidURL(String url) {
        UrlValidator validator = new UrlValidator();
        return validator.isValid(url);
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.out.println("Не задан один из входных аргументов <URL> либо <speed>");
            return;
        }

        String url = args[0];
        if (isValidURL(url)) {
            int speed = Integer.parseInt(args[1]);
            Thread wget = new Thread(new Wget(url, speed));
            wget.start();
            wget.join();
        } else {
            System.out.println("Указан неверный URL-адрес.");
        }
    }
}
