package com.dao.db1.mock;

import com.dao.entity.mock.MockGuest;

import java.util.List;

public interface MockGuestMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mock_guest
     *
     * @mbg.generated
     */
    int insert(MockGuest record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mock_guest
     *
     * @mbg.generated
     */
    List<MockGuest> selectAll();
}