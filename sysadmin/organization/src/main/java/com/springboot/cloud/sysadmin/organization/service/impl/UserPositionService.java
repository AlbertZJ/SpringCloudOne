package com.springboot.cloud.sysadmin.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cloud.sysadmin.organization.dao.UserPositionMapper;
import com.springboot.cloud.sysadmin.organization.entity.po.UserPosition;
import com.springboot.cloud.sysadmin.organization.service.IUserPositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserPositionService extends ServiceImpl<UserPositionMapper, UserPosition> implements IUserPositionService {

    @Override
    @Transactional
    public boolean saveBatch(String userId, Set<String> positionIds) {
        if (CollectionUtils.isEmpty(positionIds))
            return false;
        removeByUserId(userId);
        Set<UserPosition> userPositions = positionIds.stream().map(positionId -> new UserPosition(userId, positionId)).collect(Collectors.toSet());
        return saveBatch(userPositions);
    }

    @Override
    @Transactional
    public boolean removeByUserId(String userId) {
        QueryWrapper<UserPosition> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserPosition::getUserId, userId);
        return remove(queryWrapper);
    }

    @Override
    public Set<String> queryByUserId(String userId) {
        QueryWrapper<UserPosition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserPosition> userPositionList = list(queryWrapper);
        return userPositionList.stream().map(UserPosition::getPositionId).collect(Collectors.toSet());
    }
}
