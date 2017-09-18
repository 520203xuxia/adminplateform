package cn.edu.szu.bigdata.mapper;

import cn.edu.szu.bigdata.model.UserRole;
import cn.edu.szu.bigdata.util.MyMapper;

import java.util.List;

public interface UserRoleMapper extends MyMapper<UserRole> {
    public List<Integer> findUserIdByRoleId(Integer roleId);
}