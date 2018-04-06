package app.bo.web.controller;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author caine
 */
public class LocalFormat {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static <T extends Number> String formatToDecimal(T amount) {
        if (amount == null) return null;
        return DECIMAL_FORMAT.format(amount);
    }

    public static String formatToLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    public static String formatToLocalDate(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String formatToLocalDate(LocalDate localDate) {
        if (localDate == null) return null;
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String formatToLocalTime(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static String formatToLocalTime(LocalTime localTime) {
        if (localTime == null) return null;
        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
