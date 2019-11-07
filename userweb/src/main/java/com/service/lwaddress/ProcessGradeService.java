package com.service.lwaddress;

import org.apache.poi.ss.formula.functions.T;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProcessGradeService {
    /*最大值求*/
    Integer[] processMaxSum(int a1,int a2,int n);
    /*实际值算法*/
    Integer[] processSum(String[] a1,String[] a2,int n);
    /*算法Demo*/
    Map<String,Object>  processDemo(Map<String,String[]> strMap,int n);
    /*单字符或者数组的总分*/
    BigDecimal processSingleSum(Integer[] a1);
    /*计算Integer[]的总和*/
    Map<String,Object> processCount(String a1[],String a2[],int n);
    /*相似度得分计算*/
    BigDecimal processliked(BigDecimal a1,BigDecimal a2,BigDecimal weight1,BigDecimal weight2);
}
