package top.top6699.mall.service;

import top.top6699.mall.exception.SouthMallException;
import top.top6699.mall.model.pojo.User;

/**
 * @author LongHaiJiang
 * @date 2021/10/8 22:19
 * @description UserService
 **/
public interface UserService {
    User getUser();

    void register(String username, String password) throws SouthMallException;

    User login(String username, String password) throws SouthMallException;

    void updateInformation(User user) throws SouthMallException;

    boolean checkAdminRole(User user);
}
