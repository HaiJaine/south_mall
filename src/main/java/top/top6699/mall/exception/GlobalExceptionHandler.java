package top.top6699.mall.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.top6699.mall.common.ApiRestResponse;

/**
 * @author LongHaiJiang
 * @date 2021/10/15 23:49
 * @description 处理统一异常handler
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    //发生异常时，需要打印日志
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handlerException(Exception e) {
        log.error("Default Exception", e);
        return ApiRestResponse.error(SouthMallExceptionEnum.SYSTEM_ERROR);
    }

    /**
     * 处理SouthMall异常
     */
    @ExceptionHandler(SouthMallException.class)
    @ResponseBody
    public Object handlerSouthMallException(SouthMallException e) {
        log.error("SouthMall Exception", e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }
}
