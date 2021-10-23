package top.top6699.mall.service;

import com.github.pagehelper.PageInfo;
import top.top6699.mall.model.pojo.Product;
import top.top6699.mall.model.request.AddProductReq;
import top.top6699.mall.model.request.ProductListReq;

/**
 * @author LongHaiJiang
 * @date 2021/10/23 23:10
 * @description 商品Service
 **/
public interface ProductService {

    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo list(ProductListReq productListReq);
}
