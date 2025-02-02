package org.example;

import java.util.List;

public class Statistics {
    public <T extends Number> List<Double> extendedStatistics(List<T> inputList) {
        if (inputList == null || inputList.isEmpty()) {
            return List.of(0.0, Double.NaN, Double.NaN, 0.0, Double.NaN);
        }

        return List.of(
                (double) inputList.size(),
                inputList.stream().mapToDouble(Number::doubleValue).min().orElse(Double.NaN),
                inputList.stream().mapToDouble(Number::doubleValue).max().orElse(Double.NaN),
                inputList.stream().mapToDouble(Number::doubleValue).sum(),
                inputList.stream().mapToDouble(Number::doubleValue).average().orElse(Double.NaN)
        );
    }

    public List<Integer> extendedStatisticsString(List<String> inputList) {
        if (inputList == null || inputList.isEmpty()) {
            return List.of(0, 0, 0);
        }

        return List.of(
                inputList.size(),
                inputList.stream().map(String::length).min(Integer::compare).orElse(0),
                inputList.stream().map(String::length).max(Integer::compare).orElse(0)
        );
    }
}