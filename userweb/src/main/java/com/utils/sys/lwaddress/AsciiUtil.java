package com.utils.sys.lwaddress;

import static com.dto.constants.Constants.*;

/**
 * 全角半角字符串转换工具类
 */
public class AsciiUtil {
    public static final char SBC_SPACE = 12288; // 全角空格 12288

    public static final char DBC_SPACE = 32; // 半角空格 32

    // ASCII character 33-126 <-> unicode 65281-65374
    public static final char ASCII_START = 33;

    public static final char ASCII_END = 126;

    public static final char UNICODE_START = 65281;

    public static final char UNICODE_END = 65374;

    public static final char DBC_SBC_STEP = 65248; // 全角半角转换间隔

    public static char sbc2dbc(char src) {
        if (src == SBC_SPACE) {
            return DBC_SPACE;
        }

        if (src >= UNICODE_START && src <= UNICODE_END) {
            return (char) (src - DBC_SBC_STEP);
        }
        return src;
    }

    /**
     * 全角转半角
     *
     * @param src
     * @return
     */
    public static String sbc2dbcCase(String src) {
        if (src == null) {
            return null;
        }
        char[] c = src.toCharArray();
        for (int i = 0; i < c.length; i++) {
            c[i] = sbc2dbc(c[i]);
        }
        return new String(c);
    }

    /**
     * 特殊情况处理
     * @param str
     * @return
     */
    public static String SpecialHandl(String str) {
        str = sbc2dbcCase(str);
        str = str.replaceAll(REGEX_POSTCADE, "").replaceAll("\\r", "")
                .replaceAll("\\n", "").replaceAll(REGEX_SYMBOL,"")
                .replaceAll(REGEX_PHONE, "");
        return str;
    }
}
