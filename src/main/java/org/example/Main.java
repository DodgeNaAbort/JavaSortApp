package org.example;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CommandLine cmd = parseCommandLine(args);
        if (cmd == null) {
            System.err.println("Ошибка: Не удалось совершить парсинг аргументов");
            return;
        }

        FileProcessor fileProcessor = new FileProcessor();
        StatisticsPrinter statisticsPrinter = new StatisticsPrinter();

        try {
            List<String> fileNames = fileProcessor.getTxTFileNames(args);
            if (fileNames.isEmpty()) {
                System.err.println("Ошибка: Не указаны файлы для обработки.");
                return;
            }

            List<Path> filePaths = fileProcessor.getFilePaths(fileNames);
            List<String> linesFromFiles = fileProcessor.readLinesFromFiles(filePaths);

            if (linesFromFiles.isEmpty()) {
                System.err.println("Ошибка: Файлы пусты или не содержат данных.");
                return;
            }

            DataFilter dataFilter = new DataFilter();
            List<Integer> integerLines = dataFilter.filterLinesByType(linesFromFiles, Integer.class);
            List<Float> floatLines = dataFilter.filterLinesByType(linesFromFiles, Float.class);
            List<String> stringLines = dataFilter.filterLinesByType(linesFromFiles, String.class);

            String outputPath = cmd.getOptionValue("o", "");
            String filesPrefix = cmd.getOptionValue("p", "");
            boolean appendMode = cmd.hasOption("a");

            fileProcessor.writeDataToFiles(outputPath, filesPrefix, integerLines, floatLines, stringLines, appendMode);

            if (cmd.hasOption("s")) {
                statisticsPrinter.printShortStatistics(stringLines, integerLines, floatLines, outputPath, filesPrefix);
            } else if (cmd.hasOption("f")) {
                statisticsPrinter.printFullStatistics(stringLines, integerLines, floatLines, outputPath, filesPrefix);
            }
        } catch (IOException e) {
            System.err.println("Ошибка обработки файлов: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        }
    }

    private static CommandLine parseCommandLine(String[] args) {
        Options options = new Options();
        options.addOption("o", "output", true, "Path to output directory");
        options.addOption("p", "prefix", true, "Prefix to names of the files");
        options.addOption("a", "append", false, "Append strings to the files");
        options.addOption("s", "short", false, "Short statistics");
        options.addOption("f", "full", false, "Full statistics");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("s") && cmd.hasOption("f")) {
                System.err.println("Флаг -s и флаг -f не могут использоваться одновременно.");
                System.exit(1);
            }
            return cmd;
        } catch (ParseException e) {
            System.err.println("Ошибка в написании флагов. Причина: " + e.getMessage());
            return null;
        }
    }
}