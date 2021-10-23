package top.top6699.mall.service;

import top.top6699.mall.model.vo.CartVO;

import java.util.List;

/**
 * @author LongHaiJiang
 * @date 2021/10/23 23:28
 * @description 购物车Service
 **/
public interface CartService {

    List<CartVO> list(Integer userId);

    List<CartVO> add(Integer userId, Integer productId, Integer count);

    List<CartVO> update(Integer userId, Integer productId, Integer count);

    List<CartVO> delete(Integer userId, Integer productId);

    List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected);

    List<CartVO> selectAllOrNot(Integer userId, Integer selected);
}
