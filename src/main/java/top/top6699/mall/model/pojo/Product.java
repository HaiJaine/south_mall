package top.top6699.mall.model.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * imooc_mall_product
 * @author 
 */
public class Product implements Serializable {
    /**
     * 商品主键id
     */
    private Integer id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 产品图片,相对路径地址
     */
    private String image;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 价格,单位-分
     */
    private Integer price;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 商品上架状态：0-下架，1-上架
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}