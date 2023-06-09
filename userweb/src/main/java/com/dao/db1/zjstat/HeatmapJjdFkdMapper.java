package com.dao.db1.zjstat;

import com.dao.db1.zjstat.entity.HeatmapJjdFkd;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeatmapJjdFkdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table heatmap_jjd_fkd
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table heatmap_jjd_fkd
     *
     * @mbg.generated
     */
    int insert(HeatmapJjdFkd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table heatmap_jjd_fkd
     *
     * @mbg.generated
     */
    HeatmapJjdFkd selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table heatmap_jjd_fkd
     *
     * @mbg.generated
     */
    List<HeatmapJjdFkd> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table heatmap_jjd_fkd
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(HeatmapJjdFkd record);

    List<HeatmapJjdFkd> selectJjdByJwd(@Param("leftBottom") String leftBottom,
                                       @Param("rightTop") String rightTop,
                                       @Param("displayLevel") String displayLevel);

    List<HeatmapJjdFkd> selectLdllgjByJwd(@Param("leftBottom") String leftBottom,
                                          @Param("rightTop") String rightTop,
                                          @Param("displayLevel") String displayLevel);

    List<HeatmapJjdFkd> selectXcllgjByJwd(@Param("leftBottom") String leftBottom,
                                          @Param("rightTop") String rightTop,
                                          @Param("displayLevel") String displayLevel);

    void insertBatch(@Param("tableName") String tableName,
                     @Param("list") List<?> resDays);

    void disposeJwd(@Param("tableName") String tableName,
                    @Param("displayLevel") String displayLevel,
                    @Param("originalTable") String originalTable);
}