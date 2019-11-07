package com.utils.sys;


import com.dao.entity.sys.constants.Constants;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by CuiL on 2018-12-26.
 */
public class PopedomUtils {

    public static byte[] operatorAccountPopedomAnd(byte[] operatorPopedom, byte[] accountPopedom) {
        byte[] bytes = new byte[Constants.MAXFRAMELEN];
        if (TextUtils.isEmpty(operatorPopedom) || (TextUtils.isEmpty(accountPopedom)))
            return null;
        for (int i = 0; i <= operatorPopedom.length - 1; i++) {
            bytes[i] = (byte) (operatorPopedom[i] & accountPopedom[i]);
        }
        return bytes;
    }

    public static String byteToBinaryString(byte[] bytes) {
        String _ret = "";
        for (byte _b : bytes) {
            if (_b == 0)
                _ret = _ret + "00000000";
            else
                _ret = _ret + StringUtils.leftPad(Integer.toBinaryString(_b), 8, '0');
        }
        return _ret;
    }

    public static boolean checkPopedom(byte[] popedom, Integer funcId) {
        return false;
    }

    public static boolean checkPopedom(String popedom, Integer funcId) {
        if(popedom.substring(funcId,funcId+1).equals("1"))
            return true;
        else
            return false;
    }
}
