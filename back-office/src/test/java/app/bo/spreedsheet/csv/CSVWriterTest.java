package app.bo.spreedsheet.csv;

import core.framework.json.JSON;
import core.framework.util.ClasspathResources;
import core.framework.util.Types;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author caine
 */
public class CSVWriterTest {
    private CSVWriter writer = new CSVWriter();

    @Test
    public void write() {
        List<Record> records = JSON.fromJSON(Types.list(Record.class), ClasspathResources.text("spreadsheet/record.json"));
        writer.write(records, "D:\\record.csv");
    }
}
