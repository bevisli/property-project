package app.bo.web.controller.home;

import app.bo.web.EmptyPageModel;
import app.bo.web.interceptor.LoginRequired;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author mort
 */
@LoginRequired
public class HomeController {
    public Response home(Request request) {
//        LoginUser loginUser = Sessions.currentUser(request);

        return Response.html("/template/index.html", new EmptyPageModel());
    }
}
