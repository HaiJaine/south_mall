package top.top6699.mall.exception;

/**
 * @author LongHaiJiang
 * @date 2021/10/15 22:15
 * @description 异常枚举
 **/
public enum SouthMallExceptionEnum {
    NEED_USER_NAME(10001, "用户名不能为空");
    Integer code;
    String msg;

    SouthMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
