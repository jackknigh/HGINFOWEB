package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.DwdGxZfba2JaglAllDd;

import java.util.List;

public interface DwdGxZfba2JaglAllDdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_gx_zfba2_jagl_all_dd
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_gx_zfba2_jagl_all_dd
     *
     * @mbg.generated
     */
    int insert(DwdGxZfba2JaglAllDd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_gx_zfba2_jagl_all_dd
     *
     * @mbg.generated
     */
    DwdGxZfba2JaglAllDd selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_gx_zfba2_jagl_all_dd
     *
     * @mbg.generated
     */
    List<DwdGxZfba2JaglAllDd> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_gx_zfba2_jagl_all_dd
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DwdGxZfba2JaglAllDd record);
}