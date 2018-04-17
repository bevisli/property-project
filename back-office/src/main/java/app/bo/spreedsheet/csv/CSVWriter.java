package app.bo.spreedsheet.csv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author caine
 */
public class CSVWriter {
    public <T> void write(List<T> objects, String fileName) {
        List<String> lines = objects.stream().map(CSV::toCSV).collect(Collectors.toList());
        writeAll(lines, fileName);
    }

    private void writeAll(List<String> lines, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            lines.forEach(line -> {
                try {
                    writer.write(line);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
