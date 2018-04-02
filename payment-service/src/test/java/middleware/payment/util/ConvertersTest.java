package middleware.payment.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author mort
 */
class ConvertersTest {
    @Test
    void toLocalDateTime() {
        LocalDateTime time = Converters.toLocalDateTime("2015-12-27 15:45:58");
        assertNotNull(time);
        LocalDateTime time1 = Converters.toLocalDateTime("2015-04-28 15:45:57.320", Converters.TIME_WITH_NANO_FORMATTER);
        assertNotNull(time1);
        LocalDateTime time2 = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        assertNotNull(time2);
    }
}