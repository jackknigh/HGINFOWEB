package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.LdDwdGjKdJbxxDs;

import java.util.List;

public interface LdDwdGjKdJbxxDsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_kd_jbxx_ds
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_kd_jbxx_ds
     *
     * @mbg.generated
     */
    int insert(LdDwdGjKdJbxxDs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_kd_jbxx_ds
     *
     * @mbg.generated
     */
    LdDwdGjKdJbxxDs selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_kd_jbxx_ds
     *
     * @mbg.generated
     */
    List<LdDwdGjKdJbxxDs> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_kd_jbxx_ds
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LdDwdGjKdJbxxDs record);
}