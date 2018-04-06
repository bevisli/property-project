package app.bo.web;

import app.bo.web.session.LoginUser;
import core.framework.json.JSON;
import core.framework.util.Lists;
import core.framework.web.Request;
import core.framework.web.exception.UnauthorizedException;

/**
 * @author mort
 */
public final class Sessions {
    private static final String USER = "BO_USER";

    public static LoginUser currentUser(Request request) {
        String loginUser = request.session().get(USER).orElseThrow(() -> new UnauthorizedException("user not authorized"));
        return JSON.fromJSON(LoginUser.class, loginUser);
    }

    public static void login(Request request, LoginUser user) {
        request.session().set(USER, JSON.toJSON(user));
    }

    public static void logout(Request request) {
        request.session().invalidate();
    }

    public static Boolean isRolePermission(Request request, String[] roleCodes) {
        if (roleCodes != null && roleCodes.length > 0) {
            return Lists.newArrayList(roleCodes).stream().anyMatch(currentUser(request).roleCodes::contains);
        }
        return Boolean.TRUE;
    }

    public static Boolean isUserLogin(Request request) {
        return request.session().get(USER).isPresent();
    }
}
