package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.DwdJgZfba2JgxxDd;

import java.util.List;

public interface DwdJgZfba2JgxxDdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_jg_zfba2_jgxx_dd
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_jg_zfba2_jgxx_dd
     *
     * @mbg.generated
     */
    int insert(DwdJgZfba2JgxxDd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_jg_zfba2_jgxx_dd
     *
     * @mbg.generated
     */
    DwdJgZfba2JgxxDd selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_jg_zfba2_jgxx_dd
     *
     * @mbg.generated
     */
    List<DwdJgZfba2JgxxDd> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dwd_jg_zfba2_jgxx_dd
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DwdJgZfba2JgxxDd record);
}