package com.utils.sys;

import com.Application;
import com.dao.entity.lwaddress.Base_addr;
import com.dao.entity.lwaddress.Sec_addr;
import com.service.lwaddress.MsgSearchService;
import com.service.lwaddress.impl.Bs_startWayServiceImpl;
import com.service.lwaddress.impl.MsgSearchServiceImpl;
import com.service.lwaddress.impl.NameProcessServiceImpl;
import com.service.lwaddress.impl.ProcessGradeServiceImpl;
import com.utils.sys.lwaddress.AsciiUtil;
import com.utils.sys.lwaddress.BatchListUtil;
import com.utils.sys.lwaddress.ListUtil;
import com.utils.sys.lwaddress.SwitchNumber;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dto.constants.Constants.*;
import static com.utils.sys.lwaddress.AsciiUtil.SpecialHandl;

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


        List<Sec_addr> date = new ArrayList<>();
        for (int i = 0; i < 2002; i++) {
            Sec_addr sec_addr = new Sec_addr();
            sec_addr.setAddrJj("日访问法儿玩儿");
            sec_addr.setAddrSj("沟通问题已完工");
            sec_addr.setStreet("兴海大道");
            sec_addr.setArea("绍兴市");
            date.add(sec_addr);
        }
        long startTime = System.currentTimeMillis();
        Map<Integer, List<Sec_addr>> itemMap = new BatchListUtil<Sec_addr>().batchList(date, 200);
        long endTime = System.currentTimeMillis();
        System.out.println("需要:" + (endTime - startTime) / 1000 + "秒");
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
        name = split[split.length - 1];
        System.out.println(name);
        String shortAddr = SpecialHandl(name,null);
        shortAddr = shortAddr.replace("张三", "");
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

        System.out.println("b:" + b);
        System.out.println("f:" + b1);
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
                "aaaaaaaaaaaaaaaaaaaaa" +
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
    public void changeByMsg() {
        //正则规则
        String regex = "((\\{\"XM\":\").*(\"\\,\\n)(.*\".*\":\".*\"\\,\\n)(.*\".*\":\").*(\"\\,\\n)(.*\".*\":\".*\"\\n\\}))";
        //替换内容
        String regex1 = "$2AAA$3$4$5BBB$6$7";
        //读取文件内容
        File file = new File("E:\\text\\aaa.txt");
        String string = txt2String(file);

        long startTime = System.currentTimeMillis();

        string = string.replaceAll("\\r", "");
        string = string.replaceAll(regex, regex1);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        saveAsFileWriter(string);
    }

    //读取txt的内容
    public String txt2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    //将字符串写进txt
    private void saveAsFileWriter(String content) {
        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter("E:\\text\\ccc.txt");
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

















    public void changeByXml(String str) {
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
            if (count > 0) {
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
        String[] strb1 = new String[shortAddress.length() + 1];
        String[] strb2 = new String[shortAddress.length() + 1];
        Map<String, String[]> map = new HashMap<>();
        /*
        正则匹配
         */
        shortAddress = AsciiUtil.sbc2dbcCase(shortAddress);
        pattern = Pattern.compile("[a-zA-Z\\d一,二,三,四,五,六,七,八,九,十,零,壹,贰,叁,肆,伍,陆,柒,捌,玖,拾]+");
        matcher = pattern.matcher(shortAddress);
        while (matcher.find()) {

            if (str1.length() > 0) {
                str1 = str1 + "," + matcher.group();
            } else {
                str1 = matcher.group();
            }
            shortAddress = shortAddress.replace(matcher.group(), ",");
        }
        str2 = shortAddress;
        /*
        加特殊的标记
         */
        str1 = str1 + ",and";
        strb1 = str1.split(",");
       /* shortAddress = shortAddress+",and";
        strb2 = str2.split(",");*/

       /* for (int i = 0; i < str1.length(); i++) {
            strb1[i] = str1.substring(i,i+1);
            if(i+1==str1.length()){
                strb1[i+1] = "and";
            }
        }*/
        for (int i = 0; i < str2.length(); i++) {
            strb2[i] = str2.substring(i, i + 1);
            if (i + 1 == str2.length()) {
                strb2[i + 1] = "and";
            }
        }


        map.put("strb1", strb1);
        map.put("strb2", strb2);
    }


    @Test
    public void test4() {
        String reg = "北京|上海|杭州";
        String str = "我来自北京上海湖北广西杭州澳大利亚";
        str = str.replaceAll(reg, "");
        System.out.println(str);
    }

    @Test
    public void test5() {
        String reg = "北京null上海null杭州null";
        reg = reg.replace("null", "");
        System.out.println(reg);
    }

    @Test
    public void test6() {
        String str = "爱仕达多(4件)325024000000  000000 (000000) （000000）";
        String b = str.substring(0, 1);
        boolean a = str.contains("爱");
        str = SpecialHandl(str,null);
        System.out.println(a);
        System.out.println(b);
    }


    @Test
    public void test7() {
        int sum = 101;
        int count = 100;
        int start = 0;
        int value = sum / count;
        if (value != 0) {
            for (int i = 0; i < value + 1; i++) {
                if (i * count == sum) {
                    System.out.println("刚好凑整，结束");
                    continue;
                }
                if (i == value) {
                    int result = sum - (value * count);
                    System.out.println("从" + start + "到" + (result + start));
                    continue;
                }
                System.out.println("从" + start + "到" + (count + start));
                start = start + count;
            }
        } else {
            System.out.println("从" + start + "到" + sum);
        }
    }

    @Test
    public void test8() {
        List list = new ArrayList(10);
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("0");
        List<List<String>> lists = ListUtil.splitList(list, 3);
        System.out.println("拆成了" + lists.size() + "个集合");
        for (List<String> strings : lists) {
            System.out.println("集合长度" + strings.size());
            for (String string : strings) {
                System.out.println(string);
            }
        }
    }

    @Test
    public void test9() {
        Pattern pattern = Pattern.compile(REGEX_IDCARD);
        boolean flag = pattern.matcher("33062419960702533x").matches();
        System.out.println(flag);
    }

    @Test
    public void test10() {
        for (int i = 0; i < 20; i++) {
            System.out.println(i % 3);
        }
    }

    @Test
    public void test11() {
        String str = "（aaa）和325024室和000000和325000";
        String reg = "\\d+室";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            str = str.split(reg)[0] + matcher.group();
            System.out.println(str);
        }
    }

    @Test
    public void test12() {
        String str = "高新大道32号富康景苑5104";
        String reg = "\\d+号.+\\d-\\d";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            str = str.split(reg)[0] + matcher.group();
            System.out.println(str);
        } else {
            String reg1 = "\\d+号";
            Pattern pattern1 = Pattern.compile(reg1);
            Matcher matcher1 = pattern1.matcher(str);
            if (matcher1.find()) {
                str = str.split(reg1)[0] + matcher1.group();
                System.out.println(str);

            }
        }
    }

    @Test
    public void test13() {
        String str = "状元符阿撒打发我发发发龙腾南路260号56-龙腾一期2-306室";
        str = SpecialHandl(str,null);
        System.out.println(str);
        int a = 8500;
        System.out.println(a);
    }

    @Test
    public void test14() {
        String str = "高幢、栋、弄、单元新大A一B道42号32室富康景苑5一104";
        String replace = str.replace(null, "");
        System.out.println(replace);
        str = str.replaceAll(REGEX_DANYUAN, "-");
        System.out.println(str);
    }

    @Test
    public void test15() {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> map1 = new HashMap<>();
        HashMap<String, String> map2 = new HashMap<>();
        HashMap<String, String> map3 = new HashMap<>();
        map.put("aaa", "aaa");
        map1.put("aaa", "aaa");
        map2.put("aaa", "aaa");
        map3.put("aaa", "aaa");

        HashMap<String, String>[] str = new HashMap[4];
        System.out.println(str.length);
        str[0] = map;
        str[1] = map1;
        str[2] = map2;
        str[3] = map3;
        for (int i = 0; i < str.length; i++) {
            HashMap<String, String> stringStringHashMap = str[i];
            System.out.println(stringStringHashMap.get("aaa"));
        }
    }

    @Test
    public void test17() {
        String str = "1一022";
        //数字+一+数字，就把中文一改为-
        Pattern pattern = Pattern.compile(REGEX_SHUZIYI);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            if (str.split(REGEX_SHUZIYI).length > 0) {
                str = str.split(REGEX_SHUZIYI)[0] + matcher.group(2) + "-" + matcher.group(3);
            } else {
                str = matcher.group(2) + "-" + matcher.group(3);
            }
        }
        System.out.println(str);
    }

    @Test
    public void test18() {
        String str = " 电话号码 ：183468454";
        str = SpecialHandl(str,null);
        System.out.println(str);
    }

    @Test
    public void test19() {
        String str = "a{【】s；dadsa（asdasd）";
        //去除 特殊符号及后面的内容
        str = str.replaceAll(REGEX_FUHAO, "");
        System.out.println(str);
    }

    @Test
    public void test20() {
        String str = "凤凰家园3-1-\\n302室";
        //去除 特殊符号及后面的内容
        Pattern pattern = Pattern.compile(REGEX_QUDANYUAN);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            str = str.replaceAll(REGEX_QUDANYUAN, matcher.group(2) + "-" + matcher.group(5));
        }
        str = str.replaceAll("\\n", "");
        System.out.println(str);
    }

    @Test
    public void test21() {
        String str = "凤(awe)凰家园(weq)10281030-302室";
        //去除五位以上的连续数字
        str = SpecialHandl(str,null);
        System.out.println(str);
    }

    @Test
    public void test22() {
        String str = "凤凰家园3幢5楼502室";
        //去除五位以上的连续数字
        str = AsciiUtil.RegProcess(str);
        System.out.println(str);
    }

    @Test
    public void test23() {
        String shortAddr = "应素应素凡ad";
        String name = "应素凡";
        shortAddr = AsciiUtil.RegProcess(shortAddr);
        System.out.println(shortAddr);
    }


    @Test
    public void test24() {
        String shortAddress = "金沙国际2三零3";
        //指短地址中的数字
        String str1 = new String();
        //指短地址中的字符
        String str2;

        String[] strb1;
        String[] strb2 = new String[shortAddress.length() + 1];
        Map<String, String[]> map = new HashMap<>();
        /*
        正则匹配
         */
        shortAddress = AsciiUtil.sbc2dbcCase(shortAddress);
        Pattern pattern = Pattern.compile("[a-zA-Z\\d一,二,三,四,五,六,七,八,九,十,零,壹,贰,叁,肆,伍,陆,柒,捌,玖,拾]+");
        Matcher matcher = pattern.matcher(shortAddress);
        StringBuilder sb = new StringBuilder(shortAddress);
        while (matcher.find()) {
            //把中文数字转为英文数字
            if (str1.length() > 0) {
                String group = matcher.group();
                String i = SwitchNumber.caseStr2Num(group);
                if (!"-1".equals(i)) {
                    group = i;
                }
                str1 = str1 + "," + group;
            } else {
                str1 = matcher.group();
                String i = SwitchNumber.caseStr2Num(str1);
                if (!"-1".equals(i)) {
                    str1 = i;
                }
            }
            int index = sb.indexOf(matcher.group());
            sb = sb.replace(index, index + matcher.group().length(), "");

//            if (str1.length() > 0) {
//                str1 = str1+","+matcher.group();
//            } else {
//                str1 = matcher.group();
//            }
//            shortAddress = shortAddress.replace(matcher.group(),",");
        }
        str2 = sb.toString();
        /*
        加特殊的标记
         */
        str1 = str1 + ",and";
        strb1 = str1.split(",");
       /* shortAddress = shortAddress+",and";
        strb2 = str2.split(",");*/

       /* for (int i = 0; i < str1.length(); i++) {
            strb1[i] = str1.substring(i,i+1);
            if(i+1==str1.length()){
                strb1[i+1] = "and";
            }
        }*/

        for (int i = 0; i < str2.length(); i++) {
            strb2[i] = str2.substring(i, i + 1);
            if (i + 1 == str2.length()) {
                strb2[i + 1] = "and";
            }
        }

        map.put("strb1", strb1);
        map.put("strb2", strb2);
        System.out.println(map);
    }


    @Test
    public void test25() {
        NameProcessServiceImpl nameProcessService = new NameProcessServiceImpl();
        Base_addr basics = new Base_addr();
        Base_addr merge = new Base_addr();
        merge.setName1("张**");
        merge.setPhone("183****0702");
        basics.setName1("张三");


//        String[] name1a = basics.getName1().split("");
//        String[] phone1 = basics.getPhone().split("");
        //基准值
        String[] phone1 = null;
        String[] name1a = null;
        if (!StringUtils.isBlank(basics.getPhone())) {
            phone1 = basics.getPhone().split("");
        }
        if (!StringUtils.isBlank(basics.getName1())) {
            name1a = basics.getName1().split("");
        }


        //反写手机号
        if (phone1 != null) {
            if (merge.getPhone() != null && merge.getPhone().contains("*") && nameProcessService.a2nameLikedProcess(phone1, merge.getPhone().split(""))) {
                merge.setPhone(basics.getPhone());
            }
        }

        //反写姓名
        if (name1a != null && merge.getName1() != null && phone1 != null) {
            //基准值反写合并值，不带别名
            if (merge.getName1().contains("*")
                    && basics.getPhone().equals(merge.getPhone())
                    && nameProcessService.nameLikedProcess(merge.getName1().split(""), name1a)) {
                merge.setName1(basics.getName1());
            }

            //带别名反写
            if (merge.getPhone() != null
                    && basics.getPhone().equals(merge.getPhone())
                    && nameProcessService.a3nameLikedProcess(name1a, merge.getName1().split(""))) {
                //如果合并值带别名
                if (nameProcessService.isContainChinese(merge.getName1())) {
                    merge.setName1(basics.getName1());
                }
            }
        }

        //如果基准值和合并值手机号都为null就更据姓判断反写
        if (basics.getPhone() == null && merge.getPhone() == null && merge.getName1() != null) {
            //基准值反写合并值，不带别名
            if (merge.getName1().contains("*")
                    && nameProcessService.nameLikedProcess(merge.getName1().split(""), name1a)) {
                merge.setName1(basics.getName1());
            }

            //带别名反写
            if (nameProcessService.a3nameLikedProcess(name1a, merge.getName1().split(""))) {
                //如果合并值带别名
                if (nameProcessService.isContainChinese(merge.getName1())) {
                    merge.setName1(basics.getName1());
                }
            }
        }
        name1a = basics.getName1().split("");

//        if (name1a != null && merge.getName1() != null) {
//                //合并值反写基准值，不带别名的
//                if (basics.getName1().contains("*")
//                        && nameProcessService.nameLikedProcess(name1a, merge.getName1().split(""))) {
//                    basics.setName1(merge.getName1());
//                }
//
//                //基准值反写合并值，不带别名
//                    if (merge.getName1().contains("*")
//                            && nameProcessService.nameLikedProcess(merge.getName1().split(""), name1a)) {
//                        merge.setName1(basics.getName1());
//                    }
//
//                    //带别名反写
//                    if (merge.getPhone() != null
//                            && basics.getPhone().equals(merge.getPhone())
//                            && nameProcessService.a3nameLikedProcess(name1a, merge.getName1().split(""))) {
//                        //如果合并值带别名
//                        if(nameProcessService.isContainChinese(merge.getName1())){
//                            merge.setName1(basics.getName1());
//                        }
//                        //如果基准值带别名
//                        if(nameProcessService.isContainChinese(basics.getName1())){
//                            basics.setName1(merge.getName1());
//                        }
//                    }
//                    name1a = basics.getName1().split("");
//            }
        System.out.println(basics.getName1());
        System.out.println(merge.getName1());
    }

    @Test
    public void test26() {
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();

        String a = "12345";
        String b = "6712345";
        /*对比值*/
        String[] a1 = new String[a.length() + 1];
        for (int i = 0; i < a.length(); i++) {
            if (i == a.length() - 1) {
                a1[i] = a.split("")[i];
                a1[i + 1] = "and";
            } else {
                a1[i] = a.split("")[i];
            }
        }
        /*基准值*/
        String[] b1 = new String[b.length() + 1];
        for (int i = 0; i < b.length(); i++) {
            if (i == b.length() - 1) {
                b1[i] = b.split("")[i];
                b1[i + 1] = "and";
            } else {
                b1[i] = b.split("")[i];
            }
        }
        /*map中的"sum"总和，"integer"为integer数组的String形式*/
        /*传入 基准值字符串stra 匹配值字符串stab 滑块数n进行计算*/
        Map<String, Object> processresult1 = processGradeService.processCount(b1, a1, 3);
        BigDecimal suma = (BigDecimal) processresult1.get("sum");
        System.out.println(suma);
    }


    @Test
    public void test27() {
        int total = 7352;
        int start = 0;
        int batchcCount = 3000;
        int startCount = (total - start) / batchcCount + 1;
        int startValue = start;
        //通过总数和步进值得出循环几次操作
        for (int j = 0; j < startCount; j++) {
            //如果步进值不等于1
            if (batchcCount != 1) {
                //如果是最后一次操作
                if (j == startCount - 1) {
                    //判断是否有余数，没有余数就直接跳出循环
                    if (total % batchcCount == 0 && total - start / batchcCount == 1) {
                        System.out.println(total + "," + start + "," + batchcCount);
                        break;
                    }
                    //如果有余数就修改步进值为余数值
                    batchcCount = (total - startValue) - j * batchcCount;
                }
                if (batchcCount == 0 || start == total) {
                    System.out.println(total + "," + start + "," + batchcCount);
                    break;
                }
                //余数处理
                System.out.println(total + "," + start + "," + batchcCount);
                start = start + batchcCount;
                System.out.println(total + "," + start + "," + batchcCount);
            } else {
                if (total - start / batchcCount == 0) {
                    System.out.println(total + "," + start + "," + batchcCount);
                    break;
                }
                System.out.println(total + "," + start + "," + batchcCount);
                start = start + batchcCount;
                System.out.println(total + "," + start + "," + batchcCount);
            }
        }
    }

    @Test
    public void test28() {
        String a = "-花nbsp园(3e--四③幢╲n大null学(aaa3a司";
        if (a.startsWith("-")) {
            a = a.substring(1);
        }
        System.out.println(a);
    }

    @Test
    public void test29() {
        String str = "定安路35号萨德楼";
        //如果数字+号后面没有数字-数字或者中英文数字大小写字母就去除 数字+号 后面的内容.
        Pattern pattern = Pattern.compile(REGEX_CCHANGHAO);
        Matcher matcher = pattern.matcher(str);
        if (!matcher.find()) {
//            Pattern pattern  = Pattern.compile(REGEX_CHANGHAO);
//            Matcher matcher = pattern.matcher(str);
//            if (matcher.find()) {
//                if(str.split(REGEX_CHANGHAO).length>0) {
//                    str = str.split(REGEX_CHANGHAO)[0] + matcher.group();
//                }
//                return str;
//            }else {
            Pattern pattern1 = Pattern.compile(REGEX_DUANHAO);
            Matcher matcher1 = pattern1.matcher(str);
            if (matcher1.find()) {
                if (str.split(REGEX_DUANHAO).length > 0) {
                    str = str.split(REGEX_DUANHAO)[0] + matcher1.group();
                }
                System.out.println(str);
            }
//            }
        }
        System.out.println(str);
    }


    @Test
    public void test31() {
        NameProcessServiceImpl nameProcessService = new NameProcessServiceImpl();
        String mname = "城冰";
        String bname = "陈二乐";
        //基准值反写合并值，不带别名
        if (mname.contains("*")
                && nameProcessService.nameLikedProcess(mname.split(""), bname.split(""))) {
            mname = bname;
        }
        System.out.println(mname);
    }


    @Test
    public void test30() {
        String shortAddress = "302室";
        //指短地址中的数字
        String str1 = new String();
        //指短地址中的字符
        String str2;

        String[] strb1;
        String[] strb2;
        Map<String, String[]> map = new HashMap<>();
        /*
        正则匹配
         */
        shortAddress = AsciiUtil.sbc2dbcCase(shortAddress);
        Pattern pattern = Pattern.compile("[a-zA-Z\\d一,二,三,四,五,六,七,八,九,十,零,壹,贰,叁,肆,伍,陆,柒,捌,玖,拾]+");
        Matcher matcher = pattern.matcher(shortAddress);

        StringBuilder sb = new StringBuilder(shortAddress);
        while (matcher.find()) {
            //把中文数字转为英文数字
            if (str1.length() > 0) {
                String group = matcher.group();
                String i = SwitchNumber.caseStr2Num(group);
                if (!"-1".equals(i)) {
                    group = i;
                }
                str1 = str1 + "," + group;
            } else {
                str1 = matcher.group();
                String i = SwitchNumber.caseStr2Num(str1);
                if (!"-1".equals(i)) {
                    str1 = i;
                }
            }
            int index = sb.indexOf(matcher.group());
            sb = sb.replace(index, index + matcher.group().length(), "");

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
        if (strb1.length > 7) {
            String[] strb3 = new String[8];
            for (int i = 0; i < 7; i++) {
                strb3[i] = strb1[i];
            }
            //加特殊标记
            strb3[7] = "and";
            strb1 = strb3;
        } else {
            str1 = str1 + ",and";
            strb1 = str1.split(",");
        }


        strb2 = new String[str2.length() + 1];
        for (int i = 0; i < str2.length(); i++) {
            strb2[i] = str2.substring(i, i + 1);
            if (i + 1 == str2.length()) {
                strb2[i + 1] = "and";
            }
        }

        System.out.println(strb1);
        System.out.println(strb2);
    }

    @Test
    public void test33() {
        String name1 = "凤凰家园19 19号3幢一12室";
        String s = AsciiUtil.RegProcess(name1);
        System.out.println(s);
    }

    @Test
    public void test34() {
        String phone = "1832";
        String shortPhone = phone.substring(1, 3);
        shortPhone = shortPhone + phone.substring(phone.length()-4);
        System.out.println(shortPhone);
    }

    @Test
    public void test35() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        // 改成这样就好了
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time = cal.getTimeInMillis() - System.currentTimeMillis()+1800;
        System.out.println(time);
    }
}
