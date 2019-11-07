package com.dao.db1.zjstat;

import com.dao.entity.zjstat.ZbAssessDetail1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZbAssessDetail1Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_assess_detail1
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_assess_detail1
     *
     * @mbg.generated
     */
    int insert(ZbAssessDetail1 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_assess_detail1
     *
     * @mbg.generated
     */
    ZbAssessDetail1 selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_assess_detail1
     *
     * @mbg.generated
     */
    List<ZbAssessDetail1> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zb_assess_detail1
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ZbAssessDetail1 record);

    void deletebyRefId(List<String> ids);

    List<ZbAssessDetail1> selectByMainID(String mainID);

    int assessSum(@Param("field") String field,
                  @Param("table") String table,
                  @Param("fieldNameValue") String fieldNameValue,
                  @Param("depCode") String depCode,
                  @Param("statUnit") String statUnit,
                  @Param("timeField") String timeField,
                  @Param("depCodeField") String depCodeField);
}