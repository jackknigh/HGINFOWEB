package com.utils.sys.lwaddress;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dto.constants.Constants.*;

/**
 * 正则工具类
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
     * 大写字母转小写字母
     * @param str
     * @return
     */
    public static String toLowerCase(String str) {
        char[] s=str.toCharArray();
        for(int i=0;i<s.length;i++){
            if(s[i]>='A' && s[i]<='Z')
                s[i]+=32;
        }
        str="";
        for(int j=0;j<s.length;j++){
            str+=s[j];
        }
        return str;
    }

//    /**
//     * 正则处理
//     * @param shortAddr
//     * @param name
//     * @return
//     */
//    public static String RegProcess(String shortAddr,String name){
//
//        //全角转半角
//        shortAddr = sbc2dbcCase(shortAddr);
//
//        //数字+一+数字，就把中文一改为-
//        Pattern pattern  = Pattern.compile(REGEX_SHUZIYI);
//        Matcher matcher = pattern.matcher(shortAddr);
//        if (matcher.find()) {
//            if(shortAddr.split(REGEX_SHUZIYI).length>1){
//                shortAddr = shortAddr.split(REGEX_SHUZIYI)[0]+"-"+matcher.group(2)+ shortAddr.split(REGEX_SHUZIYI)[1];
//            }else if (shortAddr.split(REGEX_SHUZIYI).length>0){
//                shortAddr = shortAddr.split(REGEX_SHUZIYI)[0]+"-"+matcher.group(2);
//            }
//        }
//
//        //数字+空格+数字，就把 空格 改为-
//        Pattern pattern10  = Pattern.compile(REGEX_KONG);
//        Matcher matcher10 = pattern10.matcher(shortAddr);
//        if (matcher10.find()) {
//            if(shortAddr.split(REGEX_KONG).length>1){
//                shortAddr = shortAddr.split(REGEX_KONG)[0]+matcher10.group(2)+"-"+matcher10.group(3)+ shortAddr.split(REGEX_KONG)[1];
//            }else if (shortAddr.split(REGEX_KONG).length>0){
//                shortAddr = shortAddr.split(REGEX_KONG)[0]+matcher10.group(2)+"-"+matcher10.group(3);
//            }else {
//                shortAddr = matcher10.group(2)+"-"+matcher10.group(3);
//            }
//        }
//
//        //中文数字转阿拉伯数字
//        Pattern pattern8  = Pattern.compile(REGEX_ZSHUZI);
//        Matcher matcher8 = pattern8.matcher(shortAddr);
//        while (matcher8.find()) {
//            String group = matcher8.group();
//            String group1 = group;
//            group = group.replaceAll("十+","十");
//            if(group.indexOf("百")>group.indexOf("十")){
//                continue;
//            }
//            int count = 0;
//            String[] split = group.split("");
//            for (String s : split) {
//                if("十".equals(s)){
//                    count += 1;
//                }
//            }
//            if(count>1){
//                continue;
//            }
//            if(group.length()<2 || group.length()>5){
//                continue;
//            }
//            String s = SwitchNumber.testA(group);
//            shortAddr = shortAddr.replaceAll(group1,s);
//        }
//
//        //中文字符转数字
//        shortAddr = SwitchNumber.caseStr2Num(shortAddr);
//
//        //数字+o+数字，就把中文o改为0
//        Pattern pattern9  = Pattern.compile(REGEX_SHUZILIN);
//        Matcher matcher9 = pattern9.matcher(shortAddr);
//        if (matcher9.find()) {
//            if(shortAddr.split(REGEX_SHUZILIN).length>0){
//                shortAddr = shortAddr.split(REGEX_SHUZILIN)[0]+matcher9.group(2)+"0"+matcher9.group(3);
//            }
//        }
//
//        //字符串中的大写字母改为小写字母
//        shortAddr = AsciiUtil.toLowerCase(shortAddr);
//        //幢、栋、弄、单元 改为-
//        shortAddr = shortAddr.replaceAll(REGEX_DANYUAN,"-");
//        //特殊字符处理
//        shortAddr = AsciiUtil.SpecialHandl(shortAddr);
//        if(!StringUtils.isBlank(name)){
//            shortAddr = shortAddr.replace(name, "");
//        }
//        return shortAddr;
//    }

    /**
     * 正则处理
     * @param shortAddr
     * @return
     */
    public static String RegProcess(String shortAddr){
        //中文数字转阿拉伯数字
        Pattern pattern8  = Pattern.compile(REGEX_ZSHUZI);
        Matcher matcher8 = pattern8.matcher(shortAddr);
        while (matcher8.find()) {
            String group = matcher8.group();
            String group1 = group;
            group = group.replaceAll("十+","十");
            if(group.indexOf("百")>group.indexOf("十")){
                continue;
            }
            int count = 0;
            String[] split = group.split("");
            for (String s : split) {
                if("十".equals(s)){
                    count += 1;
                }
            }
            if(count>1){
                continue;
            }
            if(group.length()<2 || group.length()>5){
                continue;
            }
            String s = SwitchNumber.testA(group);
            shortAddr = shortAddr.replaceAll(group1,s);
        }

        //中文字符转数字
        shortAddr = SwitchNumber.caseStr2Num(shortAddr);

        //数字+一+数字，就把中文一改为-
        Pattern pattern  = Pattern.compile(REGEX_SHUZIYI);
        Matcher matcher = pattern.matcher(shortAddr);
        if (matcher.find()) {
            if(shortAddr.split(REGEX_SHUZIYI).length>1){
                shortAddr = shortAddr.split(REGEX_SHUZIYI)[0]+"-"+matcher.group(2)+ shortAddr.split(REGEX_SHUZIYI)[1];
            }else if (shortAddr.split(REGEX_SHUZIYI).length>0){
                shortAddr = shortAddr.split(REGEX_SHUZIYI)[0]+"-"+matcher.group(2);
            }
        }

        //数字+空格+数字，就把 空格 改为-
        Pattern pattern10  = Pattern.compile(REGEX_KONG);
        Matcher matcher10 = pattern10.matcher(shortAddr);
        if (matcher10.find()) {
            if(shortAddr.split(REGEX_KONG).length>1){
                shortAddr = shortAddr.split(REGEX_KONG)[0]+matcher10.group(2)+"-"+matcher10.group(3)+ shortAddr.split(REGEX_KONG)[1];
            }else if (shortAddr.split(REGEX_KONG).length>0){
                shortAddr = shortAddr.split(REGEX_KONG)[0]+matcher10.group(2)+"-"+matcher10.group(3);
            }else {
                shortAddr = matcher10.group(2)+"-"+matcher10.group(3);
            }
        }

        //数字+o+数字，就把中文o改为0
        Pattern pattern9  = Pattern.compile(REGEX_SHUZILIN);
        Matcher matcher9 = pattern9.matcher(shortAddr);
        if (matcher9.find()) {
            if(shortAddr.split(REGEX_SHUZILIN).length>0){
                shortAddr = shortAddr.split(REGEX_SHUZILIN)[0]+matcher9.group(2)+"0"+matcher9.group(3);
            }
        }

        //数字+楼+数字 变成 数字-数字
        Pattern pattern4  = Pattern.compile(REGEX_LOU);
        Matcher matcher4 = pattern4.matcher(shortAddr);
        if (matcher4.find()) {
            shortAddr = shortAddr.replaceAll(REGEX_LOU, matcher4.group(2)+"-"+matcher4.group(3));
        }

        //数字+层+数字 变成 数字-数字
        Pattern pattern5  = Pattern.compile(REGEX_CENG);
        Matcher matcher5 = pattern5.matcher(shortAddr);
        if (matcher5.find()) {
            shortAddr = shortAddr.replaceAll(REGEX_CENG, matcher5.group(2)+"-"+matcher5.group(3));
        }

        //字符串中的大写字母改为小写字母
        shortAddr = AsciiUtil.toLowerCase(shortAddr);
        //幢、栋、弄、单元 改为-
        shortAddr = shortAddr.replaceAll(REGEX_DANYUAN,"-");
        //去除单元
        Pattern pattern3  = Pattern.compile(REGEX_QUDANYUAN);
        Matcher matcher3 = pattern3.matcher(shortAddr);
        if (matcher3.find()) {
            shortAddr = shortAddr.replaceAll(REGEX_QUDANYUAN, matcher3.group(2)+"-"+matcher3.group(5));
        }
        return shortAddr;
    }

    /**
     * 特殊情况处理
     * @param str
     * @param name
     * @return
     */
    public static String SpecialHandl(String str,String name) {
        //全角转半角
        str = sbc2dbcCase(str);

        str = str.replaceAll(REGEX_HENG,"-");

        //去除转义字符
        str = str.replaceAll(REGEX_ZHUANYI,"");

        //去除 特殊符号及后面的内容
        str = str.replaceAll(REGEX_FUHAO,"");

        //去除特殊字符(特殊符号、指定邮编、手机号)
        str = str.replaceAll(REGEX_POSTCADE, "").replaceAll("\\\\r", "").replaceAll("\\n", "")
                .replaceAll("\\\\n", "").replaceAll(REGEX_SYMBOL,"").replaceAll("\\r", "")
                .replaceAll(REGEX_PHONE, "").replaceAll(REGEX_KUOHAO,"") .replaceAll("\\\\t", "")
                .replaceAll("\\\\", "");

        //数字+号后面如果有大小写英文或者阿拉伯、中文数字就保留号后面的字符
        Pattern pattern  = Pattern.compile(REGEX_CCHANGHAO);
        Matcher matcher = pattern.matcher(str);
        if(!matcher.find()){
            Pattern pattern1 = Pattern.compile(REGEX_DUANHAO);
            Matcher matcher1 = pattern1.matcher(str);
            if (matcher1.find()) {
                if (str.split(REGEX_DUANHAO).length > 0) {
                    str = str.split(REGEX_DUANHAO)[0] + matcher1.group();
                }
                return str;
            }
    }

        //去除 数字+室 后面的内容
        Pattern pattern2  = Pattern.compile(REGEX_SHI);
        Matcher matcher2 = pattern2.matcher(str);
        if (matcher2.find()) {
            if(str.split(REGEX_SHI).length>0){
                str = str.split(REGEX_SHI)[0]+matcher2.group();
            }
        }

        //去除 公司 后面的内容
        Pattern pattern5  = Pattern.compile(REGEX_GONGSI);
        Matcher matcher5 = pattern5.matcher(str);
        if (matcher5.find()) {
            str = matcher5.group();
        }

        //如果( 后面带数字
        Pattern pattern6  = Pattern.compile(REGEX_ZKH);
        Matcher matcher6 = pattern6.matcher(str);
        if (matcher6.find()) {
            String group = matcher6.group();
            Pattern pattern7  = Pattern.compile(REGEX_SHUZI);
            Matcher matcher7 = pattern7.matcher(group);
            if (matcher7.find()) {
                str = str.replace("(","");
            }else {
                str = str.replace(group,"");
            }
        }

        //去除五位以上的连续数字
        str =str.replaceAll(REGEX_NUMBER,"");

        //如果地址带姓名的就去除地址中的姓名
        if(!StringUtils.isBlank(name)){
            str = str.replace(name, "");
        }
        return str;
    }

