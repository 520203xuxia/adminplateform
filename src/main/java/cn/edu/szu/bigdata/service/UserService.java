package cn.edu.szu.bigdata.service;

import cn.edu.szu.bigdata.model.User;
import com.github.pagehelper.PageInfo;


/**
 * Created by yangqj on 2017/4/21.
 */
public interface UserService extends IService<User>{
    PageInfo<User> selectByPage(User user, int start, int length);

    User selectByUsername(String username);

    void delUser(Integer userid);

}
