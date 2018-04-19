package app.bo.spreedsheet.csv;

import app.bo.spreedsheet.SpreadsheetTable;
import core.framework.util.Lists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author caine
 */
public class CSVReader {
    public <T> List<T> read(Class<T> objectType, String fileName) {
        return read(objectType, new File(fileName));
    }

    public <T> List<T> read(Class<T> objectType, File file) {
        SpreadsheetTable tableTag = objectType.getAnnotation(SpreadsheetTable.class);
        if (tableTag == null) {
            throw new RuntimeException("missing @SpreadsheetTable annotation");
        }
        List<String> lines = readAll(file);
        if (tableTag.firstRowIsHeader()) {
            lines.remove(0);
        }
        return lines.stream().map(line -> CSV.fromCSV(objectType, line)).collect(Collectors.toList());
    }

    public List<String> readAll(File file) {
        List<String> lines = Lists.newArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
