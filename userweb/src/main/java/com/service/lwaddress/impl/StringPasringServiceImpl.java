package com.service.lwaddress.impl;

import com.service.lwaddress.StringParsingService;
import com.utils.sys.lwaddress.AsciiUtil;
import com.utils.sys.lwaddress.SwitchNumber;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StringPasringServiceImpl implements StringParsingService {

    @Override
    public Map stringParse(String shortAddress) {

        //指短地址中的数字
        String str1 = new String();
        //指短地址中的字符
        String str2;

        String[] strb1;
        String[] strb2;
        Map<String,String[]> map = new HashMap<>();
        /*
        正则匹配
         */
        shortAddress = AsciiUtil.sbc2dbcCase(shortAddress);
        Pattern pattern  = Pattern.compile( "[a-zA-Z\\d一,二,三,四,五,六,七,八,九,十,零,壹,贰,叁,肆,伍,陆,柒,捌,玖,拾]+");
        Matcher matcher = pattern.matcher(shortAddress);

        StringBuilder sb = new StringBuilder(shortAddress);
        while (matcher.find()) {
            //把中文数字转为英文数字
            if (str1.length() > 0) {
                String group = matcher.group();
                String i = SwitchNumber.caseStr2Num(group);
                if(!"-1".equals(i)){
                    group = i;
                }
                str1 = str1+","+group;
            } else {
                str1 = matcher.group();
                String i = SwitchNumber.caseStr2Num(str1);
                if(!"-1".equals(i)){
                    str1 = i;
                }
            }
            int index = sb.indexOf(matcher.group());
            sb = sb.replace(index,index+matcher.group().length(),"");

//            if (str1.length() > 0) {
//                str1 = str1+","+matcher.group();
//            } else {
//                str1 = matcher.group();
//            }
//            shortAddress = shortAddress.replace(matcher.group(),",");
        }
        str2 = sb.toString();

        //数字最长7位
        strb1 = str1.split(",");
        if(strb1.length>7){
            String[] strb3 = new String[8];
            for (int i = 0; i < 7; i++) {
                strb3[i] = strb1[i];
            }
            //加特殊标记
            strb3[7] = "and";
            strb1 = strb3;
        }else {
            str1 = str1 + ",and";
            strb1=str1.split(",");
        }


        strb2 = new String[str2.length()+1];
        for (int i = 0; i < str2.length(); i++) {
            strb2[i] = str2.substring(i,i+1);
            if(i+1==str2.length()){
                strb2[i+1] = "and";
            }
        }

        map.put("strb1",strb1);
        map.put("strb2",strb2);
        return map;
    }

    @Override
    public String charBig5ToChinese(String s) {
        try{
            if ( s == null || s.equals( "" ) )
                return("");
            String newstring = new String( s.getBytes( "big5" ), "gb2312" );
            return(newstring);
        }
        catch ( UnsupportedEncodingException e )
        {
            return(s);
        }
    }


}
