package app.bo.spreedsheet.csv;

import app.bo.spreedsheet.SpreadsheetColumn;
import app.bo.spreedsheet.SpreadsheetTable;
import core.framework.util.Lists;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author caine
 */
public class CSVWriter {
    public <T> void write(List<T> objects, String fileName) {
        write(objects, new File(fileName));
    }

    public <T> void write(List<T> objects, File file) {
        Class<?> objectType = objects.get(0).getClass();
        SpreadsheetTable tableTag = objectType.getAnnotation(SpreadsheetTable.class);
        if (tableTag == null) {
            throw new RuntimeException("missing @SpreadsheetTable annotation");
        }
        List<String> lines = Lists.newArrayList();
        if (tableTag.firstRowIsHeader()) {
            lines.add(header(objectType));
        }
        lines.addAll(objects.stream().map(CSV::toCSV).collect(Collectors.toList()));
        writeAll(lines, file);
    }

    private String header(Class<?> objectType) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = objectType.getFields();
        for (Field field : fields) {
            SpreadsheetColumn columnTag = field.getAnnotation(SpreadsheetColumn.class);
            if (columnTag != null) {
                sb.append('\"').append(columnTag.name()).append("\",");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    private void writeAll(List<String> lines, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            lines.forEach(line -> writeLine(line, writer));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeLine(String line, BufferedWriter writer) {
        try {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
