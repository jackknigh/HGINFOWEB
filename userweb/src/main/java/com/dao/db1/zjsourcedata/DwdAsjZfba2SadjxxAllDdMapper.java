package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.DwdAsjZfba2SadjxxAllDd;

import java.util.List;

public interface DwdAsjZfba2SadjxxAllDdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_asj_zfba2_sadjxx_all_dd
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_asj_zfba2_sadjxx_all_dd
     *
     * @mbg.generated
     */
    int insert(DwdAsjZfba2SadjxxAllDd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_asj_zfba2_sadjxx_all_dd
     *
     * @mbg.generated
     */
    DwdAsjZfba2SadjxxAllDd selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_asj_zfba2_sadjxx_all_dd
     *
     * @mbg.generated
     */
    List<DwdAsjZfba2SadjxxAllDd> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_asj_zfba2_sadjxx_all_dd
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DwdAsjZfba2SadjxxAllDd record);
}