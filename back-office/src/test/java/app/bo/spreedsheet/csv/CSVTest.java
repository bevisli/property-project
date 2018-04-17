package app.bo.spreedsheet.csv;

import core.framework.json.JSON;
import core.framework.util.ClasspathResources;
import core.framework.util.Types;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author caine
 */
public class CSVTest {
    @Test
    public void toCSV() {
        List<Record> records = JSON.fromJSON(Types.list(Record.class), ClasspathResources.text("spreadsheet/records.json"));
        Assertions.assertEquals("\"true\",\"Yes\",\"5555\",\"6,666\",\"7777.777\",\"8,888.9\",\"caine\",\"\",\"\",\"2018-04-17T16:01:20\",\"2018-04-17 16:01\",\"\",", CSV.toCSV(records.get(0)));
    }

    @Test
    public void fromCSV() {
        String csv = "true,Yes,5555,6666,7777.777,8888.888,caine,,,2018-04-17T16:01:20,2018-04-17 16:01,,";
        Record record = CSV.fromCSV(Record.class, csv);
        Assertions.assertTrue(record.booleanValue);
        Assertions.assertTrue(record.booleanFormatValue);
        Assertions.assertEquals(5555, record.integerValue, 1);
        Assertions.assertEquals(6666, record.integerFormatValue, 1);
        Assertions.assertEquals(7777.777, record.doubleValue, 0.000001);
        Assertions.assertEquals(8888.888, record.doubleFormatValue, 0.000001);
        Assertions.assertEquals("caine", record.stringValue);
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
