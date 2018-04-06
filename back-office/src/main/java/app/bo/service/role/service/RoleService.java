package app.bo.service.role.service;

import app.bo.service.role.domain.Role;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Lists;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mort
 */
public class RoleService {
    @Inject
    Repository<Role> roleRepository;
    @Inject
    Database database;

    public void create(CreateRoleRequest request) {
        LocalDateTime now = LocalDateTime.now();
        Role role = new Role();
        role.code = request.code;
        role.name = request.name;
        role.createdBy = request.requestedBy;
        role.createdTime = now;
        role.updatedBy = request.requestedBy;
        role.updatedTime = now;
        roleRepository.insert(role);
    }

    public RoleView get(String code) {
        Role role = roleRepository.get(code).orElseThrow(() -> new NotFoundException("role not found, roleCode=" + code));
        return roleView(role);
    }

    private RoleView roleView(Role role) {
        RoleView roleView = new RoleView();
        roleView.code = role.code;
        roleView.name = role.name;
        return roleView;
    }

    public void update(String code, UpdateRoleRequest request) {
        Role role = roleRepository.get(code).orElseThrow(() -> new NotFoundException("role not found, roleCode=" + code));
        role.name = request.name;
        role.updatedBy = request.requestedBy;
        role.updatedTime = LocalDateTime.now();
        roleRepository.update(role);
    }

    public List<RoleView> search(SearchRoleRequest request) {
        Query<Role> query = roleRepository.select();
        if (!Strings.isEmpty(request.code)) {
            ((Query) query).where("code = ?", request.code);
        }
        if (!Strings.isEmpty(request.name)) {
            ((Query) query).where(" name LIKE ? ", "%" + request.name + "%");
        }
        query.skip(request.skip);
        query.limit(request.limit);
        return query.fetch().stream().map(this::roleView).collect(Collectors.toList());
    }

    public Integer total(SearchRoleRequest request) {
        StringBuilder whereBuilder = new StringBuilder();
        List<Object> params = Lists.newArrayList();
        if (!Strings.isEmpty(request.code)) {
            whereBuilder.append(" code = ? ");
            params.add(request.code);
        }
        if (!Strings.isEmpty(request.name)) {
            if (whereBuilder.length() > 0) {
                whereBuilder.append(" AND");
            }
            whereBuilder.append(" name LIKE ? ");
            params.add("%" + request.name + "%");
        }
        String sql = "SELECT COUNT(0) FROM roles ";
        if (whereBuilder.length() > 0) {
            sql += " WHERE " + whereBuilder.toString();
        }

        return database.selectOne(sql, Integer.class, params.toArray()).orElse(0);
    }
}
