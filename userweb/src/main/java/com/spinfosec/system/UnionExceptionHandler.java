/* Copyright (C) 2019-2019 Hangzhou HSH Co. Ltd.
 * All right reserved.*/
package com.spinfosec.system;

import com.dto.pojo.spsys.ResponseMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理控制器
 */
@RestControllerAdvice
public class UnionExceptionHandler {
    /**
     * 处理自定义异常F
     */
    @ExceptionHandler(TMCException .class)
    public ResponseMessage handleRunException(TMCException e) {
        return ResponseMessage.sendDefined(e.getErrCode());
    }
}
