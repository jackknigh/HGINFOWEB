package com.lwaddress;


import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
  * @author zhangwq
  * @date 2018/6/30 20:58
  */
 public class addressTest {

   /**
    * TODO 请从这里开始补充代码
    *
    * 测试1：138 1234 1234  code:1
    *	结果：通过此手机号注册成功
    *
    * 测试2：13812345abc   code:0
    *	结果：通知本手机号无法注册，提示为非法手机号
    *
    * 测试3：13812345678   code:1
    *	结果：通知此手机号注册成功
    *
    * 测试4：138 1234 5678  code:2
    *	结果：提示此手机号已经被其他用户注册
    *
    * 测试5：98765432101  code:3
    *	结果：此手机号码为中国大陆非法手机号码
    */
   @Test
   public static void main(String[] args) {
    // TODO 输出最长对称字符串：goog
    String input1 = "google";
    input1.toCharArray();
    // TODO 输出3个最长对称字符串：aba/aca/ada
    String input2 = "abcda";

    // TODO 输出2个最长对称字符串：pop/upu，不会输出特殊字符的对称字符串p-p
    String input3 = "pop-upu";

    System.out.println(input1 + " -> " + findLCS(input1)); // 结果: google -> goog
    System.out.println(input2 + " -> " + findLCS(input2)); // 结果: abcda -> aca
    System.out.println(input3 + " -> " + findLCS(input3)); // 结果: opo-upu -> opo/upu
   }

 private static String findLCS(String input) {
  // 要返回的结果
  StringBuilder result = new StringBuilder();

  // 将字符串反转
  String reverse = new StringBuilder(input).reverse().toString();

  // 字符串长度
  int len = input.length();

  // 矩阵 -> 二维数组
  int[][] temp = new int[len][len];

  // 横向字符
  char[] hor = input.toCharArray();

  // 纵向字符
  char[] ver = reverse.toCharArray();

  // 给矩阵(二维数组赋值)
  for (int i = 0; i < len; i++) {
   for (int j = 0; j < len; j++) {
    temp[i][j] = (hor[j] == ver[i]) ? 1 : 0;
   }
  }

  // 找到第一个横向
  int horIndex = -1;
  for (int i = 0; i < len - 1; i++) {
   if (temp[0][i] == 1) {
    horIndex = i;
   }
  }

  // 找到第一个纵向
  int verIndex = -1;
  for (int i = 0; i < len - 1; i++) {
   if (temp[i][0] == 1) {
    verIndex = i;
   }
  }

  // 处理特殊情况的标识，如 abcda
  boolean flag = false;

  int indexHor = 0;
  if (horIndex != -1 && horIndex != 0) {
   for (int i = horIndex; i < len; i++) {
    if (temp[indexHor++][i] == 1) {
     result.append(hor[i]);
    }
   }
   flag = true;
  }

  int indexVer = verIndex;
  if (verIndex != -1) {
   if (flag) {
    result.append("/");
   }
   for (int i = 0; i < len - verIndex; i++) {
    if (temp[indexVer++][i] == 1) {
     result.append(hor[i]);
    }
   }
  }

  return result.toString();
 }
}
