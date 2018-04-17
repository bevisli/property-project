package app.bo.spreedsheet.csv;

import core.framework.util.Lists;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author caine
 */
public class CSVReader {
    public <T> List<T> read(Class<T> instanceType, String fileName) {
        List<String> lines = readAll(fileName);
        return lines.stream().map(line -> CSV.fromCSV(instanceType, line)).collect(Collectors.toList());
    }

    public List<String> readAll(String fileName) {
        List<String> lines = Lists.newArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            do {
                line = reader.readLine();
                if (line != null) lines.add(line);
            } while (line != null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}
