package com.dao.entity.mock;

import java.io.Serializable;
import java.math.BigDecimal;

public class MockTmessage implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mock_tmessage.datem
     *
     * @mbg.generated
     */
    private String datem;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mock_tmessage.tname
     *
     * @mbg.generated
     */
    private String tname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mock_tmessage.num
     *
     * @mbg.generated
     */
    private BigDecimal num;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mock_tmessage.tota
     *
     * @mbg.generated
     */
    private BigDecimal tota;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mock_tmessage.diff
     *
     * @mbg.generated
     */
    private BigDecimal diff;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table mock_tmessage
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mock_tmessage.datem
     *
     * @return the value of mock_tmessage.datem
     *
     * @mbg.generated
     */
    public String getDatem() {
        return datem;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mock_tmessage.datem
     *
     * @param datem the value for mock_tmessage.datem
     *
     * @mbg.generated
     */
    public void setDatem(String datem) {
        this.datem = datem == null ? null : datem.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mock_tmessage.tname
     *
     * @return the value of mock_tmessage.tname
     *
     * @mbg.generated
     */
    public String getTname() {
        return tname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mock_tmessage.tname
     *
     * @param tname the value for mock_tmessage.tname
     *
     * @mbg.generated
     */
    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mock_tmessage.num
     *
     * @return the value of mock_tmessage.num
     *
     * @mbg.generated
     */
    public BigDecimal getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mock_tmessage.num
     *
     * @param num the value for mock_tmessage.num
     *
     * @mbg.generated
     */
    public void setNum(BigDecimal num) {
        this.num = num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mock_tmessage.tota
     *
     * @return the value of mock_tmessage.tota
     *
     * @mbg.generated
     */
    public BigDecimal getTota() {
        return tota;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mock_tmessage.tota
     *
     * @param tota the value for mock_tmessage.tota
     *
     * @mbg.generated
     */
    public void setTota(BigDecimal tota) {
        this.tota = tota;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mock_tmessage.diff
     *
     * @return the value of mock_tmessage.diff
     *
     * @mbg.generated
     */
    public BigDecimal getDiff() {
        return diff;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mock_tmessage.diff
     *
     * @param diff the value for mock_tmessage.diff
     *
     * @mbg.generated
     */
    public void setDiff(BigDecimal diff) {
        this.diff = diff;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mock_tmessage
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", datem=").append(datem);
        sb.append(", tname=").append(tname);
        sb.append(", num=").append(num);
        sb.append(", tota=").append(tota);
        sb.append(", diff=").append(diff);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}