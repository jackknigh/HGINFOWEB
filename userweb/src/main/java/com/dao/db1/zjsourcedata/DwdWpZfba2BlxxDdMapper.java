package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.DwdWpZfba2BlxxDd;

import java.util.List;

public interface DwdWpZfba2BlxxDdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_blxx_dd
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_blxx_dd
     *
     * @mbg.generated
     */
    int insert(DwdWpZfba2BlxxDd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_blxx_dd
     *
     * @mbg.generated
     */
    DwdWpZfba2BlxxDd selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_blxx_dd
     *
     * @mbg.generated
     */
    List<DwdWpZfba2BlxxDd> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_blxx_dd
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DwdWpZfba2BlxxDd record);
}