package com.utils.sys;

import com.supercla.BaseTester;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JasyptEncryptorTests extends BaseTester
{
    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void contextLoads()
    {
        System.out.println("key:"+stringEncryptor.decrypt("MPUri3LGk97aFHwCTmoYgrb7/HCuAudu"));;


        String pwd = stringEncryptor.encrypt("Spinfo0123");
        String rootEnc = stringEncryptor.encrypt("root");
        System.out.println("pwd enc = " + pwd);
        System.out.println("rootEnc enc = " + rootEnc);


         pwd = stringEncryptor.encrypt("ourway123");
         rootEnc = stringEncryptor.encrypt("root");
        System.out.println("pwd enc = " + pwd);
        System.out.println("rootEnc enc = " + rootEnc);

        pwd = stringEncryptor.encrypt("WeichaoDB123!@#");
        rootEnc = stringEncryptor.encrypt("root");
        System.out.println("pwd enc = " + pwd);
        System.out.println("rootEnc enc = " + rootEnc);

        pwd = stringEncryptor.encrypt("amityamity");
        rootEnc = stringEncryptor.encrypt("root");
        System.out.println("pwd enc = " + pwd);
        System.out.println("rootEnc enc = " + rootEnc);

        pwd = stringEncryptor.encrypt("ourway123");
        rootEnc = stringEncryptor.encrypt("root");
        System.out.println("pwd enc = " + pwd);
        System.out.println("rootEnc enc = " + rootEnc);
    }

}
