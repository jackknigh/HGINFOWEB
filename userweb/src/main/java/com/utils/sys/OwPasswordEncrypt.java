package com.utils.sys;

/**
 * Created by CuiL on 2018-11-23.
 */
public class OwPasswordEncrypt {
    public static final Integer MAXLEN = 16;


    public static char[] Encrypt(String pwd, String operatorId, Integer len) {
        byte bBuf;
        byte bCode;
        char[] res = new char[MAXLEN];
        char[] pwd_char = new char[MAXLEN];
        for (int i = 0; i < pwd.length(); i++) {
            pwd_char[i] = (byte)0;
            if(pwd.length()>=i)
                pwd_char[i] = pwd.charAt(i);
        }

        if (len == 0)
            return res;
        for (int i = 0; i <= MAXLEN - 1; i++) {

            bBuf = (byte) pwd_char[i];
            bCode = (byte) operatorId.charAt(i % len);
            res[i] = (char) (bBuf ^ bCode);
        }
        return res;
    }


    public static String StrtoBrinaryString(byte[] _b) {
        String res = "";
        for (int i = 0; i < _b.length; i++) {
            String hex = Integer.toHexString(_b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase());
        }
        return res;
    }

    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * @return
     */
    public static String str2HexStr(char[] bs) {
        StringBuilder sb = new StringBuilder("");
        char[] chars = "0123456789ABCDEF".toCharArray();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }
}
