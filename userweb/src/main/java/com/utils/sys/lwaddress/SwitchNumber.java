package com.utils.sys.lwaddress;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 把中文数字转为阿拉伯数字
 */
public class SwitchNumber {
//    /**
//     * 支持到12位
//     *
//     * @param numberStr 中文数字
//     * @return int 数字
//     */
//    public static String getIntegerByNumberStr(String numberStr) {
//
//        // 返回结果
//        String sum = "0";
//
//        // null或空串直接返回
//        if (numberStr == null || ("").equals(numberStr)) {
//            return sum;
//        }
//
//        // 过亿的数字处理
//        if (numberStr.indexOf("亿") > 0) {
//            String currentNumberStr = numberStr.substring(0, numberStr.indexOf("亿"));
//            int currentNumber = testA(currentNumberStr);
//            sum += currentNumber * Math.pow(10, 8);
//            numberStr = numberStr.substring(numberStr.indexOf("亿") + 1);
//        }
//
//        // 过万的数字处理
//        if (numberStr.indexOf("万") > 0) {
//            String currentNumberStr = numberStr.substring(0, numberStr.indexOf("万"));
//            int currentNumber = testA(currentNumberStr);
//            sum += currentNumber * Math.pow(10, 4);
//            numberStr = numberStr.substring(numberStr.indexOf("万") + 1);
//        }
//
//        // 小于万的数字处理
//        if (!("").equals(numberStr)) {
//            int currentNumber = testA(numberStr);
//            sum += currentNumber;
//        }
//
//        return sum;
//    }
//
    /**
     * 把亿、万分开每4位一个单元，解析并获取到数据
     * @param testNumber
     * @return
     */
    public static String testA(String testNumber) {
        // 返回结果
        int sum = 0;

        // null或空串直接返回
        if(testNumber == null || ("").equals(testNumber)){
            return sum+"";
        }

        // 获取到千位数
        if (testNumber.indexOf("千") > 0) {
            String currentNumberStr = testNumber.substring(0, testNumber.indexOf("千"));
            sum += Integer.valueOf(testB(currentNumberStr)) * Math.pow(10, 3);
            testNumber = testNumber.substring(testNumber.indexOf("千") + 1);
        }

        // 对于特殊情况处理 比如10-19是个数字，十五转化为一十五，然后再进行处理
        if (testNumber.indexOf("百") == 0) {
            return testNumber;
        }

        // 获取到百位数
        if (testNumber.indexOf("百") > 0) {
            String currentNumberStr = testNumber.substring(0, testNumber.indexOf("百"));
            sum += Integer.valueOf(testB(currentNumberStr)) * Math.pow(10, 2);
            testNumber = testNumber.substring(testNumber.indexOf("百") + 1);
        }

        // 对于特殊情况处理 比如10-19是个数字，十五转化为一十五，然后再进行处理
        if (testNumber.indexOf("十") == 0) {
            testNumber = "一" + testNumber;
        }

        // 获取到十位数
        if (testNumber.indexOf("十") > 0) {
            String currentNumberStr = testNumber.substring(0, testNumber.indexOf("十"));
            sum += Integer.valueOf(testB(currentNumberStr)) * Math.pow(10, 1);
            testNumber = testNumber.substring(testNumber.indexOf("十") + 1);
        }

        // 获取到个位数
        if(!("").equals(testNumber)){
            sum += Integer.valueOf(testB(testNumber.replaceAll("零","")));
        }

        return sum+"";
    }

    public static String testB(String replaceNumber) {
        String[] split = replaceNumber.split("");
        String str = "";
        for (String s : split) {
            switch (s) {
                case "一":
                    str = str + "1";
                    break;
                case "二":
                    str = str + "2";
                    break;
                case "三":
                    str = str + "3";
                    break;
                case "四":
                    str = str + "4";
                    break;
                case "五":
                    str = str + "5";
                    break;
                case "六":
                    str = str + "6";
                    break;
                case "七":
                    str = str + "7";
                    break;
                case "八":
                    str = str + "8";
                    break;
                case "九":
                    str = str + "9";
                    break;
                case "零":
                    str = str + "0";
                    break;
                default:
                    str = str + s;
                    break;
            }
        }
        return str;
    }

    public static String caseStr2Num(String numberStr) {
        // 返回结果
        int sum = -1;

        // null或空串直接返回
        if (numberStr == null || ("").equals(numberStr)) {
            return String.valueOf(sum);
        }

        String[] split = numberStr.split("");
        StringBuilder sb = new StringBuilder();
        Pattern pattern  = Pattern.compile("\\d");
        for (String s : split) {
            Matcher matcher = pattern.matcher(s);
            if(matcher.find()){
                sb.append(s);
                continue;
            }
            String i = testB(s);
            sb.append(i);
        }
        return sb.toString();
    }
}
