package app.bo.web.interceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @author mort
 */
@Target({METHOD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RolePermission {
    String[] value();
}
