package com.dao.db1.zjstat;

import com.dao.entity.zjstat.ZbCalculationConfig;

import java.util.List;

public interface ZbCalculationConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_calculation_config
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_calculation_config
     *
     * @mbg.generated
     */
    int insert(ZbCalculationConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_calculation_config
     *
     * @mbg.generated
     */
    ZbCalculationConfig selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_calculation_config
     *
     * @mbg.generated
     */
    List<ZbCalculationConfig> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_calculation_config
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ZbCalculationConfig record);

    int deleteByPrimaryKeys(List<String> ids);
}