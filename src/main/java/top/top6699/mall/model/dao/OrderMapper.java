package top.top6699.mall.model.dao;

import org.springframework.stereotype.Repository;
import top.top6699.mall.model.pojo.Order;

import java.util.List;

@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    Order selectByOrderNo(String orderNo);

    List<Order> selectForCustomer(Integer userId);

    List<Order> selectAllForAdmin();
}