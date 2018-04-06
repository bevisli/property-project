package app.bo;

import app.bo.service.role.service.RoleService;
import app.bo.service.user.service.UserService;
import app.bo.web.controller.role.RoleController;
import app.bo.web.controller.user.UserController;
import core.framework.module.Module;

/**
 * @author mort
 */
public class AdminModule extends Module {
    @Override
    protected void initialize() {
        bind(RoleService.class);
        bind(UserService.class);

        UserController userController = bind(UserController.class);
        route().post("/user", userController::create);
        route().put("/user", userController::search);
        route().get("/user/:id", userController::get);
        route().put("/user/:id", userController::update);
        route().put("/user/:id/status", userController::updateStatus);
        route().put("/user/:id/role", userController::updateRole);
        route().put("/reset-password", userController::resetPassword);

        RoleController roleController = bind(RoleController.class);
        route().post("/role", roleController::create);
        route().put("/role", roleController::search);
        route().get("/role/:code", roleController::get);
        route().put("/role/:code", roleController::update);
    }
}
