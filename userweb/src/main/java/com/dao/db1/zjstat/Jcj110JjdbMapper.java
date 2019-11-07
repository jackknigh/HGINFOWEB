package com.dao.db1.zjstat;

import com.dao.entity.zjstat.Jcj110Jjdb;
import com.dto.pojo.stata.StatPGISDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Jcj110JjdbMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jcj110_jjdb
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jcj110_jjdb
     *
     * @mbg.generated
     */
    int insert(Jcj110Jjdb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jcj110_jjdb
     *
     * @mbg.generated
     */
    Jcj110Jjdb selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jcj110_jjdb
     *
     * @mbg.generated
     */
    List<Jcj110Jjdb> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jcj110_jjdb
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Jcj110Jjdb record);

    List<Jcj110Jjdb> search(Map<String, Object> queryMap);

    Integer sum(Map<String, Object> queryMap);

    List<Jcj110Jjdb> list(Map<String, Object> queryMap);

    int updateStateBatchByIds(@Param("shztdm") String shztdm, @Param("list") List<String> ids);

    int deleteByPrimaryKeys(List<String> ids);

    List<?> statJcjTotalByDepCode(@Param("depCodes") List<String> depCodes,
                                  @Param("beginDate") String beginDate,
                                  @Param("endDate") String endDate,
                                  @Param("preCount") int preCount);

    List<?> statJcjTotalByDate(@Param("depCodes") List<String> depCodes,
                               @Param("beginDate") String beginDate,
                               @Param("endDate") String endDate,
                               @Param("preCount") int preCount);

    List<?> statJcjLbByDay(@Param("depCode") String depCode,
                           @Param("lbdms") List<String> lbdms,
                           @Param("table") String table,
                           @Param("beginDate") String beginDate,
                           @Param("endDate") String endDate,
                           @Param("lbbhField") String lbbhField,
                           @Param("dwdmField") String dwdmField,
                           @Param("lbSize") Integer lbSize);

    List<?> statJcjLbByDepCode(@Param("depCode") String depCode,
                               @Param("beginDate") String beginDate,
                               @Param("endDate") String endDate,
                               @Param("lbdm") String lbdm,
                               @Param("lbType") String lbType);

    List<?> statJcjLxByDepCode(@Param("depCode") String depCode,
                               @Param("beginDate") String beginDate,
                               @Param("endDate") String endDate,
                               @Param("lbbhField") String lbbhField,
                               @Param("lbdm") String lbdm,
                               @Param("lxdms") List<String> lxdms,
                               @Param("table") String table,
                               @Param("lxbhField") String lxbhField,
                               @Param("dwdmField") String dwdmField);

    List<StatPGISDTO> statJcjTotalPGIS(@Param("depCodes") List<String> depCodes,
                                       @Param("beginDate") String beginDate,
                                       @Param("endDate") String endDate,
                                       @Param("tbBeginDate") String tbBeginDate,
                                       @Param("tbEndDate") String tbEndDate,
                                       @Param("lbbhs") List<String> lbbhs,
                                       @Param("lxbhs") List<String> lxbhs,
                                       @Param("table") String table,
                                       @Param("lxbhField") String lxbhField,
                                       @Param("lbbhField") String lbbhField,
                                       @Param("dwdmField") String dwdmField,
                                       @Param("preCount") int preCount);

    List<?> statJcjLb(@Param("depCode") String depCode,
                      @Param("beginDate") String beginDate,
                      @Param("endDate") String endDate,
                      @Param("lbdms") List<String> lbdms,
                      @Param("table") String table,
                      @Param("lbbhField") String lbbhField,
                      @Param("dwdmField") String dwdmField);

    List<?> statJcj(@Param("depCode") String depCode,
                    @Param("beginDate") Date beginDate,
                    @Param("endDate") Date endDate);

    List<?> statJcjByDate(@Param("depCode") String depCode,
                          @Param("beginDate") Date beginDate,
                          @Param("endDate") Date endDate);

    List<?> statJcjByDepCode(@Param("depCodes") List<String> depCodes,
                             @Param("beginDate") Date beginDate,
                             @Param("endDate") Date endDate,
                             @Param("preCount") int preCount);

    List<?> statJcjByLb(@Param("depCode") String depCode,
                        @Param("beginDate") Date beginDate,
                        @Param("endDate") Date endDate);

    List<?> statAjxxByLx(@Param("depCode") String depCode,
                         @Param("beginDate") Date beginDate,
                         @Param("endDate") Date endDate);

    List<?> statAjxxByDepCode(@Param("depCodes") List<String> depCodes,
                              @Param("beginDate") Date beginDate,
                              @Param("endDate") Date endDate,
                              @Param("preCount") int preCount);

    List<?> statAjxx(@Param("depCode") String depCode,
                     @Param("beginDate") Date beginDate,
                     @Param("endDate") Date endDate);
}
