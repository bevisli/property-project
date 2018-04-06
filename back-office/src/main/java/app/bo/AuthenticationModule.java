package app.bo;

import app.bo.service.auth.service.AuthorizationService;
import app.bo.service.role.domain.Role;
import app.bo.service.user.domain.User;
import app.bo.web.controller.home.HomeController;
import app.bo.web.controller.login.LoginController;
import core.framework.module.Module;
import core.framework.web.Response;

/**
 * @author mort
 */
public class AuthenticationModule extends Module {
    @Override
    protected void initialize() {
        db().repository(Role.class);
        db().repository(User.class);
        bind(AuthorizationService.class);

        LoginController loginController = bind(LoginController.class);
        route().get("/", request -> Response.redirect("/home"));
        route().get("/login", loginController::login);
        route().post("/login", loginController::submit);
        route().get("/logout", loginController::logout);

        HomeController homeController = new HomeController();
        route().get("/home", homeController::home);
    }
}
