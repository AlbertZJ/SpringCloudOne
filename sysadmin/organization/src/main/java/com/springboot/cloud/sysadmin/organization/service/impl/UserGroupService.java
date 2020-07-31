package com.springboot.cloud.sysadmin.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cloud.sysadmin.organization.dao.UserGroupMapper;
import com.springboot.cloud.sysadmin.organization.entity.po.UserGroup;
import com.springboot.cloud.sysadmin.organization.service.IUserGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserGroupService extends ServiceImpl<UserGroupMapper, UserGroup> implements IUserGroupService {

    @Override
    @Transactional
    public boolean saveBatch(String userId, Set<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds))
            return false;
        removeByUserId(userId);
        Set<UserGroup> userGroups = groupIds.stream().map(groupId -> new UserGroup(userId, groupId)).collect(Collectors.toSet());
        return saveBatch(userGroups);
    }

    @Override
    @Transactional
    public boolean removeByUserId(String userId) {
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserGroup::getUserId, userId);
        return remove(queryWrapper);
    }

    @Override
    public Set<String> queryByUserId(String userId) {
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserGroup> userGroupList = list(queryWrapper);
        return userGroupList.stream().map(UserGroup::getGroupId).collect(Collectors.toSet());
    }
}
