package top.top6699.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.top6699.mall.common.ApiRestResponse;
import top.top6699.mall.exception.SouthMallException;
import top.top6699.mall.exception.SouthMallExceptionEnum;
import top.top6699.mall.model.pojo.User;
import top.top6699.mall.service.UserService;

/**
 * @author LongHaiJiang
 * @date 2021/10/8 22:17
 * @description 用户Controller
 **/
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/test")
    @ResponseBody
    public User personalPage() {
        return userService.getUser();
    }

    @ResponseBody
    @PostMapping("register")
    public ApiRestResponse register(@RequestParam("username") String username, @RequestParam("password") String password) throws SouthMallException {
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
}
