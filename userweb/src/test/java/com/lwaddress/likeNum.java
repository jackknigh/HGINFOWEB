package com.lwaddress;

import com.service.lwaddress.impl.ProcessGradeServiceImpl;
import com.service.lwaddress.impl.StringPasringServiceImpl;
import com.utils.sys.lwaddress.AsciiUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dto.constants.Constants.REGEX_DANYUAN;
import static com.dto.constants.Constants.REGEX_SHUZIYI;

public class likeNum {
    @Test
    public void test1() {
        Map<String, String[]> strMapa = new HashMap<>();
        Map<String, String[]> strMapb = new HashMap<>();
        StringPasringServiceImpl stringParsingService = new StringPasringServiceImpl();
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();
        String basics = "瓯江路37号";
        String merge = "景南路37号精华1气垫二眼线液一";
            //正则匹配去除某些关键字
            String name = RegProcess(basics);
            //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
            Map stringMap = stringParsingService.stringParse(name);
            strMapa.put("stra", (String[])stringMap.get("strb1"));
            strMapb.put("stra", (String[])stringMap.get("strb2"));

            /*用于判断的阈值*/
            BigDecimal grace = new BigDecimal(0.8);

                    //正则匹配去除某些关键字
                    String name1 = RegProcess(merge);
                    //切将地址切割成字符串数组，装进map集合，strMapa是数字，strMapb是字符串
                    Map stringMap1 = stringParsingService.stringParse(name1);
                    strMapb.put("strb", (String[]) stringMap1.get("strb2"));
                    strMapa.put("strb", (String[]) stringMap1.get("strb1"));

                    /*返回一个map出来，其中包括计算用的Double的sum,String的integerer[]*/
                    //数字和字符相似度匹配
                    Map<String, Object> processresult1 = processGradeService.processDemo(strMapa, 7);
                    Map<String, Object> processresult2 = processGradeService.processDemo(strMapb, 3);

                    BigDecimal suma = (BigDecimal) processresult1.get("sum");
                    BigDecimal sumb = (BigDecimal) processresult2.get("sum");

                    //计算相似度
                    BigDecimal sum = getSum(suma, sumb);

                    //如果相似度大于阈值
                    if (sum.compareTo(grace) > 0 ) {
                        //绑定数据关联关系
                        System.out.println("相似");
                    }
    }


    /**
     * 正则处理
     * @param shortAddr
     * @return
     */
    public String RegProcess(String shortAddr){
        //数字+一+数字，就把中文一改为-
        Pattern pattern  = Pattern.compile(REGEX_SHUZIYI);
        Matcher matcher = pattern.matcher(shortAddr);
        if (matcher.find()) {
            if(shortAddr.split(REGEX_SHUZIYI).length>0){
                shortAddr = shortAddr.split(REGEX_SHUZIYI)[0]+matcher.group(2)+"-"+matcher.group(3);
            }else {
                shortAddr =  matcher.group(2)+"-"+matcher.group(3);
            }
        }
        //字符串中的大写字母改为小写字母
        shortAddr = AsciiUtil.toLowerCase(shortAddr);
        //幢、栋、弄、单元 改为-
        shortAddr = shortAddr.replaceAll(REGEX_DANYUAN,"-");
        //特殊字符处理
        shortAddr = AsciiUtil.SpecialHandl(shortAddr,null);
        return shortAddr;
    }


    public BigDecimal getSum(BigDecimal suma, BigDecimal sumb) {
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();
        BigDecimal weight1;
        BigDecimal weight2;

        /*判断数字位数决定权重*/
        switch (2) {
            case 2:
                weight1 = new BigDecimal(0.4);
                break;
            case 3:
                weight1 = new BigDecimal(0.5);
                break;
            case 4:
                weight1 = new BigDecimal(0.6);
                break;
            default:
                weight1 = new BigDecimal(0.7);
                break;
        }
        weight2 = BigDecimal.ONE.subtract(weight1);
        //计算相似度
        BigDecimal sum = processGradeService.processliked(suma, sumb, weight1, weight2);
        if (sum.compareTo(BigDecimal.valueOf(0.94546000)) == 0) {
            sum = BigDecimal.ONE;
        }
        System.out.println(sum);
        return sum;
    }
}
