package com.service.spsys.impl;


import com.dao.db1.spsys.AlgorithmDao;
import com.dao.entity.spsys.SpEncryptionAlgorithm;
import com.service.spsys.IAlgorithmSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ank
 * @version v 1.0
 * @title [标题]
 * @ClassName: com.spinfosec.service.impl.AlgorithmSrvImpl
 * @description [一句话描述]
 * @create 2018/11/13 12:02
 * @copyright Copyright(C) 2018 SHIPING INFO Corporation. All rights reserved.
 */
@Transactional
@Service("algorithmSrv")
public class AlgorithmSrvImpl implements IAlgorithmSrv
{
    @Autowired
    AlgorithmDao algorithmDao;

    /**
     * 根据算法id获取算法信息
     * @param id
     * @return
     */
    @Override
    public SpEncryptionAlgorithm getAlgorithm(String id)
    {
        return algorithmDao.getAlgorithm(id);
    }

    /**
     * 根据算法名称获取算法信息
     * @param algorithm
     * @return
     */
    @Override
    public SpEncryptionAlgorithm getAlgorithmByName(String algorithm)
    {
        return algorithmDao.getAlgorithmByName(algorithm);
    }
}
