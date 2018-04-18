package app.bo.spreedsheet.csv;

import app.bo.spreedsheet.SpreadsheetBoolean;
import app.bo.spreedsheet.SpreadsheetColumn;
import app.bo.spreedsheet.SpreadsheetDataType;
import app.bo.spreedsheet.SpreadsheetDateTime;
import app.bo.spreedsheet.SpreadsheetNumber;
import app.bo.spreedsheet.SpreadsheetTable;

import java.time.LocalDateTime;

/**
 * @author caine
 */
@SpreadsheetTable(firstRowIsHeader = true)
public class Record {
    @SpreadsheetColumn(name = "boolean value", dataType = SpreadsheetDataType.BOOLEAN)
    public Boolean booleanValue;

    @SpreadsheetBoolean(trueValue = "YES", falseValue = "NO")
    @SpreadsheetColumn(name = "boolean format value", dataType = SpreadsheetDataType.BOOLEAN)
    public Boolean booleanFormatValue;

    @SpreadsheetColumn(name = "integer value", dataType = SpreadsheetDataType.INTERGE)
    public Integer integerValue;

    @SpreadsheetNumber(format = "#,###")
    @SpreadsheetColumn(name = "integer format value", dataType = SpreadsheetDataType.INTERGE)
    public Integer integerFormatValue;

    @SpreadsheetColumn(name = "double value", dataType = SpreadsheetDataType.DOUBLE)
    public Double doubleValue;

    @SpreadsheetNumber(format = "#,##0.0")
    @SpreadsheetColumn(name = "double format value", dataType = SpreadsheetDataType.DOUBLE)
    public Double doubleFormatValue;

    @SpreadsheetColumn(name = "string value")
    public String stringValue;

    @SpreadsheetColumn(name = "empty string")
    public String emptyString;

    @SpreadsheetColumn(name = "null in middle")
    public LocalDateTime nullInMiddle;

    @SpreadsheetColumn(name = "local date time value", dataType = SpreadsheetDataType.DATE_TIME)
    public LocalDateTime localDateTimeValue;

    @SpreadsheetDateTime(format = "yyyy-MM-dd HH:mm")
    @SpreadsheetColumn(name = "local date time format value", dataType = SpreadsheetDataType.DATE_TIME)
    public LocalDateTime localDateTimeFormatValue;

    @SpreadsheetColumn(name = "null in end")
    public LocalDateTime nullInEnd;

    public String hiddenValue;
}
