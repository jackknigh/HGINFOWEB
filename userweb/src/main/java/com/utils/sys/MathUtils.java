package com.utils.sys;

import java.math.BigDecimal;

/**
 * Created by CuiL on 2018-12-09.
 */
public class MathUtils {

    public static Integer strAddInt(String arg0, String arg1) {
        Integer _arg0 = 0;
        Integer _arg1 = 0;
        if ((arg0 != null) && !(arg0.equalsIgnoreCase(""))
                && !(arg0.equalsIgnoreCase("null")))
            _arg0 = Integer.valueOf(arg0);
        if ((arg1 != null) && !(arg1.equalsIgnoreCase(""))
                && !(arg1.equalsIgnoreCase("null")))
            _arg1 = Integer.valueOf(arg1);

        return _arg0 + _arg1;
    }

    public static BigDecimal[]  getEmptyBigDecimalList(Integer length,BigDecimal defaultVal){
        BigDecimal[] res = new BigDecimal[length];
        for(int i =0;i<res.length;i++){
            res[i] = defaultVal;
        }
        return res;
    }

    public static String strAddStr(String arg0, String arg1) {
        return strAddInt(arg0, arg1).toString();
    }

    public static String strAddStr(String... args) {
        String res = "0";
        for (String _arg : args) {
            res = strAddInt(res, _arg).toString();
        }
        return res;
    }

    /**
     * 转换为负数
     */
    public static String strNeg(String arg0) {
        Integer _arg0 = 0;
        if ((arg0 != null) && !(arg0.equalsIgnoreCase(""))
                && !(arg0.equalsIgnoreCase("null")))
            _arg0 = 0 - Integer.valueOf(arg0);
        return _arg0.toString();
    }

    public static BigDecimal getMax(BigDecimal value1, BigDecimal value2){
        if(value1 == null) value1 = new BigDecimal(0);
        if(value2 == null) value2 = new BigDecimal(0);
       return value1.compareTo(value2)>0 ?value1:value2;
    }
    public static BigDecimal getMin(BigDecimal value1, BigDecimal value2){
        if(value1 == null) value1 = new BigDecimal(0);
        if(value2 == null) value2 = new BigDecimal(0);
       return value1.compareTo(value2)<0 ?value1:value2;
    }
}
