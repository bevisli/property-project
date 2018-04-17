package app.bo.spreedsheet.csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author caine
 */
public class CSVReaderTest {
    public CSVReader reader = new CSVReader();

    @Test
    public void read() {
//        ClasspathResources.text()
        List<Record> records = reader.read(Record.class, "D:\\Project\\nan\\prototype-project\\back-office\\src\\test\\resources\\record.csv");
        Assertions.assertEquals(1, records.size());
    }
}
