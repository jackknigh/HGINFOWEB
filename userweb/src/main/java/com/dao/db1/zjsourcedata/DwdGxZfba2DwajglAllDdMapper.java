package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.DwdGxZfba2DwajglAllDd;

import java.util.List;

public interface DwdGxZfba2DwajglAllDdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_gx_zfba2_dwajgl_all_dd
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_gx_zfba2_dwajgl_all_dd
     *
     * @mbg.generated
     */
    int insert(DwdGxZfba2DwajglAllDd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_gx_zfba2_dwajgl_all_dd
     *
     * @mbg.generated
     */
    DwdGxZfba2DwajglAllDd selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_gx_zfba2_dwajgl_all_dd
     *
     * @mbg.generated
     */
    List<DwdGxZfba2DwajglAllDd> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_gx_zfba2_dwajgl_all_dd
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DwdGxZfba2DwajglAllDd record);
}