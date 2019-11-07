package com.service.system;

/**
 * @author Noah
 * @since 1.0.0, 2019/03/15
 */
public interface AOPService {

    /**
     * 保存切面结果
     *
     * @param responseStr
     */
    void saveAopLog(String responseStr);
}
