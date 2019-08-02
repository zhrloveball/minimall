package com.minimall.service;

import com.minimall.mbg.model.UmsAdmin;
import com.minimall.mbg.model.UmsPermission;

import java.util.List;

/**
 * 后台管理员 Service
 */
public interface UmsAdminService {

    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     *
     */
    UmsAdmin register(UmsAdmin umsAdmin);

    /**
     * 登录
     * @param username
     * @param password
     * @return JWT Token
     */
    String login(String username, String password);

    /**
     * 获取所有权限
     */
    List<UmsPermission> getPermissionList(Long adminId);
}
