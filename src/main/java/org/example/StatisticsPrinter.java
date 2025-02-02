package org.example;

import java.util.List;

public class StatisticsPrinter {
    public void printShortStatistics(List<String> stringLines, List<Integer> integerLines,
                                     List<Float> floatLines, String outputPath, String filesPrefix) {
        String finalPathOut = outputPath + filesPrefix;
        System.out.println("Количество строк записаных в " + finalPathOut + "strings.txt: " + stringLines.size());
        System.out.println("Количество строк записаных в " + finalPathOut + "integers.txt: " + integerLines.size());
        System.out.println("Количество строк записаных в " + finalPathOut + "floats.txt: " + floatLines.size());
    }

    public void printFullStatistics(List<String> stringLines, List<Integer> integerLines,
                                    List<Float> floatLines, String outputPath, String filesPrefix) {
        String finalPathOut = outputPath + filesPrefix;
        Statistics statistics = new Statistics();

        List<Double> integerStats = statistics.extendedStatistics(integerLines);
        List<Double> floatStats = statistics.extendedStatistics(floatLines);
        List<Integer> stringStats = statistics.extendedStatisticsString(stringLines);

        System.out.println("Количество строк записаных в " + finalPathOut + "strings.txt: " + stringStats.get(0));
        System.out.println("Длина самой короткой строки: " + stringStats.get(1));
        System.out.println("Длина самой длинной строки: " + stringStats.get(2) + "\n");

        printExtendedStatistics(integerStats, finalPathOut + "integers.txt");
        printExtendedStatistics(floatStats, finalPathOut + "floats.txt");
    }

    private void printExtendedStatistics(List<Double> statistics, String filePath) {
        System.out.println("Количество строк записаных в " + filePath + ": " + statistics.get(0));
        System.out.println("Минимальный записанный элемент: " + statistics.get(1));
        System.out.println("Максимальный записанный элемент: " + statistics.get(2));
        System.out.println("Сумма всех записанных элементов: " + statistics.get(3));
        System.out.println("Среднее арифметическое: " + statistics.get(4) + "\n");
    }
}