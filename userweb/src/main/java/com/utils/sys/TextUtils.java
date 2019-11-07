package com.utils.sys;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class TextUtils {
    protected static MessageDigest messagedigest = null;
    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(TextUtils.class.getName() + "初始化失败，MessageDigest不支持TextUtils。");
            nsaex.printStackTrace();
        }
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    public static boolean isEmpty(Object o) {
        if (null == o)
            return true;
//        if (o instanceof JSONNull)
//            return true;
        if ("".equals(o))
            return true;
        if ("null".equals(o))
            return true;
        return false;
    }

    public static boolean isEmpty(Collection collection) {
        if (null == collection) {
            return true;
        }
        if (collection.size() == 0) {
            return true;
        }
        return false;
    }

    public static Integer[] toIntegerArray(String s[]) {
        Integer toInt[] = new Integer[s.length];
        for (int i = 0; i < s.length; i++)
            toInt[i] = Integer.valueOf(s[i]);
        return toInt;
    }

    public static Byte[] toByteArray(byte b[]) {
        Byte byteWrapClass[] = new Byte[b.length];
        for (int i = 0; i < b.length; i++)
            byteWrapClass[i] = new Byte(b[i]);
        return byteWrapClass;
    }

    public static Long[] toLongArray(String s[]) {
        Long longWrapClass[] = new Long[s.length];
        for (int i = 0; i < s.length; i++)
            longWrapClass[i] = new Long(s[i]);

        return longWrapClass;
    }

    public static Float[] toFloatArray(String s[]) {
        Float floatWrapClass[] = new Float[s.length];
        for (int i = 0; i < s.length; i++)
            floatWrapClass[i] = new Float(s[i]);

        return floatWrapClass;
    }

    public static Double[] toDoubleArray(String s[]) {
        Double doubleWrapClass[] = new Double[s.length];
        for (int i = 0; i < s.length; i++)
            doubleWrapClass[i] = new Double(s[i]);

        return doubleWrapClass;
    }

    public static BigDecimal[] toBigDecimalArray(String s[]) {
        BigDecimal bigDecimalWrapClass[] = new BigDecimal[s.length];
        for (int i = 0; i < s.length; i++)
            bigDecimalWrapClass[i] = new BigDecimal(s[i]);

        return bigDecimalWrapClass;
    }

    public static Byte[][] toByteArray(String s[]) {
        Byte bs[][] = new Byte[0][];
        for (int i = 0; i < s.length; i++) {
            @SuppressWarnings("unused")
            Byte b[] = toByteArray(s[i].getBytes());
            // ArrayUtils.add(bs, b);
        }

        return bs;
    }

    @SuppressWarnings("unchecked")
    public static Collection toList(Object o[]) {

        return Arrays.asList(o);
    }

    public static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return (new StringBuilder(String.valueOf(hexDigits[d1]))).append(
                hexDigits[d2]).toString();
    }

    public static String MD5(String str) {
        // 确定计算方法
        MessageDigest md5;
        String resultString = "";
        try {
            md5 = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md5.digest(str.getBytes()));/* 213 */
            resultString = resultString.substring(8, 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    public static String getFileMD5String(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        messagedigest.update(byteBuffer);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }
    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        String c0 = hexDigits[(bt & 0xf0) >> 4];
        String c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static String getUUID() {
//		IdentifierGenerator gen = new UUIDHexGenerator();
        String uuid = "";
//		try {
//			uuid = (String) gen.generate(null, null);
//			// uuid = uuid;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        //uuid = uuid + System.currentTimeMillis();
        return uuid;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static void main(String[] args) {
//		int i = 65;
        System.out.println(TextUtils.MD5("root11").toUpperCase());
//		MessageDigest md5;
//		String resultString = "";
//		try {
//			md5 = MessageDigest.getInstance("MD5");
//			resultString = byteArrayToHexString(md5.digest("123456".getBytes()));/* 213 */
//			//resultString = resultString.substring(8, 24);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(resultString);

        //System.out.println(System.currentTimeMillis());;


    }
}
