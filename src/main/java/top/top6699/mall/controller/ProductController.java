package top.top6699.mall.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.top6699.mall.common.ApiRestResponse;
import top.top6699.mall.model.pojo.Product;
import top.top6699.mall.model.request.ProductListReq;
import top.top6699.mall.service.ProductService;

/**
 * @author LongHaiJiang
 * @date 2021/10/24 22:58
 * @description 前台商品Controller
 **/
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation("商品详情")
    @GetMapping("product/detail")
    public ApiRestResponse detail(@RequestParam Integer id) {
        Product product = productService.detail(id);
        return ApiRestResponse.success(product);
    }

    /**
     *
     * @param productListReq  参数类
     * @return
     */
    @ApiOperation("商品详情")
    @GetMapping("product/list")
    public ApiRestResponse list(ProductListReq productListReq) {
        PageInfo list = productService.list(productListReq);
        return ApiRestResponse.success(list);
    }
}
