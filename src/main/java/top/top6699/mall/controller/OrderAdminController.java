package top.top6699.mall.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.top6699.mall.common.ApiRestResponse;
import top.top6699.mall.service.OrderService;

/**
 * @author LongHaiJiang
 * @date 2021/10/24 22:58
 * @description 订单后台管理Controller
 **/
@RestController
@CrossOrigin
public class OrderAdminController {

    @Autowired
    OrderService orderService;

    @GetMapping("admin/order/list")
    @ApiOperation("管理员订单列表")

    public ApiRestResponse listForAdmin(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize) {
        PageInfo pageInfo = orderService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    /**
     * 发货。订单状态流程：0用户已取消，10未付款，20已付款，30已发货，40交易完成
     */
    @PostMapping("admin/order/delivered")
    @ApiOperation("管理员发货")

    public ApiRestResponse delivered(@RequestParam String orderNo) {
        orderService.deliver(orderNo);
        return ApiRestResponse.success();
    }

    /**
     * 完结订单。订单状态流程：0用户已取消，10未付款，20已付款，30已发货，40交易完成。管理员和用户都可以调用
     */
    @PostMapping("order/finish")
    @ApiOperation("完结订单")
    public ApiRestResponse finish(@RequestParam String orderNo) {
        orderService.finish(orderNo);
        return ApiRestResponse.success();
    }
}
