package top.top6699.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.top6699.mall.model.dao.UserMapper;
import top.top6699.mall.model.pojo.User;
import top.top6699.mall.service.UserService;

/**
 * @author LongHaiJiang
 * @date 2021/10/8 22:20
 * @description UserService实现类
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(3);
    }
}
