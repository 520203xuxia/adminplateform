package cn.edu.szu.bigdata.mapper;

import cn.edu.szu.bigdata.model.Role;
import cn.edu.szu.bigdata.util.MyMapper;

import java.util.List;

public interface RoleMapper extends MyMapper<Role> {
    public List<Role> queryRoleListWithSelected(Integer id);
}