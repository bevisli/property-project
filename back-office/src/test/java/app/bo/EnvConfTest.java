package app.bo;

import core.framework.test.EnvResourceValidator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author neo
 */
public class EnvConfTest {
    @Test
    public void validateEnvResource() throws IOException {
        new EnvResourceValidator().validate();
    }
}
