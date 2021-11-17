package top.top6699.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.top6699.mall.common.ApiRestResponse;
import top.top6699.mall.common.Constant;
import top.top6699.mall.exception.SouthMallException;
import top.top6699.mall.exception.SouthMallExceptionEnum;
import top.top6699.mall.model.pojo.User;
import top.top6699.mall.model.request.UserReq;
import top.top6699.mall.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * @author LongHaiJiang
 * @date 2021/10/8 22:17
 * @description 用户Controller
 **/
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("user/register")
    @CrossOrigin
    public ApiRestResponse register(@RequestBody UserReq userReq) throws SouthMallException {
        String username = userReq.getUsername();
        String password = userReq.getPassword();
        //利用Spring框架中的StringUtils.isEmpty方式来判断是否为空
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(SouthMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(SouthMallExceptionEnum.NEED_PASSWORD);
        }
        //密码长度不能少于8位
        if (password.length() < 8) {
            return ApiRestResponse.error(SouthMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(username, password);
        return ApiRestResponse.success();
    }

    @PostMapping("user/login")
    @CrossOrigin
    public ApiRestResponse login(@RequestBody UserReq userReq,
                                 HttpSession session) throws SouthMallException {
        String username = userReq.getUsername();
        String password = userReq.getPassword();
        //利用Spring框架中的StringUtils.isEmpty方式来判断是否为空
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(SouthMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(SouthMallExceptionEnum.NEED_PASSWORD);
        }
        //密码长度不能少于8位
        if (password.length() < 8) {
            return ApiRestResponse.error(SouthMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        User user = userService.login(username, password);
        //保存用户信息时，不保存密码
        user.setPassword(null);
        session.setAttribute(Constant.SOUTH_MALL_USER, user);
        return ApiRestResponse.success(user);
    }

    @PostMapping("user/update")
    @CrossOrigin
    public ApiRestResponse updateUserInfo(HttpSession session, @RequestBody UserReq userReq) throws SouthMallException {
        String signature = userReq.getSignature();
        User currentUser = (User) session.getAttribute(Constant.SOUTH_MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(SouthMallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateInformation(user);
        return ApiRestResponse.success();
    }

    @PostMapping("user/logout")
    @CrossOrigin
    public ApiRestResponse logout(HttpSession session) {
        session.removeAttribute(Constant.SOUTH_MALL_USER);
        return ApiRestResponse.success();
    }

    @PostMapping("user/adminLogin")
    @CrossOrigin
    public ApiRestResponse adminLogin(@RequestBody UserReq userReq,
                                      HttpSession session) throws SouthMallException {
        String username = userReq.getUsername();
        String password = userReq.getPassword();
        //利用Spring框架中的StringUtils.isEmpty方式来判断是否为空
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(SouthMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(SouthMallExceptionEnum.NEED_PASSWORD);
        }
        //密码长度不能少于8位
        if (password.length() < 8) {
            return ApiRestResponse.error(SouthMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        User user = userService.login(username, password);
        //校验是否是管理员
        if (userService.checkAdminRole(user)) {
            //是管理员，执行操作
            //保存用户信息时，不保存密码
            user.setPassword(null);
            session.setAttribute(Constant.SOUTH_MALL_USER, user);
            return ApiRestResponse.success(user);
        } else {
            //不是管理员
            return ApiRestResponse.error(SouthMallExceptionEnum.NEED_ADMIN);
        }
    }
}
