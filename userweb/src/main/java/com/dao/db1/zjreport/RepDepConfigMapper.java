package com.dao.db1.zjreport;

import com.dao.entity.zjreport.RepDepConfig;
import com.dto.pojo.stata.StatAjxxByDepCodeDTO;
import com.dto.pojo.stata.StatJcjDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RepDepConfigMapper {

    int deleteByPrimaryKey(String id);


    int insert(RepDepConfig record);


    RepDepConfig selectByPrimaryKey(String id);

    RepDepConfig selectByDepCode(String depCodeLike);


    List<RepDepConfig> selectAll(@Param("depCodeLike") String depCodeLike, @Param("depCode") String depCode);

    List<RepDepConfig> selectAllRepCode();

    int updateByPrimaryKey(RepDepConfig record);

    List<RepDepConfig> selectAllChild(String depCode);

    List<StatAjxxByDepCodeDTO> selectByDepCodes(@Param("depCodes") List<String> depCodes,
                                                @Param("fieldId") String fieldId,
                                                @Param("clauses") List<String> clauses);

    List<StatAjxxByDepCodeDTO> selectByDepCodeAndDate(@Param("depCodes") List<String> depCodes,
                                                      @Param("fieldId") String fieldId,
                                                      @Param("clauses") List<String> clauses,
                                                      @Param("beginDate") String beginDate,
                                                      @Param("endDate") String endDate);

    List<StatAjxxByDepCodeDTO> selectByTargetAndDate(@Param("depCode") String depCode,
                                                     @Param("fieldId") String fieldId,
                                                     @Param("clauses") List<String> clauses,
                                                     @Param("beginDate") String beginDate,
                                                     @Param("endDate") String endDate);

    List<StatAjxxByDepCodeDTO> selectByTargets(@Param("depCode") String depCode,
                                               @Param("fieldId") String fieldId,
                                               @Param("clauses") List<String> clauses);

    List<StatAjxxByDepCodeDTO> selectByDepCodeAndHjd(@Param("depCodes") List<String> depCodes,
                                                     @Param("fieldId") String fieldId,
                                                     @Param("clauses") List<String> clauses);

    List<StatJcjDTO> selectDate(@Param("depCodes") List<String> depCodes,
                                @Param("beginDate") String beginDate,
                                @Param("endDate") String endDate);

    List<StatJcjDTO> selectDateAndLb(@Param("lbdms") List<String> lbdms,
                                     @Param("beginDate") String beginDate,
                                     @Param("endDate") String endDate);

    List<StatJcjDTO> selectLx(String lbdm);

    List<StatJcjDTO> selectFklx(String lbdm);

    List<StatJcjDTO> selectLb(@Param("lbdms") List<String> lbdms);

    List<RepDepConfig>  selectByDepCodeLength(@Param("depCode") String depCode);
}