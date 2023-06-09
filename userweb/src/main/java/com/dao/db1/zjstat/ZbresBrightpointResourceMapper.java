package com.dao.db1.zjstat;

import com.dao.entity.zjstat.Jcj110Jjdb;
import com.dao.entity.zjstat.ZbresBrightpointResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZbresBrightpointResourceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zbres_brightpoint_resource
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zbres_brightpoint_resource
     *
     * @mbg.generated
     */
    int insert(ZbresBrightpointResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zbres_brightpoint_resource
     *
     * @mbg.generated
     */
    ZbresBrightpointResource selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zbres_brightpoint_resource
     *
     * @mbg.generated
     */
    List<ZbresBrightpointResource> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zbres_brightpoint_resource
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ZbresBrightpointResource record);

    List<?> selectDetailList(@Param("depCode") String depCode, @Param("zbAssessMainRefid") String zbAssessMainRefid, @Param("zbBrightpointRefid") String zbBrightpointRefid,
                             @Param("statUnit") String statUnit, @Param("statDate") String statDate, @Param("statCycle") String statCycle);

    void insertBatch(@Param("list") List<ZbresBrightpointResource> zbresBrightpointResources);

    void deleteByCycles(@Param("zbAssessMainId") String zbAssessMainId, @Param("depCode") String depCode, @Param("statCycle") String statCycle, @Param("statUnit") String statUnit);

    List<Jcj110Jjdb> selectBrightpointResource(@Param("zbAssessMainRefid") String zbAssessMainRefid, @Param("zbBrightpointRefid") String zbBrightpointRefid,
                                               @Param("statUnit") String statUnit, @Param("statCycle") String statCycle, @Param("depCode") String depCode);
}