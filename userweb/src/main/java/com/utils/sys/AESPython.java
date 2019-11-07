package com.utils.sys;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @version v1.0
 * @title [凭证加密解密用于跟后台python交互]
 * @description [描述]
 */
public class AESPython
{
    public static final String IV = "fedcba9876543210";
    public static final String SKEY = "AES0123abcmiIO02";

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception
    {
        if (sKey == null)
        {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16)
        {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(encrypted);
//        return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception
    {
        try
        {
            // 判断Key是否正确
            if (sKey == null)
            {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16)
            {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encrypted1 = decoder.decode(sSrc);
//            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
            try
            {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
                return null;
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * mq参数解密
     * @param encStr
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String encStr, String sKey) throws Exception
    {
        try
        {
            // 判断Key是否正确
            if (sKey == null)
            {
                throw new Exception("the key is null");
            }
            // 判断Key是否为16位
            if (sKey.length() != 16)
            {
                throw new Exception("key length is not 16.");
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = hex2byte(encStr);
            try
            {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
                return null;
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static byte[] hex2byte(String strhex)
    {
        if (strhex == null)
        {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1)
        {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++)
        {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }
    // 加密
    public static String Encrypt(String sSrc, String sKey, String sIv) throws Exception
    {
        if (sKey == null)
        {
            return "Key为空null";
        }
        // 判断Key是否为16位
        if (sKey.length() != 16)
        {
            return "Key长度不是16位";
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(sIv.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(encrypted);
//        return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey, String sIv) throws Exception
    {
        try
        {
            // 判断Key是否正确
            if (sKey == null)
            {
                return "Key为空null";
            }
            // 判断Key是否为16位
            if (sKey.length() != 16)
            {
                return "Key长度不是16位";
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(sIv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encrypted1 = decoder.decode(sSrc);
//            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
            try
            {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
                return null;
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

}
