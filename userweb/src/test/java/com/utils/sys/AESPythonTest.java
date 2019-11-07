package com.utils.sys;

import com.Application;
import com.dao.entity.lwaddress.Base_addr;
import com.dao.entity.lwaddress.Sec_addr;
import com.service.lwaddress.MsgSearchService;
import com.service.lwaddress.impl.Bs_startWayServiceImpl;
import com.service.lwaddress.impl.MsgSearchServiceImpl;
import com.service.lwaddress.impl.NameProcessServiceImpl;
import com.utils.sys.lwaddress.AsciiUtil;
import com.utils.sys.lwaddress.BatchListUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dto.constants.Constants.*;

/**
 * Created by CuiL on 2018-11-23.
 */
public class AESPythonTest {

    String sKey = "b2e8f02b9da350e4";
    String sIv = "b6d88ffa8502c805";
    String sSrc = "OurwayInfomation";

    @Test
    public void encrypt() throws Exception {
        // 加密
        long lStart = System.currentTimeMillis();
        String enString = AESPython.Encrypt(sSrc, sKey, sIv);
        System.out.println("加密后的字串是：" + enString);

        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加密耗时：" + lUseTime + "毫秒");

    }

    @Test
    public void decrypt() throws Exception {

        // 加密
        long lStart = System.currentTimeMillis();
        String enString = AESPython.Encrypt(sSrc, sKey, sIv);
        System.out.println("加密后的字串是：" + enString);

        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加密耗时：" + lUseTime + "毫秒");
        // 解密
        lStart = System.currentTimeMillis();
        String DeString = AESPython.Decrypt(enString, sKey, sIv);
        System.out.println("解密后的字串是：" + DeString);
        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("解密耗时：" + lUseTime + "毫秒");
    }

    @Test
    public void test() {
//        String name = "小*";
//        String phone = "168****6177";
//            if (!StringUtils.isBlank(name)) {
//                name = name.trim().replaceAll("(\\*)\\1{0,}", "%");
//                System.out.println(name);
//            }
//        if (!StringUtils.isBlank(phone)) {
//            phone = phone.trim().replaceAll("(\\*)\\1{0,}", "%");
//            System.out.println(phone);
//        }
//        String ss = "_aa测试aabmbpbcd";
//        String s1 = ss.replaceAll(REGEX_SYMBOL, "");
//        System.out.println(s1);
//        String s = "ａｓｄａｄ中发";
//        String str = AsciiUtil.sbc2dbcCase(s);
//        System.out.println("转换前：" + s + "转换后：" + str);
//        Pattern pattern = Pattern.compile("浙江省");
//        Matcher matcher = pattern.matcher("浙江");
//        if (matcher.find()) {
//            String provinceName = matcher.group();
//            System.out.println(provinceName);
//        }
//        NameProcessServiceImpl nameProcessService = new NameProcessServiceImpl();
//        Boolean str1 = nameProcessService.isContainChinese("张先生");
//        Boolean str2 = nameProcessService.isContainChinese("张先");
//        Boolean str3 = nameProcessService.isContainChinese("先");
//        Boolean str6 = nameProcessService.isContainChinese("女");
//        Boolean str4 = nameProcessService.isContainChinese("张");
//        System.out.println(str1);
//        System.out.println(str2);
//        System.out.println(str3);
//        System.out.println(str4);
//        System.out.println(str6);


        List<Sec_addr> date= new ArrayList<>();
        for (int i = 0; i < 2002; i++) {
            Sec_addr sec_addr = new Sec_addr();
            sec_addr.setAddrJj("日访问法儿玩儿");
            sec_addr.setAddrSj("沟通问题已完工");
            sec_addr.setStreet("兴海大道");
            sec_addr.setArea("绍兴市");
            date.add(sec_addr);
        }
        long startTime = System.currentTimeMillis();
        Map<Integer,List<Sec_addr>> itemMap = new BatchListUtil<Sec_addr>().batchList(date, 200);
        long endTime = System.currentTimeMillis();
        System.out.println("需要:"+(endTime-startTime)/1000+"秒");
        Set<Integer> keySet = itemMap.keySet();
        for (Integer integer : keySet) {
            List<Sec_addr> integers = itemMap.get(integer);
            System.out.println(integers);
        }
    }

    @Test
    public void test1() {
        String name = "温州市龙湾区浙江温州市龙湾区浙江省温州市龙湾区永兴乐富住宅区12幢5号12-5-12张三 11111111111 (312500) 0571-82625241";
        String[] split = name.split("浙江省");
        name = split[split.length-1];
        System.out.println(name);
        String shortAddr = AsciiUtil.SpecialHandl(name);
        shortAddr = shortAddr.replace("张三","");
        System.out.println(shortAddr);
    }

