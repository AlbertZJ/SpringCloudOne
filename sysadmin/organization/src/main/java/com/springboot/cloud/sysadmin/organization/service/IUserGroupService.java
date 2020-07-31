package com.springboot.cloud.sysadmin.organization.service;

import java.util.Set;

public interface IUserGroupService {
    /**
     * 给用户添加组织
     *
     * @param userId
     * @param groupIds
     * @return
     */
    boolean saveBatch(String userId, Set<String> groupIds);

    /**
     * 删除用户拥有的组织
     *
     * @param userId
     * @return
     */
    boolean removeByUserId(String userId);

    /**
     * 根据userId查询用户拥有组织id集合
     *
     * @param userId
     * @return
     */
    Set<String> queryByUserId(String userId);
}

