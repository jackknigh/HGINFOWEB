package com.utils.sys;

/**
 * Created by CuiL on 2018-12-09.
 */
public class DepCodeUtils {

    public static String getFirstChildDepCodeLike(String depCode){
        String retString =getAllChildDepCode(depCode)+"%%";
        while (retString.length() < 12) {
            retString = retString +"00";
        }
        return retString.replace("%%","%");
    }

    /***
    *<p>方法:getChildDepCodeLikeAndDeep 获取到一定级别的子机构 </p>
    *<ul>
     *<li> @param likeDepCode 百分号结尾的depCode，如3301%</li>
     *<li> @param deep 深度   2=市  3=区  4=派出所  5=科室</li>
    *<li>@return java.lang.String  </li>
    *<li>@author CuiLiang </li>
    *<li>@date 2019-03-21 15:11  </li>
    *</ul>
    */
    public static String getChildDepCodeLikeAndDeep(String likeDepCode,Integer deep){
        if(deep ==5){
            return likeDepCode+"00";
        }
        else if(deep ==4){
            return likeDepCode+"0000";
        }
        else if(deep ==3){
            return likeDepCode+"000000";
        }
        else if(deep ==2){
            return likeDepCode+"00000000";
        }
        return "getChildDepCodeLikeAndDeep error";
    }

    /***
    *<p>方法:getAllChildDepCodeLike 获取本机及全部子机构
     *  如330100000000，返回3301%</p>
    *<ul>
     *<li> @param depCode 机构代码</li>
    *<li>@return java.lang.String  </li>
    *</ul>
    */
    public static String getAllChildDepCodeLike(String depCode){
        return getAllChildDepCode(depCode)+"%";
    }

    public static String getAllChildDepCode(String depCode){
        if(depCode.substring(2,12).equalsIgnoreCase("0000000000"))
            return depCode.substring(0,2);
        else if(depCode.substring(4,12).equalsIgnoreCase("00000000"))
            return depCode.substring(0,4);
        else if(depCode.substring(6,12).equalsIgnoreCase("000000"))
            return depCode.substring(0,6);
        else if(depCode.substring(8,12).equalsIgnoreCase("0000"))
            return depCode.substring(0,8);
        else if(depCode.substring(10,12).equalsIgnoreCase("00"))
            return depCode.substring(0,10);
        else
            return depCode;
    }

    public static Integer getDepDeep(String depCode) {
        if(depCode.substring(2,12).equalsIgnoreCase("0000000000"))
            return 2;
        else if(depCode.substring(4,12).equalsIgnoreCase("00000000"))
            return 4;
        else if(depCode.substring(6,12).equalsIgnoreCase("000000"))
            return 6;
        else if(depCode.substring(8,12).equalsIgnoreCase("0000"))
            return 8;
        else if(depCode.substring(10,12).equalsIgnoreCase("00"))
            return 10;
        else
            return 12;
    }
}
