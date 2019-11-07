package com.dao.db1.zjstat;

import com.dao.entity.zjsouredata.DwdAsjZfba2AjxxAllDd;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DwdAsjZfba2AjxxAllDdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_asj_zfba2_ajxx_all_dd
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_asj_zfba2_ajxx_all_dd
     *
     * @mbg.generated
     */
    int insert(DwdAsjZfba2AjxxAllDd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_asj_zfba2_ajxx_all_dd
     *
     * @mbg.generated
     */
    DwdAsjZfba2AjxxAllDd selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_asj_zfba2_ajxx_all_dd
     *
     * @mbg.generated
     */
    List<DwdAsjZfba2AjxxAllDd> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_asj_zfba2_ajxx_all_dd
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DwdAsjZfba2AjxxAllDd record);

    List<?> aybh();

    List<?> aymc(@Param("aybhs") List<String> aybhs);

    List<?> ageList();

    List<?> nationList();

    List<?> statAjxxTotalByTarget1(@Param("depCode") String depCode,
                                   @Param("beginDate") String beginDate,
                                   @Param("endDate") String endDate,
                                   @Param("strings") List<String> strings);

    List<?> statAjxxTotalByTarget2(@Param("depCode") String depCode,
                                   @Param("beginDate") String beginDate,
                                   @Param("endDate") String endDate,
                                   @Param("target") String target,
                                   @Param("strings") List<String> strings);

    List<?> statAjxxTotalByDepCode1(@Param("depCodes") List<String> depCodes,
                                    @Param("beginDate") String beginDate,
                                    @Param("endDate") String endDate,
                                    @Param("strings") List<String> strings,
                                    @Param("preCount") Integer preCount,
                                    @Param("aymcSize") Integer aymcSize);

    List<?> statAjxxTotalByDepCode2(@Param("depCodes") List<String> depCode,
                                    @Param("beginDate") String beginDate,
                                    @Param("endDate") String endDate,
                                    @Param("target") String target,
                                    @Param("strings") List<String> strings,
                                    @Param("preCount") Integer preCount);

    List<?> statAjxxTargetByDate1(@Param("depCode") String depCode,
                                  @Param("beginDate") String beginDate,
                                  @Param("endDate") String endDate,
                                  @Param("strings") List<String> strings,
                                  @Param("aymcSize") Integer aymcSize);

    List<?> statAjxxTargetByDate2(@Param("depCode") String depCode,
                                  @Param("beginDate") String beginDate,
                                  @Param("endDate") String endDate,
                                  @Param("target") String target,
                                  @Param("strings") List<String> strings);

    List<?> statAjxxTotalByDate1(@Param("depCodes") List<String> depCode,
                                 @Param("beginDate") String beginDate,
                                 @Param("endDate") String endDate,
                                 @Param("preCount") Integer preCount,
                                 @Param("strings") List<String> strings,
                                 @Param("aymcSize") Integer aymcSize);

    List<?> statAjxxTotalByDate2(@Param("depCodes") List<String> depCode,
                                 @Param("beginDate") String beginDate,
                                 @Param("endDate") String endDate,
                                 @Param("preCount") Integer preCount,
                                 @Param("target") String target,
                                 @Param("strings") List<String> strings);

    List<?> statAjxxTotalByHjd1(@Param("depCodes") List<String> depCodes,
                                @Param("beginDate") String beginDate,
                                @Param("endDate") String endDate,
                                @Param("strings") List<String> strings,
                                @Param("preCount") Integer preCount,
                                @Param("aymcSize") Integer aymcSize);

    List<?> statAjxxTotalByHjd2(@Param("depCodes") List<String> depCode,
                                @Param("beginDate") String beginDate,
                                @Param("endDate") String endDate,
                                @Param("target") String target,
                                @Param("strings") List<String> strings,
                                @Param("preCount") Integer preCount);
}