package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.DwdWpZfba2WsxxAllDd;

import java.util.List;

public interface DwdWpZfba2WsxxAllDdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_wsxx_all_dd
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_wsxx_all_dd
     *
     * @mbg.generated
     */
    int insert(DwdWpZfba2WsxxAllDd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_wsxx_all_dd
     *
     * @mbg.generated
     */
    DwdWpZfba2WsxxAllDd selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_wsxx_all_dd
     *
     * @mbg.generated
     */
    List<DwdWpZfba2WsxxAllDd> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_wp_zfba2_wsxx_all_dd
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DwdWpZfba2WsxxAllDd record);
}