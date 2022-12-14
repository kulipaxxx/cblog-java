package com.cheng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cheng.entity.User;
import com.cheng.entity.role.Record;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author: cheng
 * @since 2022-10-31
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select r.sn from m_user u,m_role r where u.role_id = r.id and u.id = #{id}")
    List<Record> getRoles(Long id);

    @Select("select p.resource from m_permission p where p.id in" +
            "(select p.permission_id from m_role_permission p,m_role r,m_user u where")
    List<Record> getPermission();
}
