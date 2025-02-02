package org.example;

import java.util.List;
import java.util.stream.Collectors;


public class DataFilter {
    public <T> List<T> filterLinesByType(List<String> lines, Class<T> type) {
        return lines.stream()
                .map(this::parseLine)
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    private <T> T parseLine(String line) {
        try {
            return (T) Integer.valueOf(line);
        } catch (NumberFormatException e1) {
            try {
                return (T) Float.valueOf(line);
            } catch (NumberFormatException e2) {
                return (T) line;
            }
        }
    }
}