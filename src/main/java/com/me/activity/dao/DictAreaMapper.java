package com.me.activity.dao;

import com.me.activity.po.DictArea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictAreaMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(DictArea record);

    int insertSelective(DictArea record);

    DictArea selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DictArea record);

    int updateByPrimaryKey(DictArea record);

    /**
     * 城市字典三级联动
     *
     * @param pcode 父级行政区划代码
     * @param depth 是否省/直辖市：1-是 0-否
     * @return
     */
    List<DictArea> getArea(@Param("pcode") int pcode, @Param("depth") int depth);

    List<DictArea> getAreaList(@Param("depth") int depth);
}