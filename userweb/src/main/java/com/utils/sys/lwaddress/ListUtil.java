package com.utils.sys.lwaddress;

import com.dao.entity.lwaddress.Base_addr;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    /**
     * 将一个集合分成若干个小集合
     */
    public static List<List<Base_addr>> splitList(List<Base_addr> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }

        List<List<Base_addr>> result = new ArrayList<>();


        int size = list.size();
        int count = (size + len - 1) / len;


        for (int i = 0; i < count; i++) {
            List<Base_addr> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }
}
