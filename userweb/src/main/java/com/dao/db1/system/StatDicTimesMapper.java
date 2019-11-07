package com.dao.db1.system;

import com.dao.db1.system.entity.StatDicTimes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatDicTimesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_dic_times
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_dic_times
     *
     * @mbg.generated
     */
    int insert(StatDicTimes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_dic_times
     *
     * @mbg.generated
     */
    StatDicTimes selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_dic_times
     *
     * @mbg.generated
     */
    List<StatDicTimes> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_dic_times
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(StatDicTimes record);

    void deleteByPrimaryKeys(List<String> ids);

    List<StatDicTimes> search(@Param("depCode") String depCode, @Param("dicType") String dicType);

    List<StatDicTimes> searchInsideTime(String depCode);

    List<StatDicTimes> searchSpecialTime(String depCode);
}