package app.bo.spreedsheet.csv;

import core.framework.util.ClasspathResources;
import core.framework.util.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author caine
 */
public class CSVReaderTest {
    public CSVReader reader = new CSVReader();

    @Test
    public void read() throws IOException {
        Path tempFile = Files.tempFile();
        java.nio.file.Files.write(tempFile, ClasspathResources.bytes("spreadsheet/record.csv"));
        List<Record> records = reader.read(Record.class, tempFile.toFile().getAbsolutePath());
        Assertions.assertEquals(2, records.size());
    }
}
