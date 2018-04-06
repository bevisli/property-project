package app.bo.web.interceptor;

import app.bo.web.Sessions;
import core.framework.api.http.HTTPStatus;
import core.framework.http.ContentType;
import core.framework.http.HTTPHeaders;
import core.framework.web.Interceptor;
import core.framework.web.Invocation;
import core.framework.web.Response;

/**
 * @author mort
 */
public class LoginRequiredInterceptor implements Interceptor {
    private static final String REDIRECT_URL = "/login";

    @Override
    public Response intercept(Invocation invocation) throws Exception {
        if (invocation.annotation(LoginRequired.class) != null && !Sessions.isUserLogin(invocation.context().request())) {
            if (invocation.context().request().header(HTTPHeaders.ACCEPT).isPresent()) {
                return Response.text(REDIRECT_URL).status(HTTPStatus.UNAUTHORIZED).contentType(ContentType.TEXT_PLAIN);
            }
            return Response.redirect(REDIRECT_URL);
        }
        return invocation.proceed();
    }
}
