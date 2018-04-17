package app.bo.spreedsheet.csv;

import app.bo.spreedsheet.SpreadsheetBoolean;
import app.bo.spreedsheet.SpreadsheetColumn;
import app.bo.spreedsheet.SpreadsheetDataType;
import app.bo.spreedsheet.SpreadsheetDateTime;
import app.bo.spreedsheet.SpreadsheetNumber;

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
    public static <T> T fromCSV(Class<T> instanceType, String row) {
        try {
            T instance = instanceType.getDeclaredConstructor().newInstance();
            Field[] fields = instanceType.getFields();
            String[] values = row.split(",");
            for (int i = 0; i < fields.length; i++) {
                SpreadsheetColumn columnTag = fields[i].getAnnotation(SpreadsheetColumn.class);
                if (columnTag == null) {
                    continue;
                }
                Object value = fieldValue(columnTag, fields[i], values[i]);
                fields[i].set(instance, value);
            }
            return instance;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> Object fieldValue(SpreadsheetColumn columnTag, Field field, String value) {
        String fieldValue = trim(value, '\"');
        if (columnTag.dataType() == SpreadsheetDataType.BOOLEAN) {
            SpreadsheetBoolean booleanTag = field.getAnnotation(SpreadsheetBoolean.class);
            if (booleanTag != null) {
                return booleanTag.trueValue().equals(fieldValue);
            } else {
                return Boolean.valueOf(fieldValue);
            }
        }
        if (columnTag.dataType() == SpreadsheetDataType.INTERGE) {
            return Integer.valueOf(fieldValue);
        }
        if (columnTag.dataType() == SpreadsheetDataType.DOUBLE) {
            return Double.valueOf(fieldValue);
        }
        if (columnTag.dataType() == SpreadsheetDataType.DATE_TIME) {
            SpreadsheetDateTime dateTimeTag = field.getAnnotation(SpreadsheetDateTime.class);
            if (dateTimeTag != null) {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(dateTimeTag.format()).toFormatter();
                return LocalDateTime.parse(fieldValue, formatter);
            } else {
                return LocalDateTime.parse(fieldValue);
            }
        }
        return fieldValue;
    }

    static String trim(String value, char character) {
        String newValue = value;
        if (value.charAt(0) == character) {
            newValue = value.substring(1, value.length());
        }
        if (value.charAt(value.length() - 1) == character) {
            newValue = newValue.substring(0, newValue.length() - 1);
        }
        return newValue;
    }

    public static <T> String toCSV(T instance) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = instance.getClass().getFields();

        for (Field field : fields) {
            SpreadsheetColumn columnTag = field.getAnnotation(SpreadsheetColumn.class);
            if (columnTag == null) {
                continue;
            }
            String csv = fieldCSV(columnTag, field, instance);
            sb.append('"').append(csv).append("\",");
        }

        return sb.toString();
    }

    private static <T> String fieldCSV(SpreadsheetColumn columnTag, Field field, T instance) {
        try {
            if (columnTag.dataType() == SpreadsheetDataType.BOOLEAN) {
                SpreadsheetBoolean boolTag = field.getAnnotation(SpreadsheetBoolean.class);
                if (boolTag != null) {
                    Boolean value = (Boolean) field.get(instance);
                    return Boolean.TRUE.equals(value) ? boolTag.trueValue() : boolTag.falseValue();
                }
            }
            if (columnTag.dataType() == SpreadsheetDataType.INTERGE || columnTag.dataType() == SpreadsheetDataType.DOUBLE) {
                SpreadsheetNumber numberTag = field.getAnnotation(SpreadsheetNumber.class);
                if (numberTag != null) {
                    BigDecimal value = new BigDecimal(field.get(instance).toString());
                    return new DecimalFormat(numberTag.format()).format(value);
                }
            }
            if (columnTag.dataType() == SpreadsheetDataType.DATE_TIME) {
                SpreadsheetDateTime dateTimeTag = field.getAnnotation(SpreadsheetDateTime.class);
                if (dateTimeTag != null) {
                    LocalDateTime value = (LocalDateTime) field.get(instance);
                    return value.format(new DateTimeFormatterBuilder().appendPattern(dateTimeTag.format()).toFormatter());
                }
            }
            return field.get(instance).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
