package com.dao.db1.decisionForm;

import com.dao.entity.decisionForm.OutCar;
import com.dao.entity.decisionForm.OutCarKey;

public interface OutCarMapper {

    OutCar selectByPrimaryKey(OutCarKey key);



}