package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.DwdWpZfba2QsyjsAllDd;

import java.util.List;

public interface DwdWpZfba2QsyjsAllDdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_qsyjs_all_dd
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_qsyjs_all_dd
     *
     * @mbg.generated
     */
    int insert(DwdWpZfba2QsyjsAllDd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_qsyjs_all_dd
     *
     * @mbg.generated
     */
    DwdWpZfba2QsyjsAllDd selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_qsyjs_all_dd
     *
     * @mbg.generated
     */
    List<DwdWpZfba2QsyjsAllDd> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_qsyjs_all_dd
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DwdWpZfba2QsyjsAllDd record);
}