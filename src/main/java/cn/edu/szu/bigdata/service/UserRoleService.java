package cn.edu.szu.bigdata.service;


import cn.edu.szu.bigdata.model.UserRole;

/**
 * Created by yangqj on 2017/4/26.
 */
public interface UserRoleService extends IService<UserRole> {

    public void addUserRole(UserRole userRole);
}
