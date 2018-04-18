package app.bo.spreedsheet.csv;

import core.framework.json.JSON;
import core.framework.util.ClasspathResources;
import core.framework.util.Lists;
import core.framework.util.Types;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author caine
 */
public class CSVTest {
    @Test
    public void toCSV() {
        List<Record> records = JSON.fromJSON(Types.list(Record.class), ClasspathResources.text("spreadsheet/record.json"));
        Assertions.assertEquals("\"true\",\"YES\",\"555\",\"666\",\"777.777\",\"888.9\",\"normal\",\"\",\"\",\"2018-04-17T16:01:20\",\"2018-04-17 16:01\",\"\"", CSV.toCSV(records.get(0)));
        Assertions.assertEquals("\"false\",\"NO\",\"5555\",\"6,666\",\"7777.777\",\"8,888.9\",\"specific\",\"\",\"\",\"2018-04-17T16:01:20\",\"2018-04-17 16:01\",\"\"", CSV.toCSV(records.get(1)));
    }

    @Test
    public void fromCSV() throws IOException {
        String text = ClasspathResources.text("spreadsheet/record.csv");
        List<String> lines = Lists.newArrayList(text.split("\n"));
        assertNormal(lines.get(1));
        assertSpecific(lines.get(2));
    }

    private void assertNormal(String line) {
        Record record = CSV.fromCSV(Record.class, line);
        Assertions.assertTrue(record.booleanValue);
        Assertions.assertTrue(record.booleanFormatValue);
        Assertions.assertEquals(555, record.integerValue, 1);
        Assertions.assertEquals(666, record.integerFormatValue, 1);
        Assertions.assertEquals(777.777, record.doubleValue, 0.000001);
        Assertions.assertEquals(888.9, record.doubleFormatValue, 0.000001);
        Assertions.assertEquals("normal", record.stringValue);
        Assertions.assertNull(record.emptyString);
        Assertions.assertNull(record.nullInMiddle);
        LocalDateTime dateTime1 = LocalDateTime.of(2018, 4, 17, 16, 1, 20);
        Assertions.assertEquals(dateTime1, record.localDateTimeValue);
        LocalDateTime dateTime2 = LocalDateTime.of(2018, 4, 17, 16, 1);
        Assertions.assertEquals(dateTime2, record.localDateTimeFormatValue);
        Assertions.assertNull(record.nullInEnd);
        Assertions.assertNull(record.hiddenValue);
    }

    private void assertSpecific(String line) {
        Record record = CSV.fromCSV(Record.class, line);
        Assertions.assertFalse(record.booleanValue);
        Assertions.assertFalse(record.booleanFormatValue);
        Assertions.assertEquals(5555, record.integerValue, 1);
        Assertions.assertEquals(6666, record.integerFormatValue, 1);
        Assertions.assertEquals(7777.777, record.doubleValue, 0.000001);
        Assertions.assertEquals(8888.9, record.doubleFormatValue, 0.000001);
        Assertions.assertEquals("specific", record.stringValue);
        Assertions.assertNull(record.emptyString);
        Assertions.assertNull(record.nullInMiddle);
        LocalDateTime dateTime1 = LocalDateTime.of(2018, 4, 17, 16, 1, 20);
        Assertions.assertEquals(dateTime1, record.localDateTimeValue);
        LocalDateTime dateTime2 = LocalDateTime.of(2018, 4, 17, 16, 1);
        Assertions.assertEquals(dateTime2, record.localDateTimeFormatValue);
        Assertions.assertNull(record.nullInEnd);
        Assertions.assertNull(record.hiddenValue);
    }


}
