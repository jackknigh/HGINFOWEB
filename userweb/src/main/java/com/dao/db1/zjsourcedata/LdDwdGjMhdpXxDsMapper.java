package com.dao.db1.zjsourcedata;

import com.dao.entity.zjsouredata.LdDwdGjMhdpXxDs;

import java.util.List;

public interface LdDwdGjMhdpXxDsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_mhdp_xx_ds
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_mhdp_xx_ds
     *
     * @mbg.generated
     */
    int insert(LdDwdGjMhdpXxDs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_mhdp_xx_ds
     *
     * @mbg.generated
     */
    LdDwdGjMhdpXxDs selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_mhdp_xx_ds
     *
     * @mbg.generated
     */
    List<LdDwdGjMhdpXxDs> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ld_dwd_gj_mhdp_xx_ds
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LdDwdGjMhdpXxDs record);
}