package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.DwdWpZfba2XzcfjdsDd;

import java.util.List;

public interface DwdWpZfba2XzcfjdsDdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_xzcfjds_dd
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_xzcfjds_dd
     *
     * @mbg.generated
     */
    int insert(DwdWpZfba2XzcfjdsDd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_xzcfjds_dd
     *
     * @mbg.generated
     */
    DwdWpZfba2XzcfjdsDd selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_xzcfjds_dd
     *
     * @mbg.generated
     */
    List<DwdWpZfba2XzcfjdsDd> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_xzcfjds_dd
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DwdWpZfba2XzcfjdsDd record);
}