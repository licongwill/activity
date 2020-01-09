package com.me.activity.dao;


import com.me.activity.po.MeSysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MeSysUserMapper {
    /**
    *@Description: 主键删除
    *@Param: [id]
    *@return: int
    *@Author: lic
    *@date: 2019/8/29
    */
    int deleteByPrimaryKey(Long id);

    /**
    *@Description: 新增
    *@Param: [record]
    *@return: int
    *@Author: lic
    *@date: 2019/8/29
    */
    int insert(MeSysUser record);

    /**
    *@Description: 新增
    *@Param: [record]
    *@return: int
    *@Author: lic
    *@date: 2019/8/29
    */
    int insertSelective(MeSysUser record);

    /**
    *@Description: 主键查询
    *@Param: [id]
    *@return: com.xyy.me.user.core.po.MeSysUser
    *@Author: lic
    *@date: 2019/8/29
    */
    MeSysUser selectByPrimaryKey(Long id);

    /**
    *@Description: 修改
    *@Param: [record]
    *@return: int
    *@Author: lic
    *@date: 2019/8/29
    */
    int updateByPrimaryKeySelective(MeSysUser record);

    /**
    *@Description: 修改
    *@Param: [record]
    *@return: int
    *@Author: lic
    *@date: 2019/8/29
    */
    int updateByPrimaryKey(MeSysUser record);

    /**
    *@Description: 列表查询
    *@Param: [paramsMap]
    *@return: java.util.List<com.xyy.me.user.core.po.MeSysUser>
    *@Author: lic
    *@date: 2019/8/29
    */
    List<MeSysUser>  selectListByParams(@Param("map") Map<String, Object> paramsMap);

    /**
    *@Description: 批量新增
    *@Param: [list]
    *@return: int
    *@Author: lic
    *@date: 2019/8/29
    */
    int batchAdd(@Param("list") List<MeSysUser> list);

    /**
    *@Description: 批量删除
    *@Param: [ids]
    *@return: int
    *@Author: lic
    *@date: 2019/8/29
    */
    int batchDel(@Param("ids") List<Long> ids);

    /**
    *@Description: 修改用户状态
    *@Param: [meSysUser]
    *@return: int
    *@Author: lic
    *@date: 2019/8/29
    */
    int updateByUserStatus(MeSysUser meSysUser);

    /**
    *@Description: 根据账号查询
    *@Param: [account]
    *@return: java.util.List<com.xyy.me.user.core.po.MeSysUser>
    *@Author: lic
    *@date: 2019/8/29
    */
    List<MeSysUser> selectByAccount(@Param("account") String account);

    /**
    *@Description: 查询岗位下的人员id
    *@Param: [deptName]
    *@return: java.util.List<java.lang.String>
    *@Author: lic
    *@date: 2019/9/27
    */
    List<String> selectOaIdsByDeptName(@Param("deptName") String deptName);

    /**
    *@Description: 查询角色下的人员id
    *@Param: [roleName]
    *@return: java.util.List<java.lang.String>
    *@Author: lic
    *@date: 2019/9/27
    */
    List<String> selectOaIdsByRoleName(@Param("roleName") String roleName);
    

    /**
     * 冻结部门下的所有成员
     *
     * @param params
     * @return
     */
    int updateFreezeDeptUser(MeSysUser params);
}