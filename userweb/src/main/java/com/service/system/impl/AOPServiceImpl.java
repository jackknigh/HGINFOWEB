package com.service.system.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dao.entity.system.SysOperateLog;
import com.dao.entity.system.SysPortLog;
import com.dto.pojo.system.KeyValues;
import com.service.system.AOPService;
import com.service.system.SysOperateLogService;
import com.service.system.SysPortLogService;
import com.utils.sys.GenUtil;
import com.utils.sys.SysLogUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Noah
 * @since 1.0.0, 2019/03/17
 */
@Service
public class AOPServiceImpl implements AOPService {

    private ExecutorService executorService = new ThreadPoolExecutor(10,
            10,
            30,
            TimeUnit.MINUTES,
            new ArrayBlockingQueue<Runnable>(10));

    private Logger logger = LoggerFactory.getLogger(AOPServiceImpl.class);

    @Autowired
    private SysPortLogService sysPortLogService;

    @Autowired
    private SysOperateLogService sysOperateLogService;

    //@Async("asyncPromiseExecutor")
    public static void main(String[] args) {
        String aa = "{\"flag\":[\"1\"],\"ids\":[\"[{\\\"value\\\":\\\"06FD558ABB834641AEB1949D485B4EEE\\\"},{\\\"value\\\":\\\"0B95971871E343FFB0E5FFCAD0E48139\\\"}]\"]}";
        Map<String, String[]> map = JSONObject.parseObject(aa, new TypeReference<Map<String, String[]>>() {
        });
        List<KeyValues> list = JSONArray.parseArray(map.get("ids")[0], KeyValues.class);
        String data[] = null;
        data = new String[3]; //开辟一个长度为3的数组

        data[0] = "1111";
        data[1] = "222";


        String[] arr = map.get("ids");
        System.out.println(list);
    }


    /**
     * 保存切面结果
     *
     * @param responseStr
     */
    @Override
    public void saveAopLog(String responseStr) {
        SysLogUtil.SysLog sysLog = SysLogUtil.getThreadLocalLog();
        if (null == sysLog || StringUtils.isBlank(sysLog.getTracerId())) {
            return;
        }
        List<String> ids = new ArrayList<>();
        try {
            ids = getIds(sysLog, responseStr);

        } catch (Exception ex) {
            ids = new ArrayList<>();
        } finally {
            SysLogUtil.remove();
        }
        final String finalIds = String.join(";", ids);
        executorService.execute(() -> {
            SysOperateLog sysOperateLog = saveOperateLog(sysLog, responseStr, finalIds);
            savePortLog(sysLog);
        });
    }

    /**
     * 获取
     *
     * @param sysLog
     * @param responseStr
     * @return
     */
    private List<String> getIds(SysLogUtil.SysLog sysLog, String responseStr) {
        List<String> ids = new ArrayList<>();
        Object id = "";
        if (StringUtils.isNotBlank(sysLog.getOperateCondition()) && !sysLog.getOperateCondition().contains("=")) {
            Map<String, Object> map = JSONObject.parseObject(sysLog.getOperateCondition(), new TypeReference<Map<String, Object>>() {
            });
            id = map.get("id");
            if (Objects.nonNull(id) && !"".equals(id)) {
                ids.add((String) id);
            }
        }
        if (CollectionUtils.isEmpty(ids) && StringUtils.isNotBlank(responseStr)) {
            Map<String, Object> map = JSONObject.parseObject(responseStr, new TypeReference<Map<String, Object>>() {
            });
            Object obj = map.get("bean");
            if (null != obj && !"".equals(obj) && !(obj instanceof List)) {
                id = ((JSONObject) obj).getString("id");
                if (Objects.nonNull(id) && !"".equals(id)) {
                    ids.add((String) id);
                }
            }
        }
        if (CollectionUtils.isEmpty(ids)) {
            Map<String, String[]> map = JSONObject.parseObject(sysLog.getOperateCondition(), new TypeReference<Map<String, String[]>>() {
            });
            List<KeyValues> list = JSONArray.parseArray(map.get("ids")[0], KeyValues.class);
            if (!CollectionUtils.isEmpty(list)) {
                list.stream().forEach(e -> ids.add(e.getValue()));
            }
        }
        return ids;
    }


