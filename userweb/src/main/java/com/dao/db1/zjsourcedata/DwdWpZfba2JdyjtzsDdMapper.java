package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.DwdWpZfba2JdyjtzsDd;

import java.util.List;

public interface DwdWpZfba2JdyjtzsDdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_jdyjtzs_dd
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_jdyjtzs_dd
     *
     * @mbg.generated
     */
    int insert(DwdWpZfba2JdyjtzsDd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_jdyjtzs_dd
     *
     * @mbg.generated
     */
    DwdWpZfba2JdyjtzsDd selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_jdyjtzs_dd
     *
     * @mbg.generated
     */
    List<DwdWpZfba2JdyjtzsDd> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_jdyjtzs_dd
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DwdWpZfba2JdyjtzsDd record);
}