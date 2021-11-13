package top.top6699.mall.common;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.top6699.mall.exception.SouthMallException;
import top.top6699.mall.exception.SouthMallExceptionEnum;

import java.util.Set;

/**
 * @author LongHaiJiang
 * @date 2021/10/17 23:25
 * @description 常量值
 **/
@Component
public class Constant {
    //盐值
    public static final String SALT = "gf1ja46'['!987,653s1dgf789*469!!84a$s6s65#$g7869";

    //session 中保存的用户
    public static final String SOUTH_MALL_USER = "south_mall_user";

    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    //定义支持的排序字段
    public interface ProductListOrderBy {

        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price desc", "price asc");
    }

    public interface SaleStatus {

        int NOT_SALE = 0;//商品下架状态
        int SALE = 1;//商品上架状态
    }

    public interface Cart {

        int UN_CHECKED = 0;//购物车未选中状态
        int CHECKED = 1;//购物车选中状态
    }

    public enum OrderStatusEnum {
        CANCELED(0, "用户已取消"),
        NOT_PAID(10, "未付款"),
        PAID(20, "已付款"),
        DELIVERED(30, "已发货"),
        FINISHED(40, "交易完成");

        private String value;
        private int code;

        OrderStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new SouthMallException(SouthMallExceptionEnum.NO_ENUM);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public static String ICODE;

    @Value("${icode}")
    public void setICODE(String icode) {
        ICODE = icode;
    }
}
