package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.LdDwdGjMhjcgHzxxDs;

import java.util.List;

public interface LdDwdGjMhjcgHzxxDsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_mhjcg_hzxx_ds
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_mhjcg_hzxx_ds
     *
     * @mbg.generated
     */
    int insert(LdDwdGjMhjcgHzxxDs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_mhjcg_hzxx_ds
     *
     * @mbg.generated
     */
    LdDwdGjMhjcgHzxxDs selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_mhjcg_hzxx_ds
     *
     * @mbg.generated
     */
    List<LdDwdGjMhjcgHzxxDs> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_mhjcg_hzxx_ds
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LdDwdGjMhjcgHzxxDs record);
}