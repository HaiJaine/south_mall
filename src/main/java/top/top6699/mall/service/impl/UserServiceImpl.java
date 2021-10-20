package top.top6699.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.top6699.mall.exception.SouthMallException;
import top.top6699.mall.exception.SouthMallExceptionEnum;
import top.top6699.mall.model.dao.UserMapper;
import top.top6699.mall.model.pojo.User;
import top.top6699.mall.service.UserService;
import top.top6699.mall.util.MD5Utils;

import java.security.NoSuchAlgorithmException;

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

    @Override
    public void register(String username, String password) throws SouthMallException {
        //查询用户名是否存在，不允许重复名
        User result = userMapper.selectByName(username);
        if (result != null) {
            //存在该用户
            throw new SouthMallException(SouthMallExceptionEnum.NAME_EXISTED);
        }
        //存入数据库
        User user = new User();
        user.setUsername(username);
        // user.setPassword(password);
        //对密码进行md5加密
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int count = userMapper.insertSelective(user);
        if (count == 0) {
            //注册失败
            throw new SouthMallException(SouthMallExceptionEnum.INSERT_FAILED);
        }

    }

    @Override
    public User login(String username, String password) throws SouthMallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            throw new SouthMallException(SouthMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    /**
     * 更新个性签名
     */
    @Override
    public void updateInformation(User user) throws SouthMallException {
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 1) {
            throw new SouthMallException(SouthMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public boolean checkAdminRole(User user){
        // 1.普通用户
        // 2.管理员用户
        return user.getRole().equals(2);

    }
}
