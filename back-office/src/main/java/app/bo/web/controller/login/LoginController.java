package app.bo.web.controller.login;

import app.bo.service.auth.service.AuthorizationRequest;
import app.bo.service.auth.service.AuthorizationResponse;
import app.bo.service.auth.service.AuthorizationService;
import app.bo.web.EmptyPageModel;
import app.bo.web.Sessions;
import app.bo.web.session.LoginUser;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author mort
 */
public class LoginController {
    @Inject
    AuthorizationService authorizationService;

    public Response login(Request request) {
        return Response.html("/template/views/pages/login.html", new EmptyPageModel());
    }

    public Response submit(Request request) {
        AuthorizationRequest authorizeRequest = request.bean(AuthorizationRequest.class);
        AuthorizationResponse authorizeResponse = authorizationService.authorize(authorizeRequest);
        if (authorizeResponse.user != null) {
            LoginUser loginUser = new LoginUser();
            loginUser.id = authorizeResponse.user.id;
            loginUser.phoneNumber = authorizeResponse.user.phoneNumber;
            loginUser.userName = authorizeResponse.user.userName;
            loginUser.roleCodes = authorizeResponse.user.roleCodes;
            Sessions.login(request, loginUser);
        }
        return Response.empty();
    }

    public Response logout(Request request) {
        Sessions.logout(request);
        return Response.redirect("/login");
    }
}