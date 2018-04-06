package app.bo.web.interceptor;

import app.bo.web.Sessions;
import core.framework.web.Interceptor;
import core.framework.web.Invocation;
import core.framework.web.Response;
import core.framework.web.exception.UnauthorizedException;

/**
 * @author mort
 */
public class RolePermissionInterceptor implements Interceptor {
    @Override
    public Response intercept(Invocation invocation) throws Exception {
        if (invocation.annotation(RolePermission.class) != null && Sessions.isRolePermission(invocation.context().request(), invocation.annotation(RolePermission.class).value())) {
            throw new UnauthorizedException("permission denied");
        }
        return invocation.proceed();
    }
}
