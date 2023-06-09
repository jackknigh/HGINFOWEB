package com.dao.db1.zjstat;

import com.dao.entity.zjstat.StatPerpleSrcZzrk;

import java.util.List;
import java.util.Map;

public interface StatPerpleSrcZzrkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_perple_src_zzrk
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_perple_src_zzrk
     *
     * @mbg.generated
     */
    int insert(StatPerpleSrcZzrk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_perple_src_zzrk
     *
     * @mbg.generated
     */
    StatPerpleSrcZzrk selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_perple_src_zzrk
     *
     * @mbg.generated
     */
    List<StatPerpleSrcZzrk> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_perple_src_zzrk
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(StatPerpleSrcZzrk record);

    List<StatPerpleSrcZzrk> search(Map<String, Object> queryMap);

    Integer sum(Map<String, Object> queryMap);

    StatPerpleSrcZzrk selectBySfzh(String sfzh);
}