    @Test
    public void test2() {
        Date d = new Date();
        Date f = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String dateNowStr1 = "2019-10-28 00:23:55";
        try {
            d = sdf.parse(dateNowStr);
            f = sdf.parse(dateNowStr1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean b = DateUtil.isToday(d);
        boolean b1 = DateUtil.isToday(f);

        System.out.println("b:"+b);
        System.out.println("f:"+b1);
    }

    @Test
    public void test3() throws Exception {
        String str = "<data> 1 <data>\n" +
                "  <data> 2 <data>\n" +
                "   <data> 3 <data>\n" +
                " <data> 4 <data>\n" +
                "<data> 5 <data>";

        String str1 = "{\"XM\":\"张三\",\n" +
                "\"SEX\":\"男\",\n" +
                "\"SFZH\":\"123\",\n" +
                "\"AGE\":\"20\"\n}," +

                "{\"XM\":\"李四\",\n" +
                "\"SEX\":\"女\",\n" +
                "\"SFZH\":\"123\",\n" +
                "\"AGE\":\"20\"\n}," +
                "aaaaaaaaaaaaaaaaaaaaa"+
                "{\"XM\":\"张三\",\n" +
                "\"SEX\":\"男\",\n" +
                "\"SFZH\":\"7788\",\n" +
                "\"AGE\":\"28\"\n}";

        String str2 = "{\"XM\":\"张三\",\n" +
                " \"SEX\":\"男\",\n" +
                "   \"SFZH\":\"123\",\n" +
                " \"AGE\":\"20\"\n}";

        changeByXml(str);
//        changeByMsg(str1);
    }

    @Test
    public void changeByMsg(){
        String regex = "((\\{\"XM\":\").*(\"\\,\\n)(.*\".*\":\".*\"\\,\\n)(.*\".*\":\").*(\"\\,\\n)(.*\".*\":\".*\"\\n\\}))";
        String regex1 = "$2AAA$3$4$5BBB$6$7";


        File file = new File("E:\\text\\bbb.txt");
        String string = txt2String(file);

        long startTime = System.currentTimeMillis();
        string = string.replaceAll("\\r","");
        string = string.replaceAll(regex,regex1);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
//        System.out.println(string);

//        System.out.println("转换前:\n" + str);
//
//        str = str.replaceAll(regex,regex1);
//        System.out.println(str);

//        StringBuilder sb = new StringBuilder();
//        int count = 0;
//        while (true) {
//            Pattern pattern = Pattern.compile(regex);
//            Matcher matcher = pattern.matcher(str);
//            boolean flag = matcher.find();
//            if (!flag) {
//                System.out.println("=================最终转换结果====================");
//                System.out.println(sb);
//                return;
//            }
//            if(count>0){
//                sb.append(",");
//            }
////            System.out.println("转换后:");
//            sb.append(matcher.group(2))
//                    .append("*").append(matcher.group(3))
//                    .append(matcher.group(4))
//                    .append(matcher.group(5))
//                    .append("*")
//                    .append(matcher.group(6))
//                    .append(matcher.group(7));
//
////            System.out.println(sb1);
//
//            str = str.replace(matcher.group(1), "");
//            count++;
//            System.out.println("剩余:");
//            System.out.println(str1);
//        }
    }

    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }


    public void changeByXml(String str){
        String regex = "((<data>).*(<data>\\n)(.*<data>.*<data>\\n)(.*<data>).*(<data>\\n)(.*<data>.*<data>\\n)(.*<data>.*<data>))";

        System.out.println("转换前:\n" + str);

        StringBuilder sb = new StringBuilder();
        int count = 0;
        while (true) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            boolean flag = matcher.find();
            if (!flag) {
                System.out.println("=================最终转换结果====================");
                System.out.println(sb);
                return;
            }
            if(count>0){
                sb.append(",");
            }

            sb.append(matcher.group(2))
                    .append("AAA").append(matcher.group(3))
                    .append(matcher.group(4))
                    .append(matcher.group(5))
                    .append("BBB")
                    .append(matcher.group(6)).append(matcher.group(7))
                    .append(matcher.group(8)).toString();

            str = str.replace(matcher.group(1), "");
            count++;
        }
    }

    @Test
    public void stringParse() {
        String shortAddress = "金沙国际3幢305室三幢三0a室";
        Pattern pattern = null;
        Matcher matcher = null;
        String str1 = new String();
        String str2 = new String();
        String[] strb1 = new String[shortAddress.length()+1];
        String[] strb2 = new String[shortAddress.length()+1];
        Map<String,String[]> map = new HashMap<>();
        /*
        正则匹配
         */
        shortAddress = AsciiUtil.sbc2dbcCase(shortAddress);
        pattern = Pattern.compile( "[a-zA-Z\\d一,二,三,四,五,六,七,八,九,十,零,壹,贰,叁,肆,伍,陆,柒,捌,玖,拾]+");
        matcher = pattern.matcher(shortAddress);
        while (matcher.find()) {

            if (str1.length() > 0) {
                str1 = str1+","+matcher.group();
            } else {
                str1 = matcher.group();
            }
            shortAddress = shortAddress.replace(matcher.group(),",");
        }
        str2 = shortAddress;
        /*
        加特殊的标记
         */
        str1 = str1 + ",and";
        strb1=str1.split(",");
       /* shortAddress = shortAddress+",and";
        strb2 = str2.split(",");*/

       /* for (int i = 0; i < str1.length(); i++) {
            strb1[i] = str1.substring(i,i+1);
            if(i+1==str1.length()){
                strb1[i+1] = "and";
            }
        }*/
        for (int i = 0; i < str2.length(); i++) {
            strb2[i] = str2.substring(i,i+1);
            if(i+1==str2.length()){
                strb2[i+1] = "and";
            }
        }


        map.put("strb1",strb1);
        map.put("strb2",strb2);
    }


    @Test
    public void test4() {
        String reg = "北京|上海|杭州";
        String str = "我来自北京上海湖北广西杭州澳大利亚";
        str = str.replaceAll(reg,"");
        System.out.println(str);
    }

    @Test
    public void test5() {
        String reg = "北京null上海null杭州null";
        reg = reg.replace("null","");
        System.out.println(reg);
    }

    @Test
    public void test6() {
        String str = "爱仕达多(4件)325024000000  000000 (000000) （000000）";
        String b = str.substring(0, 1);
        boolean a = str.contains("爱");
        str = AsciiUtil.SpecialHandl(str);
        System.out.println(a);
        System.out.println(b);
    }
}