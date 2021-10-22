package top.top6699.mall.exception;

/**
 * @author LongHaiJiang
 * @date 2021/10/15 23:19
 * @description 统一异常
 **/
public class SouthMallException extends RuntimeException {
    private final Integer code;
    private final String message;

    public SouthMallException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public SouthMallException(SouthMallExceptionEnum exceptionEnum) {
        this(exceptionEnum.code, exceptionEnum.msg);
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
