package app.bo.spreedsheet.csv;

import app.bo.spreedsheet.SpreadsheetBoolean;
import app.bo.spreedsheet.SpreadsheetColumn;
import app.bo.spreedsheet.SpreadsheetDataType;
import app.bo.spreedsheet.SpreadsheetDateTime;
import app.bo.spreedsheet.SpreadsheetNumber;
import core.framework.util.Strings;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * @author caine
 */
public class CSV {
    public static <T> T fromCSV(Class<T> objectType, String csvRow) {
        T object = newObject(objectType);
        String[] values = csvRow.split(",");
        Field[] fields = objectType.getFields();
        for (int i = 0; i < fields.length; i++) {
            SpreadsheetColumn columnTag = fields[i].getAnnotation(SpreadsheetColumn.class);
            if (columnTag != null && i < values.length && !Strings.isEmpty(values[i])) {
                String csvValue = trim(values[i], '\"');
                if (csvValue.length() > 0) {
                    Object value = parse(fields[i], csvValue, columnTag);
                    set(fields[i], value, object);
                }
            }
        }
        return object;
    }

    private static <T> T newObject(Class<T> objectType) {
        try {
            return objectType.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private static String trim(String value, char character) {
        String newValue = value;
        if (value.charAt(0) == character) {
            newValue = value.substring(1, value.length());
        }
        if (newValue.charAt(newValue.length() - 1) == character) {
            newValue = newValue.substring(0, newValue.length() - 1);
        }
        return newValue;
    }

    private static Object parse(Field field, String csvValue, SpreadsheetColumn columnTag) {
        if (columnTag.dataType() == SpreadsheetDataType.BOOLEAN) {
            SpreadsheetBoolean booleanTag = field.getAnnotation(SpreadsheetBoolean.class);
            if (booleanTag != null) {
                return booleanTag.trueValue().equals(csvValue);
            } else {
                return Boolean.valueOf(csvValue);
            }
        } else if (columnTag.dataType() == SpreadsheetDataType.INTEGER) {
            return Integer.valueOf(csvValue);
        } else if (columnTag.dataType() == SpreadsheetDataType.DOUBLE) {
            return Double.valueOf(csvValue);
        } else if (columnTag.dataType() == SpreadsheetDataType.DATE_TIME) {
            SpreadsheetDateTime dateTimeTag = field.getAnnotation(SpreadsheetDateTime.class);
            if (dateTimeTag != null) {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(dateTimeTag.format()).toFormatter();
                return LocalDateTime.parse(csvValue, formatter);
            } else {
                return LocalDateTime.parse(csvValue);
            }
        } else {
            return csvValue;
        }
    }

    private static <T> void set(Field field, Object value, T object) {
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String toCSV(T object) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = object.getClass().getFields();
        for (Field field : fields) {
            SpreadsheetColumn columnTag = field.getAnnotation(SpreadsheetColumn.class);
            if (columnTag != null) {
                Object value = getValue(field, object);
                if (value != null) {
                    String csvValue = format(columnTag, field, value);
                    sb.append('"').append(csvValue).append("\",");
                } else {
                    sb.append("\"\",");
                }
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static <T> Object getValue(Field field, T object) {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> String format(SpreadsheetColumn columnTag, Field field, Object value) {
        if (columnTag.dataType() == SpreadsheetDataType.BOOLEAN) {
            SpreadsheetBoolean boolTag = field.getAnnotation(SpreadsheetBoolean.class);
            if (boolTag != null) {
                Boolean bool = (Boolean) value;
                return Boolean.TRUE.equals(bool) ? boolTag.trueValue() : boolTag.falseValue();
            }
        }
        if (columnTag.dataType() == SpreadsheetDataType.INTEGER || columnTag.dataType() == SpreadsheetDataType.DOUBLE) {
            SpreadsheetNumber numberTag = field.getAnnotation(SpreadsheetNumber.class);
            if (numberTag != null) {
                BigDecimal decimal = new BigDecimal(value.toString());
                return new DecimalFormat(numberTag.format()).format(decimal);
            }
        }
        if (columnTag.dataType() == SpreadsheetDataType.DATE_TIME) {
            SpreadsheetDateTime dateTimeTag = field.getAnnotation(SpreadsheetDateTime.class);
            if (dateTimeTag != null) {
                LocalDateTime dateTime = (LocalDateTime) value;
                DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(dateTimeTag.format()).toFormatter();
                return dateTime.format(formatter);
            }
        }
        return value.toString();
    }
}
