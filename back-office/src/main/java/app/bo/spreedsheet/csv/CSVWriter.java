package app.bo.spreedsheet.csv;

import app.bo.spreedsheet.SpreadsheetColumn;
import app.bo.spreedsheet.SpreadsheetTable;
import core.framework.util.Lists;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author caine
 */
public class CSVWriter {
    public <T> void write(List<T> instances, String fileName) {
        Class<?> instanceType = instances.get(0).getClass();
        SpreadsheetTable tableTag = instanceType.getAnnotation(SpreadsheetTable.class);
        if (tableTag == null) {
            throw new RuntimeException("missing @SpreadsheetTable annotation");
        }
        List<String> lines = Lists.newArrayList();
        if (tableTag.firstRowIsHeader()) {
            lines.add(header(instanceType));
        }
        lines.addAll(instances.stream().map(CSV::toCSV).collect(Collectors.toList()));
        writeAll(lines, fileName);
    }

    private String header(Class<?> instanceType) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = instanceType.getFields();
        for (Field field : fields) {
            SpreadsheetColumn columnTag = field.getAnnotation(SpreadsheetColumn.class);
            if (columnTag != null) {
                sb.append('\"').append(columnTag.name()).append("\",");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    private void writeAll(List<String> lines, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
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
