package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.DwdRkZfba2QzcsbaAllDd;

import java.util.List;

public interface DwdRkZfba2QzcsbaAllDdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_rk_zfba2_qzcsba_all_dd
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_rk_zfba2_qzcsba_all_dd
     *
     * @mbg.generated
     */
    int insert(DwdRkZfba2QzcsbaAllDd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_rk_zfba2_qzcsba_all_dd
     *
     * @mbg.generated
     */
    DwdRkZfba2QzcsbaAllDd selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_rk_zfba2_qzcsba_all_dd
     *
     * @mbg.generated
     */
    List<DwdRkZfba2QzcsbaAllDd> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_rk_zfba2_qzcsba_all_dd
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DwdRkZfba2QzcsbaAllDd record);
}