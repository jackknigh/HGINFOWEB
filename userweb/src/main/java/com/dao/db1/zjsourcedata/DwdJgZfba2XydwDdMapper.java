package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.DwdJgZfba2XydwDd;

import java.util.List;

public interface DwdJgZfba2XydwDdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_jg_zfba2_xydw_dd
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_jg_zfba2_xydw_dd
     *
     * @mbg.generated
     */
    int insert(DwdJgZfba2XydwDd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_jg_zfba2_xydw_dd
     *
     * @mbg.generated
     */
    DwdJgZfba2XydwDd selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_jg_zfba2_xydw_dd
     *
     * @mbg.generated
     */
    List<DwdJgZfba2XydwDd> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_jg_zfba2_xydw_dd
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DwdJgZfba2XydwDd record);
}