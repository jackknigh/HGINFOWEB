package com.dao.db1.zjstat;

import com.dao.entity.zjstat.ZbresBrightpointValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZbresBrightpointValueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zbres_brightpoint_value
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zbres_brightpoint_value
     *
     * @mbg.generated
     */
    int insert(ZbresBrightpointValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zbres_brightpoint_value
     *
     * @mbg.generated
     */
    ZbresBrightpointValue selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zbres_brightpoint_value
     *
     * @mbg.generated
     */
    List<ZbresBrightpointValue> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zbres_brightpoint_value
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ZbresBrightpointValue record);

    List<?> selectLikeDepCode(@Param("depCode") String depCode, @Param("zbAssessMainRefid") String zbAssessMainRefid, @Param("zbBrightpointRefid") String zbBrightpointRefid,
                              @Param("statUnit") String statUnit, @Param("statDate") String statDate, @Param("statCycle") String statCycle);

    List<?> selectByDepCode(@Param("depCode") String depCode, @Param("zbAssessMainRefid") String zbAssessMainRefid,
                            @Param("statUnit") String statUnit, @Param("statDate") String statDate, @Param("statCycle") String statCycle);

    void deleteAllData();

    void insertBatch(@Param("list") List<ZbresBrightpointValue> zbresBrightpointValues);

    void insertHisBatch(@Param("list") List<ZbresBrightpointValue> zbresBrightpointValues);

    List<ZbresBrightpointValue> stateSumDetailValue();

    void insertHis(ZbresBrightpointValue zbresBrightpointValue);
}