package com.sys.service;

import com.supercla.BaseTester;
import com.utils.sys.RedisUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by CuiL on 2018-11-01.
 */
public class RedisTester extends BaseTester {
    @Autowired
    RedisUtil redisUtil ;


    @Test
    public void TestInsert(){
        redisUtil.set("tester","崔亮");

    }
}
