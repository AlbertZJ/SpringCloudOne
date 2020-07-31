package com.springboot.cloud.sysadmin.organization.service.impl;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cloud.sysadmin.organization.dao.UserMapper;
import com.springboot.cloud.sysadmin.organization.entity.param.UserQueryParam;
import com.springboot.cloud.sysadmin.organization.entity.po.User;
import com.springboot.cloud.sysadmin.organization.entity.vo.UserVo;
import com.springboot.cloud.sysadmin.organization.exception.UserNotFoundException;
import com.springboot.cloud.sysadmin.organization.service.IUserGroupService;
import com.springboot.cloud.sysadmin.organization.service.IUserPositionService;
import com.springboot.cloud.sysadmin.organization.service.IUserRoleService;
import com.springboot.cloud.sysadmin.organization.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IUserGroupService userGroupService;

    @Autowired
    private IUserPositionService userPositionService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public boolean add(User user) {
        if (StringUtils.isNotBlank(user.getPassword()))
            user.setPassword(passwordEncoder().encode(user.getPassword()));
        boolean inserts = this.save(user);
        userRoleService.saveBatch(user.getId(), user.getRoleIds());
        userPositionService.saveBatch(user.getId(), user.getPositionIds());
        userGroupService.saveBatch(user.getId(), user.getGroupIds());
        return inserts;
    }

//需要修改，！！！！！！！！！！！
    @Override
    @Transactional
//    @CacheInvalidate(name = "user::", key = "#id")
    public boolean delete(String id) {
        this.removeById(id);
        if(!userRoleService.removeByUserId(id)){
            return userRoleService.removeByUserId(id);
        }else if(!userPositionService.removeByUserId(id)){
            return userPositionService.removeByUserId(id);
        }else {
            return userGroupService.removeByUserId(id);
        }
    }

    @Override
    @Transactional
//    @CacheInvalidate(name = "user::", key = "#user.id")
    public boolean update(User user) {
        if (StringUtils.isNotBlank(user.getPassword()))
            user.setPassword(passwordEncoder().encode(user.getPassword()));
        boolean isSuccess = this.updateById(user);
        userRoleService.saveBatch(user.getId(), user.getRoleIds());
        userPositionService.saveBatch(user.getId(), user.getPositionIds());
        userGroupService.saveBatch(user.getId(), user.getGroupIds());
        return isSuccess;
    }

    @Override
//    @Cached(name = "user::", key = "#id", cacheType = CacheType.BOTH)
    public UserVo get(String id) {
        User user = this.getById(id);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("没有找到id为:" + id+"的用户");
        }
        user.setRoleIds(userRoleService.queryByUserId(id));
        user.setPositionIds(userPositionService.queryByUserId(id));
        user.setGroupIds(userGroupService.queryByUserId(id));
        return new UserVo(user);
    }

    @Override
//    @Cached(name = "user::", key = "#uniqueId", cacheType = CacheType.BOTH)
    public User getByUniqueId(String uniqueId) {
        User user = this.getOne(new QueryWrapper<User>()
                .eq("username", uniqueId)
                .or()
                .eq("mobile", uniqueId));
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("没有找到uniqueId为:" + uniqueId+"的用户");
        }
        user.setRoleIds(userRoleService.queryByUserId(user.getId()));
        user.setGroupIds(userGroupService.queryByUserId(user.getId()));
        user.setPositionIds(userPositionService.queryByUserId(user.getId()));
        return user;
    }

    @Override
    public IPage<UserVo> query(Page page, UserQueryParam userQueryParam) {
        QueryWrapper<User> queryWrapper = userQueryParam.build();
        queryWrapper.eq(StringUtils.isNotBlank(userQueryParam.getName()), "name", userQueryParam.getName());
        queryWrapper.eq(StringUtils.isNotBlank(userQueryParam.getUsername()), "username", userQueryParam.getUsername());
        queryWrapper.eq(StringUtils.isNotBlank(userQueryParam.getMobile()), "mobile", userQueryParam.getMobile());
        // 转换成VO
        IPage<User> iPageUser = this.page(page, queryWrapper);
        return iPageUser.convert(UserVo::new);
    }
}
