package app.payment.util;

import core.framework.util.Strings;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 * @author mort
 */
public class Converters {
    public static final DateTimeFormatter DEFAULT_TIME_FORMATTER = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter();
    public static final DateTimeFormatter TIME_WITH_NANO_FORMATTER = new DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd HH:mm:ss").appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
        .toFormatter();
    public static final DateTimeFormatter WEI_TIME_FORMAT = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").toFormatter();

    public static Double toDouble(String value) {
        return Strings.isEmpty(value) ? null : Double.valueOf(value);
    }

    public static Double toDouble(String value, Double defaultValue) {
        Double d = toDouble(value);
        return d == null ? defaultValue : d;
    }


    public static Integer toInteger(String value) {
        return Strings.isEmpty(value) ? null : Integer.valueOf(value);
    }

    public static Double divide(Integer value, int divisor, int scale) {
        if (value == null) return null;
        return BigDecimal.valueOf(value)
            .divide(BigDecimal.valueOf(divisor))
            .setScale(scale, BigDecimal.ROUND_DOWN)
            .doubleValue();
    }

    public static LocalDateTime toLocalDateTime(String value) {
        return toLocalDateTime(value, DEFAULT_TIME_FORMATTER);
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(String value, DateTimeFormatter formatter) {
        if (Strings.isEmpty(value)) return null;
        if (formatter == null) {
            return LocalDateTime.parse(value, DEFAULT_TIME_FORMATTER);
        }
        return LocalDateTime.parse(value, formatter);
    }
}
