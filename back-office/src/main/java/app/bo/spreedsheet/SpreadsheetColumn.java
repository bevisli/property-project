package app.bo.spreedsheet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author caine
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpreadsheetColumn {
    String name();

    SpreadsheetDataType dataType() default SpreadsheetDataType.STRING;


}
