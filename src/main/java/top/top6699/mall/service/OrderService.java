package top.top6699.mall.service;

import com.github.pagehelper.PageInfo;
import top.top6699.mall.model.request.CreateOrderReq;
import top.top6699.mall.model.vo.OrderVO;

/**
 * @author LongHaiJiang
 * @date 2021/10/23 23:36
 * @description 订单Service
 **/
public interface OrderService {


    String create(CreateOrderReq createOrderReq);

    OrderVO detail(String orderNo);

    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    void cancel(String orderNo);

    String qrcode(String orderNo);

    void pay(String orderNo);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    void deliver(String orderNo);

    void finish(String orderNo);
}
