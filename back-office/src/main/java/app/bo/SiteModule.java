package app.bo;

import app.bo.web.EmptyPageModel;
import app.bo.web.errorhandler.BOErrorHandler;
import app.bo.web.interceptor.RolePermissionInterceptor;
import core.framework.module.Module;

/**
 * @author mort
 */
public class SiteModule extends Module {
    @Override
    protected void initialize() {
        site().session().cookie("BOSessionId", null);
        site().staticContent("/");
        site().staticContent("/views");
        site().staticContent("/static");

        site().template("/index.html", EmptyPageModel.class);
        site().template("/views/500.html", BOErrorHandler.ErrorPageModel.class);
        site().template("/views/404.html", BOErrorHandler.ErrorPageModel.class);

//        http().intercept(bind(LoginRequiredInterceptor.class));
        http().intercept(bind(RolePermissionInterceptor.class));

        http().errorHandler(bind(BOErrorHandler.class));
    }
}
