package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.LdDwdGjGlcpXxDs;

import java.util.List;

public interface LdDwdGjGlcpXxDsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_glcp_xx_ds
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_glcp_xx_ds
     *
     * @mbg.generated
     */
    int insert(LdDwdGjGlcpXxDs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_glcp_xx_ds
     *
     * @mbg.generated
     */
    LdDwdGjGlcpXxDs selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_glcp_xx_ds
     *
     * @mbg.generated
     */
    List<LdDwdGjGlcpXxDs> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_glcp_xx_ds
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LdDwdGjGlcpXxDs record);
}