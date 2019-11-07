package com.dao.db1.zjstat;

import com.dao.entity.zjstat.ZbCalculationConfigDetail;

import java.util.List;

public interface ZbCalculationConfigDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_calculation_config_detail
     *
     * @mbg.generated
     */
    int insert(ZbCalculationConfigDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_calculation_config_detail
     *
     * @mbg.generated
     */
    List<ZbCalculationConfigDetail> selectAll();

    List<ZbCalculationConfigDetail> selectByRefId(String refId);

    int updateByPrimaryKey(ZbCalculationConfigDetail record);

    int deleteByPrimaryKey(String id);
}