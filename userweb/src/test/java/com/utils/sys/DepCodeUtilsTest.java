package com.utils.sys;

import org.junit.Test;

/**
 * Created by CuiL on 2018-12-09.
 */
public class DepCodeUtilsTest {

    @Test
    public void Main(){
        System.out.println(DepCodeUtils.getAllChildDepCode("330000000000"));
        System.out.println(DepCodeUtils.getFirstChildDepCodeLike("330000000000"));
        System.out.println(DepCodeUtils.getAllChildDepCode("330100000000"));
        System.out.println(DepCodeUtils.getFirstChildDepCodeLike("330100000000"));
        System.out.println(DepCodeUtils.getAllChildDepCode("330101000000"));
        System.out.println(DepCodeUtils.getFirstChildDepCodeLike("330101000000"));
        System.out.println(DepCodeUtils.getAllChildDepCode("330100010000"));
        System.out.println(DepCodeUtils.getFirstChildDepCodeLike("330100010000"));
        System.out.println(DepCodeUtils.getAllChildDepCode("330100000100"));
        System.out.println(DepCodeUtils.getFirstChildDepCodeLike("330100000100"));
        System.out.println(DepCodeUtils.getAllChildDepCode("330100000001"));
        System.out.println(DepCodeUtils.getFirstChildDepCodeLike("330100000010"));
    }
    @Test
    public void test(){
        System.out.println(Integer.valueOf((int)(Math.random() * 100)).toString());
    }

}