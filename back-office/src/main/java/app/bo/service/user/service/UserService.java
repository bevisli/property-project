package app.bo.service.user.service;

import app.bo.service.user.domain.User;
import core.framework.crypto.Hash;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Lists;
import core.framework.util.Strings;
import core.framework.web.exception.ForbiddenException;
import core.framework.web.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author mort
 */
public class UserService {
    @Inject
    Repository<User> userRepository;
    @Inject
    Database database;

    public CreateUserResponse create(CreateUserRequest request) {
        int count = database.selectOne("SELECT COUNT(0) FROM users WHERE phone_number = ?", Integer.class, request.phoneNumber).orElse(0);
        if (count > 0) {
            throw new ForbiddenException("user already existed", "PHONE_NUMBER_EXISTED");
        }
        User user = new User();
        user.id = UUID.randomUUID().toString();
        user.phoneNumber = request.phoneNumber;
        user.password = Hash.md5Hex(request.password);
        user.name = request.name;
        user.status = User.Status.ACTIVE;
        user.createdBy = request.requestedBy;
        user.createdTime = LocalDateTime.now();
        user.updatedBy = request.requestedBy;
        user.updatedTime = LocalDateTime.now();
        userRepository.insert(user);
        CreateUserResponse response = new CreateUserResponse();
        response.id = user.id;
        return response;
    }

    public SearchUserResponse search(SearchUserRequest request) {
        SearchUserResponse response = new SearchUserResponse();
        response.total = total(request);
        response.users = searchUser(request).stream().map(this::searchUserResponse).collect(Collectors.toList());
        return response;
    }

    private Integer total(SearchUserRequest request) {
        StringBuilder whereBuilder = new StringBuilder();
        List<Object> params = Lists.newArrayList();
        whereSearch(request, whereBuilder, params);
        return database.selectOne("SELECT COUNT(0) FROM users" + (whereBuilder.length() > 0 ? " WHERE" : "") + whereBuilder.toString(), Integer.class, params.toArray()).orElse(0);
    }

    private List<User> searchUser(SearchUserRequest request) {
        Query<User> query = userRepository.select();
        if (!Strings.isEmpty(request.phoneNumber)) {
            query.where("phone_number LIKE ?", request.phoneNumber);
        }
        if (!Strings.isEmpty(request.name)) {
            query.where("name LIKE ?", "%" + request.name + "%");
        }
        if (request.status != null) {
            query.where("status = ?", request.status.name());
        }
        query.skip(request.skip);
        query.limit(request.limit);
        return query.fetch();
    }

    private SearchUserResponse.User searchUserResponse(User user) {
        SearchUserResponse.User responseUser = new SearchUserResponse.User();
        responseUser.id = user.id;
        responseUser.phoneNumber = user.phoneNumber;
        responseUser.name = user.name;
        responseUser.status = UserStatusView.valueOf(user.status.name());
        return responseUser;
    }

    private void whereSearch(SearchUserRequest request, StringBuilder whereBuilder, List<Object> params) {
        if (!Strings.isEmpty(request.phoneNumber)) {
            whereBuilder.append(" phone_number LIKE ?");
            params.add("%" + request.phoneNumber + "%");
        }
        if (!Strings.isEmpty(request.name)) {
            sqlAnd(whereBuilder);
            whereBuilder.append(" name LIKE ?");
            params.add("%" + request.name + "%");
        }
        if (request.status != null) {
            sqlAnd(whereBuilder);
            whereBuilder.append(" status = ?");
            params.add(request.status.name());
        }
    }

    private void sqlAnd(StringBuilder whereBuilder) {
        if (whereBuilder.length() > 0) {
            whereBuilder.append(" AND");
        }
    }

    public GetUserResponse get(String id) {
        User user = userRepository.get(id).orElseThrow(() -> new NotFoundException("user is not found, id = " + id));
        GetUserResponse response = new GetUserResponse();
        response.id = user.id;
        response.phoneNumber = user.phoneNumber;
        response.name = user.name;
        response.status = UserStatusView.valueOf(user.status.name());
        response.roleCodes = database.select("SELECT role_code FROM user_role_mappings WHERE id = ?", String.class, user.id);
        return response;
    }

    public void update(String id, UpdateUserRequest request) {
        User user = userRepository.get(id).orElseThrow(() -> new NotFoundException("user is not found, id = " + id));
        user.name = request.name;
        user.status = User.Status.valueOf(request.status.name());
        user.updatedBy = request.requestedBy;
        user.updatedTime = LocalDateTime.now();
        userRepository.update(user);
    }

    public void updateUserRole(String id, UpdateUserRoleRequest request) {
        boolean existed = database.selectOne("SELECT COUNT(0) FROM user_role_mappings WHERE id = ? AND role_code = ?", Integer.class, id, request.roleCode).orElse(0) == 1;
        if (request.action == UpdateUserRoleRequest.RoleAction.ASSIGN) {
            if (!existed) {
                database.execute("INSERT INTO user_role_mappings VALUES(?, ?, ?, ?, ?)", UUID.randomUUID().toString(), id, request.roleCode, request.requestedBy, LocalDateTime.now());
            }
        } else {
            if (existed) {
                database.execute("DELETE FROM user_role_mappings WHERE id = ? AND role_code = ?", id, request.roleCode);
            }
        }
    }
}
