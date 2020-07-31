package com.springboot.cloud.sysadmin.organization.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.cloud.sysadmin.organization.entity.param.PositionQueryParam;
import com.springboot.cloud.sysadmin.organization.entity.po.Position;

import java.util.List;

public interface IPositionService {
    /**
     * 获取职位
     *
     * @param id
     * @return
     */
    Position get(String id);

    /**
     * 新增职位
     *
     * @param position
     * @return
     */
    boolean add(Position position);

    /**
     * 获取所有职位
     *
     * @return
     */
    List<Position> getAll();

    /**
     * 查询职位
     *
     * @return
     */
    List<Position> query(PositionQueryParam positionQueryParam);

    /**
     * 根据用户id查询用户拥有的职位
     *
     * @return
     */
    List<Position> query(String userId);

    /**
     * 查询职位
     *
     * @return
     */
    IPage<Position> query(Page page, PositionQueryParam positionQueryParam);

    /**
     * 更新职位信息
     *
     * @param position
     */
    boolean update(Position position);

    /**
     * 根据id删除职位
     *
     * @param id
     */
    boolean delete(String id);
}
