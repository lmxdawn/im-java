package com.lmxdawn.admin.service.auth;

import com.lmxdawn.admin.entity.auth.AuthRole;
import com.lmxdawn.admin.req.auth.AuthRoleQueryRequest;

import java.util.List;

public interface AuthRoleService {

    List<AuthRole> listAdminPage(AuthRoleQueryRequest authRoleQueryRequest);

    List<AuthRole> listAuthAdminRolePage(Integer page, Integer limit, Integer status);

    AuthRole findByName(String name);

    boolean insertAuthRole(AuthRole authRole);

    boolean updateAuthRole(AuthRole authRole);

    boolean deleteById(Long id);

}
