package top.top6699.mall.service;

import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.Cacheable;
import top.top6699.mall.model.pojo.Category;
import top.top6699.mall.model.request.AddCategoryReq;
import top.top6699.mall.model.vo.CategoryVO;

import java.util.List;

/**
 * @author LongHaiJiang
 * @date 2021/10/22 14:56
 * @description 目录分类Service
 **/
public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    @Cacheable(value = "listCategoryForCustomer")
    List<CategoryVO> listCategoryForCustomer(Integer parentId);

}
