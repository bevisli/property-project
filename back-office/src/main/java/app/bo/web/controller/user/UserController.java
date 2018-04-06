package app.bo.web.controller.user;

import app.bo.service.auth.service.AuthorizationService;
import app.bo.service.auth.service.ResetPasswordRequest;
import app.bo.service.role.service.RoleService;
import app.bo.service.role.service.RoleView;
import app.bo.service.role.service.SearchRoleRequest;
import app.bo.service.user.service.CreateUserRequest;
import app.bo.service.user.service.GetUserResponse;
import app.bo.service.user.service.SearchUserRequest;
import app.bo.service.user.service.SearchUserResponse;
import app.bo.service.user.service.UpdateUserRequest;
import app.bo.service.user.service.UpdateUserRoleRequest;
import app.bo.service.user.service.UserService;
import app.bo.web.Sessions;
import app.bo.web.interceptor.LoginRequired;
import app.bo.web.session.LoginUser;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mort
 */
@LoginRequired
public class UserController {
    @Inject
    UserService userService;
    @Inject
    RoleService roleService;
    @Inject
    AuthorizationService authorizationService;

    public Response search(Request request) {
        SearchUserRequest searchUserRequest = request.bean(SearchUserRequest.class);
        SearchUserResponse searchUserResponse = userService.search(searchUserRequest);
        return Response.bean(searchUserResponse);
    }

    public Response create(Request request) {
        LoginUser loginUser = Sessions.currentUser(request);
        CreateUserWebRequest createUserWebRequest = request.bean(CreateUserWebRequest.class);
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.phoneNumber = createUserWebRequest.phoneNumber;
        createUserRequest.password = createUserWebRequest.password;
        createUserRequest.name = createUserWebRequest.name;
        createUserRequest.requestedBy = loginUser.id;
        userService.create(createUserRequest);
        return Response.empty();
    }

    public Response get(Request request) {
        String userId = request.pathParam("id");
        GetUserResponse userResponse = userService.get(userId);
        UserWebView userView = new UserWebView();
        userView.user = new UserWebView.User();
        userView.user.id = userResponse.id;
        userView.user.phoneNumber = userResponse.phoneNumber;
        userView.user.name = userResponse.name;
        userView.user.status = userResponse.status;

        SearchRoleRequest searchRoleRequest = new SearchRoleRequest();
        List<RoleView> roles = roleService.search(searchRoleRequest);

        userView.roles = roles.stream().map(role -> {
            UserWebView.Role roleView = new UserWebView.Role();
            roleView.code = role.code;
            roleView.name = role.name;
            roleView.hasRole = userResponse.roleCodes.contains(role.code);
            return roleView;
        }).collect(Collectors.toList());

        return Response.bean(userView);
    }

    public Response update(Request request) {
        String userId = request.pathParam("id");
        UpdateUserWebRequest updateUserWebRequest = request.bean(UpdateUserWebRequest.class);
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.name = updateUserWebRequest.name;
        updateUserRequest.requestedBy = Sessions.currentUser(request).id;
        userService.update(userId, updateUserRequest);
        return Response.empty();
    }

    public Response updateStatus(Request request) {
        String userId = request.pathParam("id");
        UpdateUserStatusWebRequest updateUserStatusWebRequest = request.bean(UpdateUserStatusWebRequest.class);
        LoginUser loginUser = Sessions.currentUser(request);
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.status = updateUserStatusWebRequest.status;
        updateUserRequest.requestedBy = loginUser.id;
        userService.update(userId, updateUserRequest);
        return Response.empty();
    }

    public Response updateRole(Request request) {
        String userId = request.pathParam("id");
        UpdateRoleWebRequest updateRoleWebRequest = request.bean(UpdateRoleWebRequest.class);
        UpdateUserRoleRequest updateUserRoleRequest = new UpdateUserRoleRequest();
        updateUserRoleRequest.roleCode = updateRoleWebRequest.code;
        updateUserRoleRequest.action = updateRoleWebRequest.roleAction;
        updateUserRoleRequest.requestedBy = Sessions.currentUser(request).id;
        userService.updateUserRole(userId, updateUserRoleRequest);
        return Response.empty();
    }

    public Response resetPassword(Request request) {
        String userId = Sessions.currentUser(request).id;
        ResetPasswordRequest resetPasswordRequest = request.bean(ResetPasswordRequest.class);
        resetPasswordRequest.requestedBy = userId;
        authorizationService.resetPassword(userId, resetPasswordRequest);
        return Response.empty();
    }
}
