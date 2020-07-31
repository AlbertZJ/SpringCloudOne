package com.springboot.cloud.sysadmin.organization.service.impl;

//import com.alicp.jetcache.anno.CacheInvalidate;
//import com.alicp.jetcache.anno.CacheType;
//import com.alicp.jetcache.anno.CacheUpdate;
//import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.cloud.sysadmin.organization.dao.PositionMapper;
import com.springboot.cloud.sysadmin.organization.entity.param.PositionQueryParam;
import com.springboot.cloud.sysadmin.organization.entity.po.Position;
//import com.springboot.cloud.sysadmin.organization.exception.PositionNotFoundException;
import com.springboot.cloud.sysadmin.organization.service.IPositionService;
import com.springboot.cloud.sysadmin.organization.service.IUserPositionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class PositionService extends ServiceImpl<PositionMapper, Position> implements IPositionService {

    @Autowired
    private IUserPositionService userPositionService;

    @Override
    public boolean add(Position position) {
        return this.save(position);
    }

    @Override
//    @CacheInvalidate(name = "position::", key = "#id")
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
//    @CacheInvalidate(name = "position::", key = "#position.id")
    public boolean update(Position position) {
        return this.updateById(position);
    }

    @Override
//    @Cached(name = "position::", key = "#id", cacheType = CacheType.BOTH)
    public Position get(String id) {
//        Position position = this.getById(id);
//        if(Objects.isNull(position)){
//            throw new PositionNotFoundException("没有找到id为："+ id+ "的角色");
//        }
//        position.setResource();
        return this.getById(id);
    }

    @Override
    public List<Position> getAll() {
        return this.list();
    }

    @Override
//    @Cached(name = "position4user::", key = "#userId", cacheType = CacheType.BOTH)
    public List<Position> query(String userId) {
        Set<String> positionIds = userPositionService.queryByUserId(userId);
        return (List<Position>) this.listByIds(positionIds);
    }

    @Override
    public List<Position> query(PositionQueryParam positionQueryParam) {
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(null != positionQueryParam.getName(), "name", positionQueryParam.getName());
        return this.list(queryWrapper);
    }

    @Override
    public IPage<Position> query(Page page, PositionQueryParam positionQueryParam) {
        QueryWrapper<Position> queryWrapper = positionQueryParam.build();
        queryWrapper.eq(StringUtils.isNotBlank(positionQueryParam.getName()), "name", positionQueryParam.getName());
//        queryWrapper.eq(StringUtils.isNotBlank(positionQueryParam.getCode()), "code", positionQueryParam.getCode());
        return this.page(page, queryWrapper);
    }
}