//    /**
//     * 特殊情况处理
//     * @param str
//     * @return
//     */
//    public static String SpecialHandl(String str) {
//
//        str = str.replaceAll(REGEX_HENG,"-");
//
//        //去除转义字符
//        str = str.replaceAll(REGEX_ZHUANYI,"");
//
//
//        //去除 特殊符号及后面的内容
//        str = str.replaceAll(REGEX_FUHAO,"");
//
//        //去除特殊字符(特殊符号、指定邮编、手机号)
//        str = str.replaceAll(REGEX_POSTCADE, "").replaceAll("\\\\r", "").replaceAll("\\n", "")
//                .replaceAll("\\\\n", "").replaceAll(REGEX_SYMBOL,"").replaceAll("\\r", "")
//                .replaceAll(REGEX_PHONE, "").replaceAll(REGEX_KUOHAO,"") .replaceAll("\\\\t", "")
//                .replaceAll("\\\\", "");
//
//        //数字+楼+数字 变成 数字-数字
//        Pattern pattern4  = Pattern.compile(REGEX_LOU);
//        Matcher matcher4 = pattern4.matcher(str);
//        if (matcher4.find()) {
//            str = str.replaceAll(REGEX_LOU, matcher4.group(2)+"-"+matcher4.group(3));
//        }
//
//        //数字+层+数字 变成 数字-数字
//        Pattern pattern8  = Pattern.compile(REGEX_LOU);
//        Matcher matcher8 = pattern8.matcher(str);
//        if (matcher8.find()) {
//            str = str.replaceAll(REGEX_LOU, matcher8.group(2)+"-"+matcher8.group(3));
//        }
//
//        //去除单元
//        Pattern pattern3  = Pattern.compile(REGEX_QUDANYUAN);
//        Matcher matcher3 = pattern3.matcher(str);
//        if (matcher3.find()) {
//            str = str.replaceAll(REGEX_QUDANYUAN, matcher3.group(2)+"-"+matcher3.group(5));
//        }
//
//        //数字+号后面如果有大小写英文或者阿拉伯、中文数字就保留号后面的字符
//        Pattern pattern  = Pattern.compile(REGEX_CCHANGHAO);
//        Matcher matcher = pattern.matcher(str);
//        if(!matcher.find()){
////            Pattern pattern  = Pattern.compile(REGEX_CHANGHAO);
////            Matcher matcher = pattern.matcher(str);
////            if (matcher.find()) {
////                if(str.split(REGEX_CHANGHAO).length>0) {
////                    str = str.split(REGEX_CHANGHAO)[0] + matcher.group();
////                }
////                return str;
////            }else {
//            Pattern pattern1 = Pattern.compile(REGEX_DUANHAO);
//            Matcher matcher1 = pattern1.matcher(str);
//            if (matcher1.find()) {
//                if (str.split(REGEX_DUANHAO).length > 0) {
//                    str = str.split(REGEX_DUANHAO)[0] + matcher1.group();
//                }
//                return str;
//            }
////            }
//        }
//
//        //去除 数字+室 后面的内容
//        Pattern pattern2  = Pattern.compile(REGEX_SHI);
//        Matcher matcher2 = pattern2.matcher(str);
//        if (matcher2.find()) {
//            if(str.split(REGEX_SHI).length>0){
//                str = str.split(REGEX_SHI)[0]+matcher2.group();
//            }
//        }
//
//        //去除 公司 后面的内容
//        Pattern pattern5  = Pattern.compile(REGEX_GONGSI);
//        Matcher matcher5 = pattern5.matcher(str);
//        if (matcher5.find()) {
//            str = matcher5.group();
//        }
//
//        //如果( 后面带数字
//        Pattern pattern6  = Pattern.compile(REGEX_ZKH);
//        Matcher matcher6 = pattern6.matcher(str);
//        if (matcher6.find()) {
//            String group = matcher6.group();
//            Pattern pattern7  = Pattern.compile(REGEX_SHUZI);
//            Matcher matcher7 = pattern7.matcher(group);
//            if (matcher7.find()) {
//                str = str.replace("(","");
//            }else {
//                str = str.replace(group,"");
//            }
//        }
//
//        //去除五位以上的连续数字
//        str =str.replaceAll(REGEX_NUMBER,"");
//        return str;
//    }

    /**
     * 特殊情况收尾处理
     * @param str
     * @return
     */
    public static String SpecialEndHandl(String str) {
        //去除tod
        //去除首字母中的tod
        str = str.replaceAll("(tod)","");
        //如果有多个-，就替换为一个
        str = str.replaceAll(REGEX_GANG,"-");
        //去除首字母中的-，t
        if(str.startsWith("-")){
            str = str.substring(1);
        }
        return str;
    }
}
