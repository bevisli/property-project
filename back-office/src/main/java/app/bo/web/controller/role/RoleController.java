package app.bo.web.controller.role;

import app.bo.service.role.service.CreateRoleRequest;
import app.bo.service.role.service.RoleService;
import app.bo.service.role.service.SearchRoleRequest;
import app.bo.service.role.service.SearchRoleResponse;
import app.bo.service.role.service.UpdateRoleRequest;
import app.bo.web.Sessions;
import app.bo.web.interceptor.LoginRequired;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author mort
 */
@LoginRequired
public class RoleController {
    @Inject
    RoleService roleService;

    public Response search(Request request) {
        SearchRoleRequest searchRoleRequest = request.bean(SearchRoleRequest.class);
        SearchRoleResponse searchRoleResponse = new SearchRoleResponse();
        searchRoleResponse.roles = roleService.search(searchRoleRequest);
        searchRoleResponse.total = roleService.total(searchRoleRequest);
        return Response.bean(searchRoleResponse);
    }

    public Response get(Request request) {
        String code = request.pathParam("code");
        return Response.bean(roleService.get(code));
    }

    public Response create(Request request) {
        CreateRoleWebRequest createRoleWebRequest = request.bean(CreateRoleWebRequest.class);
        CreateRoleRequest createRoleRequest = new CreateRoleRequest();
        createRoleRequest.name = createRoleWebRequest.name;
        createRoleRequest.code = createRoleWebRequest.code;
        createRoleRequest.requestedBy = Sessions.currentUser(request).userName;
        roleService.create(createRoleRequest);
        return Response.empty();
    }

    public Response update(Request request) {
        String code = request.pathParam("code");
        UpdateRoleWebRequest updateRoleWebRequest = request.bean(UpdateRoleWebRequest.class);
        UpdateRoleRequest updateRoleRequest = new UpdateRoleRequest();
        updateRoleRequest.name = updateRoleWebRequest.name;
        updateRoleRequest.requestedBy = Sessions.currentUser(request).userName;
        roleService.update(code, updateRoleRequest);
        return Response.empty();
    }

}
