package top.top6699.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
}
