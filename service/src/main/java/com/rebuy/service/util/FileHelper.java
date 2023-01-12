package com.rebuy.service.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHelper {

    private FileHelper() {
        throw new IllegalStateException("Utility class");
    }


    public static void writeToFile(String data, String filename) {
        Path path = Paths.get("src/main/resources/data/" + filename + ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(data);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String readFile(String fileName) {
        Path path = Paths.get("src/main/resources/data/" + fileName);
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data.toString();
    }

}