    /**
     * 保存拦截日志
     */
    private SysOperateLog saveOperateLog(SysLogUtil.SysLog sysLog, String responseStr, String orderId) {
        if (null == sysLog) {
            return null;
        }
        Date now = new Date();
        SysOperateLog sysOperateLog = new SysOperateLog();
        sysOperateLog.setId(GenUtil.getUUID());
        sysOperateLog.setUserName(sysLog.getUserName());
        sysOperateLog.setUserId(sysLog.getUserId());
        sysOperateLog.setSystemName("奥威ERP平台");
        sysOperateLog.setOperateTime(sysLog.getStartTime());
        sysOperateLog.setOperateType(null == sysLog.getPortType() ? -1 : sysLog.getPortType());
        sysOperateLog.setFunctionModule(sysLog.getUrlNameCN());
        sysOperateLog.setTerminalId(sysLog.getDealIp());
        sysOperateLog.setOperateName(sysLog.getUrlName());
        sysOperateLog.setOperateCondition(sysLog.getOperateCondition());
        sysOperateLog.setOperateResult(0);
        sysOperateLog.setDepCode(sysLog.getDepartCode());
        sysOperateLog.setCreateDate(now);
        sysOperateLog.setEndDate(now);
        sysOperateLog.setRemark(sysLog.getTracerId());
        sysOperateLog.setDepart(sysLog.getDepartName());
        sysOperateLog.setContent(responseStr);
        sysOperateLog.setOrderId(orderId);
        sysOperateLog.setUpdateFlg("add");
        sysOperateLogService.save(sysOperateLog);
        return sysOperateLog;
    }

    /**
     * 保存操作日志
     */
    private void savePortLog(SysLogUtil.SysLog sysLog) {
        try {
            Date now = new Date();
            List<Map<String, String>> sysInfoList = sysLog.getSqlInfoList();
            if (CollectionUtils.isEmpty(sysInfoList)) {
                return;
            }

            sysInfoList.stream().forEach(e -> {
                String sql = e.get("sql");
                if (StringUtils.isNotBlank(sql) && sql.contains("sys_operate_log_orderId")) {
                    return;
                }
                byte[] sqlByte;
                try {
                    sqlByte = sql.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    return;
                }
                SysPortLog sysPortLog = new SysPortLog();
                sysPortLog.setId(GenUtil.getUUID());
                sysPortLog.setUserName(sysLog.getUserName());
                sysPortLog.setUserId(sysLog.getUserId());
                sysPortLog.setRegId("奥威ERP平台");
                sysPortLog.setRequestId("奥威ERP平台");
                sysPortLog.setOperateTime(sysLog.getStartTime());
                sysPortLog.setPortName(e.get("method"));
                sysPortLog.setPortType("1");
                sysPortLog.setPortSevice(sysLog.getUrlNameCN());
                sysPortLog.setDealIp(sysLog.getDealIp());
                sysPortLog.setOperateCondition(e.get("params"));
                sysPortLog.setContent("BASE64:"+Base64.getEncoder().encodeToString(sqlByte));
                sysPortLog.setDealStatus("1");
                sysPortLog.setDepCode(sysLog.getDepartCode());
                sysPortLog.setCreateDate(now);
                sysPortLog.setEndDate(now);
                sysPortLog.setRemark(sysLog.getTracerId());
                sysPortLog.setOrganization(sysLog.getDepartName());
                sysPortLogService.insert(sysPortLog);

            });
        } catch (Exception ex) {
        }


    }
}
