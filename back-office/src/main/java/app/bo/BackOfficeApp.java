package app.bo;

import core.framework.module.App;
import core.framework.module.SystemModule;

import java.time.Duration;

/**
 * @author caine
 */
public class BackOfficeApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");

        http().enableGZip();
        api().timeout(Duration.ofSeconds(60));

        load(new SiteModule());
        load(new AuthenticationModule());
        load(new AdminModule());
        load(new FileModule());
    }
}
