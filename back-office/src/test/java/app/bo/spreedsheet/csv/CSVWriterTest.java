package app.bo.spreedsheet.csv;

import core.framework.util.Lists;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author caine
 */
public class CSVWriterTest {
    private CSVWriter writer = new CSVWriter();

    @Test
    public void write() {
        List<Record> records = Lists.newArrayList();
        records.add(record());
        writer.write(records, "D:\\record.csv");
    }

    private Record record() {
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
        return record;
    }
}
