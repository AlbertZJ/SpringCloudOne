package com.springboot.cloud.sysadmin.organization.service;

import java.util.Set;

public interface IUserPositionService {

    /**
     * 给用户添加职位
     *
     * @param userId
     * @param positionIds
     * @return
     */
    boolean saveBatch(String userId, Set<String> positionIds);

    /**
     * 删除用户拥有的职位
     *
     * @param userId
     * @return
     */
    boolean removeByUserId(String userId);

    /**
     * 根据userId查询用户拥有职位id集合
     *
     * @param userId
     * @return
     */
    Set<String> queryByUserId(String userId);
}
