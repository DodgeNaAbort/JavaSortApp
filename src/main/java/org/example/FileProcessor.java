package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileProcessor {
    public List<String> getTxTFileNames(String[] args) {
        if (args == null || args.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.stream(args)
                .filter(file -> file.endsWith(".txt"))
                .collect(Collectors.toList());
    }

    public List<Path> getFilePaths(List<String> fileNames) {
        String projectDir = System.getProperty("user.dir");
        return fileNames.stream()
                .map(fileName -> Paths.get(projectDir, fileName))
                .collect(Collectors.toList());
    }

    public List<String> readLinesFromFiles(List<Path> paths) throws IOException {
        List<String> allLines = new ArrayList<>();
        for (Path path : paths) {
            if (!Files.exists(path)) {
                System.err.println("Ошибка: Файл не найден - " + path);
                continue;
            }
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    allLines.add(line);
                }
            } catch (IOException e) {
                System.err.println("Ошибка чтения файла: " + path + ": " + e.getMessage());
            }
        }
        return allLines;
    }

    public void writeDataToFiles(String outputPath, String filesPrefix,
                                 List<Integer> integerLines, List<Float> floatLines,
                                 List<String> stringLines, boolean appendMode) throws IOException {
        if (outputPath == null || filesPrefix == null) {
            throw new IllegalArgumentException("Путь и префикс не могут быть null.");
        }

        Path outputDir = Paths.get(outputPath);
        if (!Files.exists(outputDir)) {
            try {
                Files.createDirectories(outputDir);
            } catch (IOException e) {
                throw new IOException("Ошибка создания директории: " + outputDir, e);
            }
        }

        writeToFile(outputDir.resolve(filesPrefix + "integers.txt").toString(), integerLines, appendMode);
        writeToFile(outputDir.resolve(filesPrefix + "floats.txt").toString(), floatLines, appendMode);
        writeToFile(outputDir.resolve(filesPrefix + "strings.txt").toString(), stringLines, appendMode);
    }

    private <T> void writeToFile(String filePath, List<T> lines, boolean appendMode) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, appendMode))) {
            for (T line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            throw new IOException("Ошибка записи в файл: " + filePath, e);
        }
    }
}