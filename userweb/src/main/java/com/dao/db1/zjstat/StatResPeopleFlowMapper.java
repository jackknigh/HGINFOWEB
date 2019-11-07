package com.dao.db1.zjstat;

import com.dao.entity.zjstat.StatResPeopleFlow;
import com.dao.entity.zjstat.StatResPoepleFlow;

import java.util.List;
import java.util.Map;

public interface StatResPeopleFlowMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_res_people_flow
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_res_people_flow
     *
     * @mbg.generated
     */
    int insert(StatResPeopleFlow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_res_people_flow
     *
     * @mbg.generated
     */
    StatResPeopleFlow selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_res_people_flow
     *
     * @mbg.generated
     */
    List<StatResPeopleFlow> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stat_res_people_flow
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(StatResPeopleFlow record);

    List<?> statMain(Map<String, Object> queryMap);

    List<?> statMainDeep1(Map<String, Object> queryMap);

    List<StatResPoepleFlow> listStatData(Map<String, Object> queryMap);
}