package top.top6699.mall.exception;

/**
 * @author LongHaiJiang
 * @date 2021/10/15 22:15
 * @description 异常枚举
 **/
public enum SouthMallExceptionEnum {
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002, "密码不能为空"),
    PASSWORD_TOO_SHORT(10003, "密码长度不能低于8位"),
    NAME_EXISTED(10004, "用户名已存在，注册失败"),
    INSERT_FAILED(10005, "注册失败，请重试"),
    SYSTEM_ERROR(20000, "系统异常"),
    ;

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
