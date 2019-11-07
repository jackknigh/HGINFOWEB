package com.utils.sys;

import org.junit.Test;

/**
 * Created by CuiL on 2018-11-23.
 */
public class OwPasswordEncryptTest {
    private String operatorId="Admin";
    private String pwd ="ourway";
    @Test
    public void encrypt() throws Exception {
        System.out.println( OwPasswordEncrypt.Encrypt(pwd,operatorId,operatorId.length())  );
    }

    @Test
    public void strtoBrinaryString() throws Exception {
        System.out.println(OwPasswordEncrypt.str2HexStr(    OwPasswordEncrypt.Encrypt(pwd,operatorId,operatorId.length())  ) );
    }


}