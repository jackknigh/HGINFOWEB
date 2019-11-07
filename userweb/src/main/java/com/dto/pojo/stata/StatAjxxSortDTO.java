package com.dto.pojo.stata;

import java.io.Serializable;
import java.util.List;

public class StatAjxxSortDTO implements Serializable, Comparable {

    private int sum;
    private List<StatAjxxByDepCodeDTO> list;

    public int getSum() {
        return sum;
    }

    public List<StatAjxxByDepCodeDTO> getList() {
        return list;
    }

    public void setList(List<StatAjxxByDepCodeDTO> list) {
        this.list = list;
        for (StatAjxxByDepCodeDTO dto : list) {
            sum += dto.getStatCount();
        }
    }

    @Override
    public int compareTo(Object o) {
        StatAjxxSortDTO dto = (StatAjxxSortDTO) o;
        if (this.sum > dto.sum) {
            return 1;
        } else if (this.sum < dto.sum) {
            return -1;
        }
        return 0;
    }
}
