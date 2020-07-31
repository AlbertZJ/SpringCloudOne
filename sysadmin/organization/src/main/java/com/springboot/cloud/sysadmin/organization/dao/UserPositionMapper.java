package com.springboot.cloud.sysadmin.organization.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.cloud.sysadmin.organization.entity.po.UserPosition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserPositionMapper extends BaseMapper<UserPosition> {
}
