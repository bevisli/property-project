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
    public static <T> T fromCSV(Class<T> instanceType, String record) {
        T instance = instance(instanceType);
        String[] values = record.split(",");
        Field[] fields = instanceType.getFields();
        for (int i = 0; i < fields.length; i++) {
            SpreadsheetColumn columnTag = fields[i].getAnnotation(SpreadsheetColumn.class);
            if (columnTag != null) {
                if (i < values.length && !Strings.isEmpty(values[i])) {
                    String csvValue = trim(values[i], '\"');
                    Object value = parse(fields[i], csvValue, columnTag);
                    set(fields[i], value, instance);
                }
            }
        }
        return instance;
    }

    private static <T> T instance(Class<T> instanceType) {
        try {
            return instanceType.getDeclaredConstructor().newInstance();
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
        } else if (columnTag.dataType() == SpreadsheetDataType.INTERGE) {
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

    private static <T> void set(Field field, Object value, T instance) {
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String toCSV(T instance) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = instance.getClass().getFields();
        for (Field field : fields) {
            SpreadsheetColumn columnTag = field.getAnnotation(SpreadsheetColumn.class);
            if (columnTag != null) {
                Object value = getValue(field, instance);
                if (value != null) {
                    String csv = format(columnTag, field, value);
                    sb.append('"').append(csv).append("\",");
                } else {
                    sb.append("\"\",");
                }
            }
        }
        return sb.toString();
    }

    private static <T> Object getValue(Field field, T instance) {
        try {
            return field.get(instance);
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
        if (columnTag.dataType() == SpreadsheetDataType.INTERGE || columnTag.dataType() == SpreadsheetDataType.DOUBLE) {
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
