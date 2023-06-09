package com.dao.db1.zjstat;

import com.dao.entity.zjstat.ZbCalculation;

import java.util.List;

public interface ZbCalculationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_calculation
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_calculation
     *
     * @mbg.generated
     */
    int insert(ZbCalculation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_calculation
     *
     * @mbg.generated
     */
    ZbCalculation selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_calculation
     *
     * @mbg.generated
     */
    List<ZbCalculation> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_calculation
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ZbCalculation record);

    int deleteByPrimaryKeys(List<String> ids);
}