package com.service.lwaddress.impl;

import com.service.lwaddress.ProcessGradeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProcessGradeServiceImpl implements ProcessGradeService{
    @Autowired
    ProcessGradeService processGradeService;

    @Override
    public Integer[] processMaxSum(int a2, int a1,int n) {
        Integer[] max=new Integer[a1];
        for(int i=0;i<a1;i++){
            int maxsum=0;
            //首字母得1分
            if(i==0){
                maxsum=1;
            }

            /*对比的块刚好被全部包住的情况,首字母+正常值+最后N位加额外分*/
            if(i+n==a1){
                maxsum=1;
                int a = 1;
                for (int i1 = 0; i1 < n; i1++) {
                    maxsum=maxsum+a;
                    a = a+1;
                }
                max[i]=new Integer(maxsum);
                continue;
            }

            /*对比的块可以被全部包住的情况*/
            if(i+n<a1){
                int a = 1;
                if(i<a1-n){
                    for (int i1 = 0; i1 < n; i1++) {
                        maxsum = maxsum+a;
                        a = a+1;
                    }
                    max[i] = new Integer(maxsum);
                    continue;
                }else if(a1 > 2 && i>=a1-n){
                    //如果是最后N为就需要额外加1分
                    int b = 1;
                    for (int i1 = 0; i1 < n; i1++) {
                        maxsum=maxsum+b;
                        b = b+1;
                    }
                    max[i] = new Integer(maxsum);
                    continue;
                }
            }

            /*对比的块不能被全部包住的情况*/
            if(i+n>a1){
                maxsum=maxsum+1;
                int b = 1;
                for (int i1 = 0; i1 < a1-i; i1++) {
                    maxsum=maxsum+b;
                    b = b+1;
                }
                max[i]=new Integer(maxsum);
            }
        }
        return max;
    }

    @Override
    public Integer[] processSum(String[] a2, String[] a1, int n,boolean flag) {
        Integer[] max;
        if(flag) {
            max = new Integer[a1.length + 1];
        }else {
            max = new Integer[a1.length];
        }
        for(int a=0;a<a1.length;a++){
            max[a]=new Integer(0);
        }
        /*记录单次匹配的分数,若匹配上直接得一分所以初始值为1*/
        int a1_index=0;
        int a2_index=0;
        for (int i=0;i<a1.length;i++){
            for (int j=0;j<a2.length;j++){
                if(a1[i].equals(a2[j])){
                    //匹配到初始分1
                    int grace=1;

                    //如果是最后N位,对应下标相同就加1
                    if(a1.length-n<=i ){
                        int index = a1.length-1-i;
                        int index2 = a2.length-1-index;
                        if(a2.length-1>=index) {
                            if (a1[i].equals(a2[index2])) {
                                grace = grace + 1;
                            }
                        }
                    }

                    a1_index=i;
                    a2_index=j;

                    int fraction = 2;

                    for (int k = n-1; k > 0; k--) {
                        //第一位加1分
                        if (a1_index == 0 ) {
                            grace = grace + 1;
                        }

                        if (a2_index + 1 == a2.length) {
                            break;
                        }

                        if(a1_index + 1 == a1.length){
                            break;
                        }

                        //如果下一位匹配到加1
                        if(a1[a1_index+1].equals(a2[a2_index+1])){
                            /*否则即为可以进行下一级计算*/
                            a1_index=a1_index+1;
                            a2_index=a2_index+1;
                            grace=grace+fraction;
                            fraction = fraction+1;
                            continue;
                        }
                        break;
                    }

                    if (grace > max[i]) {
                        max[i] =  new Integer(grace);
                    }
                }
            }
        }
        if(flag) {
            max[a1.length] = 2;
        }
        return max;
    }

    @Override
    public Map<String,Object> processDemo(Map<String, String[]> strMap,int n,boolean flag) {
        Map<String,Object> sum=new HashMap<>();
        /*对比值*/
        String[] a1=strMap.get("strb");
        /*基准值*/
        String[] b1=strMap.get("stra");
        Map<String,Object> map=new HashMap<>();
        /*map中的"sum"总和，"integer"为integer数组的String形式*/
        /*传入 基准值字符串stra 匹配值字符串stab 滑块数n进行计算*/
        sum=processCount(a1,b1,n,flag);

        /*根据上面两个加上权重算总分*/
        return sum;
    }

    @Override
    public BigDecimal processliked(BigDecimal a1,BigDecimal a2,BigDecimal weight1,BigDecimal weight2) {
        /*multiply是 bigdecimal格式下的乘法，用于计算结果分*/
        return  (a1.multiply(weight1)).add(a2.multiply(weight2));
    }

    @Override
    public BigDecimal processSingleSum(Integer[] a1) {
        /*通过每个字符的分数计算总分*/
        int a=0;
        for (int i=0;i<a1.length;i++){
            a=a+a1[i];
        }
        return new BigDecimal(a);
    }

    @Override
    public Map<String,Object> processCount(String[] a1, String[] a2, int n,boolean flag) {

        Map<String,Object> sum=new HashMap<>();
        BigDecimal sum1=new BigDecimal(0);

        /*将收到的带符号的字符串，转化为需要截取的字符串并且避免下标越界*/
        String [] a=new String[a1.length-1];
        for(int number=0;number<a.length;number++){
            a[number]=a1[number];
        }

        String [] b=new String[a2.length-1];
        for(int number=0;number<b.length;number++){
            b[number]=a2[number];
        }

        /*processMaxSum最大值计算 aSumMax为当前传入两个字符串理论最大值*/
        Integer[] aSumMax=processMaxSum(a.length,b.length,n);
        /*sSum为实际值*/
        Integer[] aSum=processSum(a,b,n,flag);

        /*integerA为各个字符得分*/
        StringBuffer integerA=new StringBuffer();
        for(int i=0;i<aSum.length;i++){
            if(i+1==aSum.length) {
                integerA.append(aSum[i].toString());
                break;
            }
            integerA.append(aSum[i].toString() + "+");
        }
        String integerA1= integerA.toString();
        /*计算总分*/
        BigDecimal asummax=processSingleSum(aSumMax);

        if(asummax.compareTo(BigDecimal.ZERO)==0){
            sum.put("sum",BigDecimal.ZERO);
        }else {
            /*计算实际总分*/
            BigDecimal asum = processSingleSum(aSum);

            /*devicde为bigdecimal方法，实际值除以总分算出该次得分*/
            sum1 = asum.divide(asummax, 4, BigDecimal.ROUND_HALF_UP);
            sum.put("integer", integerA1);
            sum.put("sum", sum1);
            sum.put("asummax", asummax);
            sum.put("asum", asum);
        }
        return sum;
    }

    /**
     * 计算相似度分值
     *
     * @param suma
     * @param sumb
     * @return
     */
    public BigDecimal getSum(BigDecimal suma, BigDecimal sumb, String[] nums,String[] nums1,String[] strs,String[] strs1) {
        ProcessGradeServiceImpl processGradeService = new ProcessGradeServiceImpl();
        //数字占比
        BigDecimal weight1;
        //字符分占比
        BigDecimal weight2;

        //基准值和合并值都不带数字
        if (StringUtils.isBlank(nums[0]) && StringUtils.isBlank(nums1[0])) {
            weight1 = new BigDecimal(0);
        } else if(StringUtils.isBlank(strs[0]) && StringUtils.isBlank(strs1[0])){
            //基准值和合并值都不带字符
            weight1 = new BigDecimal(1);
        }else if(StringUtils.isBlank(nums[0]) && !StringUtils.isBlank(nums1[0])){
            //基准值不带数字，合并值带数字
            weight1 = new BigDecimal(1);
        }else {
            /*判断数字位数决定权重*/
            switch (nums.length - 1) {
                case 1:
                    weight1 = new BigDecimal(0.4);
                    break;
                case 2:
                    weight1 = new BigDecimal(0.6);
                    break;
                case 3:
                    weight1 = new BigDecimal(0.6);
                    break;
                case 4:
                    weight1 = new BigDecimal(0.6);
                    break;
                default:
                    weight1 = new BigDecimal(0.7);
                    break;
            }
        }

        weight2 = BigDecimal.ONE.subtract(weight1);

        //计算相似度
        BigDecimal sum = processGradeService.processliked(suma, sumb, weight1, weight2);
        if (sum.compareTo(BigDecimal.valueOf(0.94546000)) == 0) {
            sum = BigDecimal.ONE;
        }
        return sum;
    }
}


