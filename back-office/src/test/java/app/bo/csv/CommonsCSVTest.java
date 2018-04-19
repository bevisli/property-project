package app.bo.csv;

import app.bo.spreedsheet.csv.Record;
import core.framework.json.JSON;
import core.framework.util.ClasspathResources;
import core.framework.util.Files;
import core.framework.util.Types;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author caine
 */
public class CommonsCSVTest {
    @Test
    public void read() throws IOException {
        Path tempFile = Files.tempFile();
        java.nio.file.Files.write(tempFile, ClasspathResources.bytes("spreadsheet/record.csv"));
        FileReader reader = new FileReader(tempFile.toFile());

        CSVParser parser1 = CSVFormat.EXCEL.parse(reader);
        for (CSVRecord record : parser1.getRecords()) {
            String value = record.get(0);
        }

        CSVParser parser2 = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(reader);
        for (CSVRecord record : parser2.getRecords()) {
            String value = record.get("string value");
        }

        parser1.close();
    }

    @Test
    public void write() throws IOException {
        List<Record> records = JSON.fromJSON(Types.list(Record.class), ClasspathResources.text("spreadsheet/record.json"));
        Path tempFile = Files.tempFile();
        FileWriter writer = new FileWriter(tempFile.toFile());
        CSVPrinter printer = CSVFormat.EXCEL.withHeader("boolean value", "integer value").print(writer);
        for (Record record : records) {
            printer.printRecord(record.booleanValue, record.integerValue);
        }
        printer.close();
        System.out.println(tempFile.toFile().getAbsolutePath());
    }
}
