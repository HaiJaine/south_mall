package top.top6699.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.top6699.mall.util.QRCodeGenerator;
import top.top6699.mall.common.Constant;
import top.top6699.mall.exception.SouthMallException;
import top.top6699.mall.exception.SouthMallExceptionEnum;
import top.top6699.mall.filter.UserFilter;
import top.top6699.mall.model.dao.CartMapper;
import top.top6699.mall.model.dao.OrderItemMapper;
import top.top6699.mall.model.dao.OrderMapper;
import top.top6699.mall.model.dao.ProductMapper;
import top.top6699.mall.model.pojo.Order;
import top.top6699.mall.model.pojo.OrderItem;
import top.top6699.mall.model.pojo.Product;
import top.top6699.mall.model.request.CreateOrderReq;
import top.top6699.mall.model.vo.CartVO;
import top.top6699.mall.model.vo.OrderItemVO;
import top.top6699.mall.model.vo.OrderVO;
import top.top6699.mall.service.CartService;
import top.top6699.mall.service.OrderService;
import top.top6699.mall.service.UserService;
import top.top6699.mall.util.OrderCodeFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LongHaiJiang
 * @date 2021/10/23 23:39
 * @description 订单Service实现类
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CartService cartService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Value("${file.upload.ip}")
    String ip;

    @Autowired
    UserService userService;

    //数据库事务
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(CreateOrderReq createOrderReq) {

        //拿到用户ID
        Integer userId = UserFilter.currentUser.getId();

        //查找购物车中的商品
        List<CartVO> cartVOList = cartService.list(userId);
        ArrayList<CartVO> cartVOListTemp = new ArrayList<>();
        for (CartVO cartVO : cartVOList) {
            //把购物车中选中的商品取出来
            if (cartVO.getSelected().equals(Constant.Cart.CHECKED)) {
                cartVOListTemp.add(cartVO);
            }
        }
        cartVOList = cartVOListTemp;
        //如果购物车已勾选的为空，报错
        if (CollectionUtils.isEmpty(cartVOList)) {
            throw new SouthMallException(SouthMallExceptionEnum.CART_EMPTY);
        }
        //判断商品是否存在、上下架状态、库存
        validSaleStatusAndStock(cartVOList);
        //把购物车对象转为订单item对象
        List<OrderItem> orderItemList = cartVOListToOrderItemList(cartVOList);
        //扣库存
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            int stock = product.getStock() - orderItem.getQuantity();
            if (stock < 0) {
                throw new SouthMallException(SouthMallExceptionEnum.NOT_ENOUGH);
            }
            product.setStock(stock);
            productMapper.updateByPrimaryKeySelective(product);
        }
        //把购物车中的已勾选商品删除
        cleanCart(cartVOList);
        //生成订单
        Order order = new Order();
        //生成订单号，有独立的规则
        String orderNo = OrderCodeFactory.getOrderCode(Long.valueOf(userId));
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTotalPrice(totalPrice(orderItemList));
        order.setReceiverName(createOrderReq.getReceiverName());
        order.setReceiverMobile(createOrderReq.getReceiverMobile());
        order.setReceiverAddress(createOrderReq.getReceiverAddress());
        order.setOrderStatus(Constant.OrderStatusEnum.NOT_PAID.getCode());
        order.setPostage(0);
        order.setPaymentType(1);
        //插入到Order表
        orderMapper.insertSelective(order);

        //循环保存每个商品到order_item表
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
            orderItemMapper.insertSelective(orderItem);
        }
        //把结果返回
        return orderNo;
    }

    private Integer totalPrice(List<OrderItem> orderItemList) {
        Integer totalPrice = 0;
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    private void cleanCart(List<CartVO> cartVOList) {
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO = cartVOList.get(i);
            cartMapper.deleteByPrimaryKey(cartVO.getId());
        }
    }

    /**
     * 把购物车对象转为订单item对象
     *
     * @param cartVOList
     * @return
     */
    private List<OrderItem> cartVOListToOrderItemList(List<CartVO> cartVOList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        //将购物车中的每一个商品都生成一个订单项
        for (CartVO cartVO : cartVOList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartVO.getProductId());
            //记录商品快照信息（后期商品可能修改名称、图片、价格、数量、总价等信息）
            orderItem.setProductName(cartVO.getProductName());
            orderItem.setProductImg(cartVO.getProductImage());
            orderItem.setUnitPrice(cartVO.getPrice());
            orderItem.setQuantity(cartVO.getQuantity());
            orderItem.setTotalPrice(cartVO.getTotalPrice());
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    /**
     * 判断商品是否存在、上下架状态、库存
     *
     * @param cartVOList
     */
    private void validSaleStatusAndStock(List<CartVO> cartVOList) {
        //遍历判断购物车中的每一个商品是否合法
        for (CartVO cartVO : cartVOList) {
            //通过购物车中商品去查找商品信息
            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());
            //判断商品是否存在，商品是否上架
            if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
                throw new SouthMallException(SouthMallExceptionEnum.NOT_SALE);
            }
            //判断商品库存（购物车数量大于库存，报错）
            if (cartVO.getQuantity() > product.getStock()) {
                throw new SouthMallException(SouthMallExceptionEnum.NOT_ENOUGH);
            }
        }
    }

    @Override
    public OrderVO detail(String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        //订单不存在，则报错
        if (order == null) {
            throw new SouthMallException(SouthMallExceptionEnum.NO_ORDER);
        }
        //订单存在，需要判断所属
        Integer userId = UserFilter.currentUser.getId();
        if (!order.getUserId().equals(userId)) {
            throw new SouthMallException(SouthMallExceptionEnum.NOT_YOUR_ORDER);
        }
        OrderVO orderVO = getOrderVO(order);
        return orderVO;
    }

    private OrderVO getOrderVO(Order order) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        //获取订单对应的orderItemVOList
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());
        List<OrderItemVO> orderItemVOList = new ArrayList<>();
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(orderItem, orderItemVO);
            orderItemVOList.add(orderItemVO);
        }
        orderVO.setOrderItemVOList(orderItemVOList);
        orderVO.setOrderStatusName(Constant.OrderStatusEnum.codeOf(orderVO.getOrderStatus()).getValue());
        return orderVO;
    }

    @Override
    public PageInfo listForCustomer(Integer pageNum, Integer pageSize) {
        Integer userId = UserFilter.currentUser.getId();
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectForCustomer(userId);
        List<OrderVO> orderVOList = orderListToOrderVOList(orderList);
        PageInfo pageInfo = new PageInfo<>(orderList);
        pageInfo.setList(orderVOList);
        return pageInfo;
    }

    private List<OrderVO> orderListToOrderVOList(List<Order> orderList) {
        List<OrderVO> orderVOList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            OrderVO orderVO = getOrderVO(order);
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    @Override
    public void cancel(String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        //查不到订单，报错
        if (order == null) {
            throw new SouthMallException(SouthMallExceptionEnum.NO_ORDER);
        }
        //验证用户身份
        //订单存在，需要判断所属
        Integer userId = UserFilter.currentUser.getId();
        if (!order.getUserId().equals(userId)) {
            throw new SouthMallException(SouthMallExceptionEnum.NOT_YOUR_ORDER);
        }
        if (order.getOrderStatus().equals(Constant.OrderStatusEnum.NOT_PAID.getCode())) {
            order.setOrderStatus(Constant.OrderStatusEnum.CANCELED.getCode());
            order.setEndTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new SouthMallException(SouthMallExceptionEnum.WRONG_ORDER_STATUS);
        }
    }

    @Override
    public String qrcode(String orderNo) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String address = ip + ":" + request.getLocalPort();
        String payUrl = "http://" + address + "/pay?orderNo=" + orderNo;
        try {
            QRCodeGenerator
                    .generateQRCodeImage(payUrl, 350, 350,
                            Constant.FILE_UPLOAD_DIR + orderNo + ".png");
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String pngAddress = "http://" + address + "/images/" + orderNo + ".png";
        return pngAddress;
    }

    @Override
    public void pay(String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        //查不到订单，报错
        if (order == null) {
            throw new SouthMallException(SouthMallExceptionEnum.NO_ORDER);
        }
        if (order.getOrderStatus() == Constant.OrderStatusEnum.NOT_PAID.getCode()) {
            order.setOrderStatus(Constant.OrderStatusEnum.PAID.getCode());
            order.setPayTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new SouthMallException(SouthMallExceptionEnum.WRONG_ORDER_STATUS);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectAllForAdmin();
        List<OrderVO> orderVOList = orderListToOrderVOList(orderList);
        PageInfo pageInfo = new PageInfo<>(orderList);
        pageInfo.setList(orderVOList);
        return pageInfo;
    }

    //发货
    @Override
    public void deliver(String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        //查不到订单，报错
        if (order == null) {
            throw new SouthMallException(SouthMallExceptionEnum.NO_ORDER);
        }
        if (order.getOrderStatus() == Constant.OrderStatusEnum.PAID.getCode()) {
            order.setOrderStatus(Constant.OrderStatusEnum.DELIVERED.getCode());
            order.setDeliveryTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new SouthMallException(SouthMallExceptionEnum.WRONG_ORDER_STATUS);
        }
    }

    @Override
    public void finish(String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        //查不到订单，报错
        if (order == null) {
            throw new SouthMallException(SouthMallExceptionEnum.NO_ORDER);
        }
        //如果是普通用户，就要校验订单的所属
        if (!userService.checkAdminRole(UserFilter.currentUser) && !order.getUserId().equals(UserFilter.currentUser.getId())) {
            throw new SouthMallException(SouthMallExceptionEnum.NOT_YOUR_ORDER);
        }
        //发货后可以完结订单
        if (order.getOrderStatus() == Constant.OrderStatusEnum.DELIVERED.getCode()) {
            order.setOrderStatus(Constant.OrderStatusEnum.FINISHED.getCode());
            order.setEndTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new SouthMallException(SouthMallExceptionEnum.WRONG_ORDER_STATUS);
        }
    }
}
