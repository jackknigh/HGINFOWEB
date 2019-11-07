package com.service.system.impl;

import com.dao.db1.system.SysDepartMapper;
import com.dao.db1.system.SysOperateLogMapper;
import com.dao.db1.system.SysOperateLogOrderIdMapper;
import com.dao.entity.system.SysOperateLog;
import com.dao.entity.system.SysOperateLogOrderId;
import com.dto.constants.Constants;
import com.dto.enums.SessionItem;
import com.dto.pojo.spsys.LoginUserBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.system.SysOperateLogService;
import com.utils.sys.GenUtil;
import com.utils.sys.SessionUtils;
import com.utils.sys.TextUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * SysOperateLog 行为日志接口实现类
 * Created by Xiezx on 2019-01-14.
 */
@Transactional
@Service("sysOperateLogService")
public class SysOperateLogServiceImpl implements SysOperateLogService {
    @Autowired
    private SysOperateLogMapper sysOperateLogMapper;
    @Autowired
    private SysDepartMapper sysDepartMapper;

    @Autowired
    private SysOperateLogOrderIdMapper sysOperateLogOrderIdMapper;

    @Override
    public int insert(SysOperateLog sysOperateLog) {
        return sysOperateLogMapper.insert(sysOperateLog);
    }

    @Override
    public SysOperateLog selectByPrimaryKey(String id) {
        return sysOperateLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysOperateLog> selectAll() {
        return sysOperateLogMapper.selectAll();
    }

    @Override
    public int save(SysOperateLog sysOperateLog) {
        int ret = 0;
        // 新增
        if ((sysOperateLog != null) && (!TextUtils.isEmpty(sysOperateLog.getUpdateFlg()))) {
//            if (sysOperateLog.getUpdateFlg().equalsIgnoreCase("add")) {
//                if(sysOperateLog.getOperateType()==0)
//                    sysOperateLog.setOperateName("登陆");
//                else if(sysOperateLog.getOperateType()==1)
//                    sysOperateLog.setOperateName("查询");
//                else if(sysOperateLog.getOperateType()==2)
//                    sysOperateLog.setOperateName("新增");
//                else if(sysOperateLog.getOperateType()==3)
//                    sysOperateLog.setOperateName("修改");
//                else if(sysOperateLog.getOperateType()==4)
//                    sysOperateLog.setOperateName("删除");
//                else if(sysOperateLog.getOperateType()==5)
//                    sysOperateLog.setOperateName("打印");
//                else if(sysOperateLog.getOperateType()==6)
//                    sysOperateLog.setOperateName("导出");
//                else if(sysOperateLog.getOperateType()==7)
//                    sysOperateLog.setOperateName("启用");
//                else if(sysOperateLog.getOperateType()==8)
//                    sysOperateLog.setOperateName("停用");
//                else if(sysOperateLog.getOperateType()==9)
//                    sysOperateLog.setOperateName("审核");
//                else if(sysOperateLog.getOperateType()==-1)
//                    sysOperateLog.setOperateName("系统内置");
//                else
//                    sysOperateLog.setOperateName("其他");
            if (TextUtils.isEmpty(sysOperateLog.getSystemName())){
                sysOperateLog.setSystemName("奥威ERP平台");
            }
            sysOperateLog.setId(GenUtil.getUUID());
            sysOperateLog.setCreateDate(new Date());
            sysOperateLog.setEndDate(new Date());
            sysOperateLog.setOperateTime(new Date());
            sysOperateLog.setUpdateDate(new Date());
            int rs = sysOperateLogMapper.insert(sysOperateLog);
            saveSysOperateLogOrderId(sysOperateLog.getOrderId(), sysOperateLog.getId(), sysOperateLog.getOperateTime(), sysOperateLog.getOperateType());
            return rs;
//            }
        }
        return ret;
    }

    @Override
    public int save(String userName, String userId, Integer sort, String parentId, String orderId, String functionModule,
                    String systemName, String depart, String depCode, String operateName, Integer operateType, Integer operateResult, String content,
                    String terminalId, String errorCode, String operateCondition, String creator, String remark) {
        return save(new SysOperateLog("", userName, userId, sort, parentId, orderId, functionModule, operateName, systemName, depart, depCode, null, operateType, operateResult, content, terminalId, errorCode, operateCondition, creator, null, null, null, remark));
    }

    @Override
    public int save(String parentId, String orderId, String operateName, String functionModule, Integer operateType, String content, String remark) {
        LoginUserBean loginUserBean = (LoginUserBean) SessionUtils.getSessionAttribute(SessionItem.loginUserBean.name());
        if (null == loginUserBean) {
            return 0;
        }
        return save(loginUserBean.getUserName(), loginUserBean.getId(), 0, parentId, orderId, functionModule,
                "", loginUserBean.getDepartName(), loginUserBean.getDepCode(), operateName, operateType, 1, content,
                SessionUtils.getIp(), "", "where id =" + orderId, loginUserBean.getUserName(), remark);

    }

    @Override
    public PageInfo<SysOperateLog> search(Map<String, Object> queryMap) {
        Integer pageNUm = (Integer) queryMap.get(Constants.PAGE_CURRENT);
        Integer pageSize = (Integer) queryMap.get(Constants.PAGE_SIZE);
        List<SysOperateLog> list = new ArrayList();
        PageHelper.startPage(pageNUm, pageSize);
        List<SysOperateLog> search = sysOperateLogMapper.search(queryMap);

        PageInfo<SysOperateLog> pageList = new PageInfo<>(search);
        return pageList;
    }

    @Override
    public List<Map<String, Object>> departHistogram(Map<String, Object> queryMap) {
        return sysOperateLogMapper.departHistogram(queryMap);
    }

    @Override
    public Map<String, Object> departPie(Map<String, Object> queryMap) {
        return sysOperateLogMapper.departPie(queryMap);
    }

    /**
     * 保存操作日志工单关系表
     *
     * @param orderId
     * @param operateLogId
     */
    public void saveSysOperateLogOrderId(String orderId, String operateLogId, Date createDate, Integer operateType) {
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(operateLogId)) {
            return;
        }
        List<String> orderIdList = Arrays.asList(orderId.split(";"));
        orderIdList.stream().forEach(e -> {
            updateOperateLogNextTime(e, createDate);
            SysOperateLogOrderId sysOperateLogOrderId = new SysOperateLogOrderId();
            sysOperateLogOrderId.setId(GenUtil.getUUID());
            sysOperateLogOrderId.setOperatorId(operateLogId);
            sysOperateLogOrderId.setOrderId(e);
            sysOperateLogOrderId.setCreateDate(createDate);
            sysOperateLogOrderId.setOperateType(operateType);
            sysOperateLogOrderId.setNextDate(createDate);
            sysOperateLogOrderIdMapper.insert(sysOperateLogOrderId);
        });
    }

    public void updateOperateLogNextTime(String orderId, Date createDate) {
        SysOperateLogOrderId sysOperateLogOrderId = sysOperateLogOrderIdMapper.selectByOrderId(orderId);
        if (null != sysOperateLogOrderId) {
            sysOperateLogOrderId.setNextDate(createDate);
            sysOperateLogOrderIdMapper.updateNextTimeById(sysOperateLogOrderId);
        }
    }
}
