package top.top6699.mall.model.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * imooc_mall_category
 * @author 
 */
public class Category implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 分类目录名称
     */
    private String name;

    /**
     * 分类目录级别，例如1代表一级，2代表二级，3代表三级
     */
    private Integer type;

    /**
     * 父id，也就是上一级目录的id，如果是一级目录，那么父id为0
     */
    private Integer parentId;

    /**
     * 目录展示时的排序
     */
    private Integer orderNum;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
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