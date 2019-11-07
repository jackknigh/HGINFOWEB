package com.dao.db1.zjstat;

import com.dao.entity.zjstat.StatAjxx;

import java.util.List;
import java.util.Map;

public interface StatAjxxMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_ajxx
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_ajxx
     *
     * @mbg.generated
     */
    int insert(StatAjxx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_ajxx
     *
     * @mbg.generated
     */
    StatAjxx selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_ajxx
     *
     * @mbg.generated
     */
    List<StatAjxx> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_ajxx
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(StatAjxx record);

    List<StatAjxx> search(Map<String, Object> queryMap);

    Integer sum(Map<String, Object> queryMap);
}