package app.bo.spreedsheet.csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author caine
 */
public class CSVTest {
    @Test
    public void toCSV() {
        Record record = new Record();
        record.booleanValue = Boolean.TRUE;
        record.booleanFormatValue = Boolean.TRUE;
        record.integerValue = 5555;
        record.integerFormatValue = 6666;
        record.doubleValue = 7777.777d;
        record.doubleFormatValue = 8888.888d;
        record.stringValue = "caine";
        record.localDateTimeValue = LocalDateTime.of(2018, 4, 17, 16, 1, 20);
        record.localDateTimeFormatValue = LocalDateTime.of(2018, 4, 17, 16, 1, 20);
        record.hiddenValue = "hidden";
        String csv = CSV.toCSV(record);
        Assertions.assertEquals("\"true\",\"Yes\",\"5555\",\"6,666\",\"7777.777\",\"8,888.9\",\"caine\",\"2018-04-17T16:01:20\",\"2018-04-17 16:01\",", csv);
    }

    @Test
    public void fromCSV() {
        String csv = "true,Yes,5555,\"6666\",7777.777,\"8888.9\",caine,2018-04-17T16:01:20,2018-04-17 16:01,";
        Record record = CSV.fromCSV(Record.class, csv);
        Assertions.assertTrue(record.booleanValue);
        Assertions.assertTrue(record.booleanFormatValue);
        Assertions.assertEquals(5555, record.integerValue, 1);
        Assertions.assertEquals(6666, record.integerFormatValue, 1);
        Assertions.assertEquals(7777.777, record.doubleValue, 0.000001);
        Assertions.assertEquals(8888.9, record.doubleFormatValue, 0.000001);
        Assertions.assertEquals("caine", record.stringValue);
        LocalDateTime dateTime1 = LocalDateTime.of(2018, 4, 17, 16, 1, 20);
        Assertions.assertEquals(dateTime1, record.localDateTimeValue);
        LocalDateTime dateTime2 = LocalDateTime.of(2018, 4, 17, 16, 1);
        Assertions.assertEquals(dateTime2, record.localDateTimeFormatValue);
        Assertions.assertNull(record.hiddenValue);
    }


}
