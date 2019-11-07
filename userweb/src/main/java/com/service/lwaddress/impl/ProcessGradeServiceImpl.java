package com.service.lwaddress.impl;

import com.service.lwaddress.ProcessGradeService;
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
    public Integer[] processMaxSum(int a1, int a2,int n) {
        int maxsum=0;
        Integer[] max=new Integer[a1];
        for(int i=0;i<a1;i++){
            if(i==0){
                maxsum=1;
            }if( i+n>=a1){
                maxsum=1+maxsum;
            }
            /*第一种情况a1中取得块长不越界*/
            if(a1>=i+n){
                /*对比的块刚好被全部包住的情况*/
                if(n==a2){
                    maxsum=1+n-1+1+maxsum;
                    max[i]=new Integer(maxsum);
                    maxsum=0;
                }
                /*对比的块可以被全部包住的情况*/
                if(n<a2){
                    maxsum=1+n-1+maxsum;
                    max[i]=new Integer(maxsum);
                    maxsum=0;

                }
                /*对比的块不能被全部包住的情况*/
                if(n>a2){
                    maxsum=1+a2-1+1+maxsum;
                    max[i]=new Integer(maxsum);
                    maxsum=0;
                }}
            /*第二种情况a1中取得块长越界了
             * 块长就是a1.length()-i
             * */
            if(a1<i+n){
                /*对比的块刚好被全部包住的情况*/
                if(a1-i==a2){
                    maxsum=1+a1-i-1+maxsum;
                    max[i]=new Integer(maxsum);
                    maxsum=0;
                }
                if(a1-i<a2){
                    maxsum=1+a1-i-1+maxsum;
                    max[i]=new Integer(maxsum);
                    maxsum=0;
                }
                if(a1-i>a2){
                    maxsum=1+a2-1+maxsum;
                    max[i]=new Integer(maxsum);
                    maxsum=0;
                }
            }
        }
        return max;
    }

    @Override
    public Integer[] processSum(String[] a1, String[] a2, int n) {
        Integer[] max=new Integer[a1.length];
        for(int a=0;a<a1.length;a++){
            max[a]=new Integer(0);
        }
        /*记录单次匹配的分数,若匹配上直接得一分所以初始值为1*/
        int a1_index=0;
        int a2_index=0;
        for (int i=0;i<a1.length;i++){
            for (int j=0;j<a2.length;j++){
                if(a1[i].equals(a2[j])){
                    int grace=1;
                    a1_index=i;
                    a2_index=j;
                    if(i+n<a1.length){
                        for(int N=n;N>0;N--){
                            /*加分时的特殊情况算法*/
                            /*先判断不影响是否停止的逻辑a2的头位置*/
                            if(a1_index==0){ grace=grace+1;    }
                            /*   下一级下标越界说明当前下标为尾数匹配成功，分数额外加一并比较分数离开
                             * 特殊情况是a2只有一位的情况，头尾同时匹配，算法同样满足
                             * */
                            if(a1_index+1==a1.length){
                                grace=grace+1;
                                if(grace>max[i]){max[i]=new Integer(grace);}
                                break;
                            }else{
                                if(a2_index+1 ==a2.length){
                                    if(grace>max[i]){max[i]=new Integer(grace);}
                                    break;
                                }
                                /*如果下一位不匹配直接比较分数并离开*/;
                                if(a1[a1_index+1].equals(a2[a2_index+1])){
                                    /*否则即为可以进行下一级计算*/
                                    a1_index=a1_index+1;
                                    a2_index=a2_index+1;
                                    if(a1_index==n+i)
                                    {
                                        if (grace > max[i]) {
                                            max[i] =  new Integer(grace);
                                        }
                                        break;
                                    }
                                    grace=grace+1;
                                }
                                  /*  else{
                                        *//*需不需要增加满分判断*//*
                                        if(grace>max[i]){max[i]=grace;}
                                        break;}*/
                                }

                            }
                            if(grace>max[i]) { max[i] = new Integer(grace); }
                    }
                    /*这是块长不满足a1出现越界的算法*/
                    else {

                        for (int N = a1.length - i; N > 0; N--) {
                            /*加分时的特殊情况算法*/
                            /*先判断不影响是否停止的逻辑a2的头位置*/
                            if (a1_index == 0 ) {
                                grace = grace + 1;
                            }
                            /*   下一级下标越界说明当前下标为尾数匹配成功，分数额外加一并比较分数离开
                             * 特殊情况是a2只有一位的情况，头尾同时匹配，算法同样满足
                             * */
                            if (a1_index + 1 == a1.length) {
                                grace = grace + 1;
                                if (grace > max[i]) {
                                    max[i] = max[i]=new Integer(grace);;
                                }
                                break;
                            } else {
                                if(a2_index+1 ==a2.length){
                                    if(grace>max[i]){max[i]=new Integer(grace);}
                                    break;
                                }
                                /*如果下一位不匹配直接比较分数并离开*/;
                                if(a1[a1_index+1].equals(a2[a2_index+1])){
                                    /*否则即为可以进行下一级计算*/
                                    a1_index=a1_index+1;
                                    a2_index=a2_index+1;
                                    if(a1_index==n+i)
                                    {
                                        if (grace > max[i]) {
                                            max[i] =  new Integer(grace);
                                        }
                                        break;
                                    }
                                    grace=grace+1;
                                }
                                   /* else{
                                        if(grace>max[i]){max[i]=grace;}
                                        break;}*/
                                }

                            }

                        if (grace > max[i]) {
                            max[i] =  new Integer(grace);
                        }
                    }
                }
            }
        }
        return max;
    }

    @Override
    public Map<String,Object> processDemo(Map<String, String[]> strMap,int n) {
        Map<String,Object> sum=new HashMap<>();
        /*对比值*/
        String[] a1=strMap.get("strb");
        /*基准值*/
        String[] b1=strMap.get("stra");
        Map<String,Object> map=new HashMap<>();
        /*map中的"sum"总和，"integer"为integer数组的String形式*/
        /*传入 基准值字符串stra 匹配值字符串stab 滑块数n进行计算*/
        sum=processCount(a1,b1,n);

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
    public Map<String,Object> processCount(String[] a1, String[] a2, int n) {

        Map<String,Object> sum=new HashMap<>();
        BigDecimal sum1=new BigDecimal(0);
        /*为了防止空指针异常,将结尾的特殊符号置入*/
        if(a1[0]==null){
          a1=new String[2];
          a1[0]="*";
          a1[1]="and";
        }
        if(a2[0]==null){
            a2=new String[2];
            a2[0]="*";
            a2[1]="and";
        }
        /*将收到的带符号的字符串，转化为需要截取的字符串并且避免下标越界*/
        int number=-1;
        for( number=0;number<a1.length;number++){
            if (a1[number].equals("and")){
                break;
            }
        }
        String [] a=new String[number];
        for( number=0;number<a.length;number++){
            a[number]=a1[number];
        }

        for( number=0;number<a2.length;number++){
            if (a2[number].equals("and")){
                break;
            }
        }
        String [] b=new String[number];
        for( number=0;number<b.length;number++){
            b[number]=a2[number];
        }
        /*processMaxSum最大值计算 aSummax为当前传入两个字符串理论最大值*/
        Integer[] aSumMax=processMaxSum(a.length,b.length,n);
        /*sSum为实际值*/
        Integer[] aSum=processSum(a,b,n);
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
        }
        return sum;
    }


}


