package com.utils.sys;

import com.dao.entity.system.SysOperateLog;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统日志采集类
 *
 * @author Noah
 * @since 1.0.0, 2019/03/10
 */
public class SysLogUtil {

    private final static ThreadLocal<SysLog> threadLocal = new InheritableThreadLocal<>();

    public static SysLog getThreadLocalLog() {
        if (threadLocal.get() == null) {
            return new SysLog();
        }
        return threadLocal.get();
    }

    public static void setThreadLocalLog(final SysLog sysLog) {
        threadLocal.set(sysLog);
    }

    public static void remove() {
        threadLocal.remove();
    }


    public static class SysLog {
        List<Map<String, String>> sqlInfoList;
        /**
         * 操作批次号
         */
        private String tracerId;
        /**
         * 用户名
         */
        private String userId;
        /**
         * 用户名
         */
        private String userName;
        /**
         * url名称
         */
        private String urlName;
        /**
         * url中文名
         */
        private String urlNameCN;
        /**
         * 请求IP
         */
        private String dealIp;

        // /**
        //  * 返回参数
        //  */
        // private String content;
        /**
         * 请求参数
         */
        private String operateCondition;
        /**
         * 部门代码
         */
        private String departCode;
        /**
         * 部门名称
         */
        private String departName;
        /**
         * 开始时间
         */
        private Date startTime;

        private Integer portType;

        public Integer getPortType() {
            return portType;
        }

        public void setPortType(Integer portType) {
            this.portType = portType;
        }

        public String getTracerId() {
            return tracerId;
        }

        public void setTracerId(String tracerId) {
            this.tracerId = tracerId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUrlName() {
            return urlName;
        }

        public void setUrlName(String urlName) {
            this.urlName = urlName;
        }

        public String getUrlNameCN() {
            return urlNameCN;
        }

        public void setUrlNameCN(String urlNameCN) {
            this.urlNameCN = urlNameCN;
        }

        public String getDealIp() {
            return dealIp;
        }

        public void setDealIp(String dealIp) {
            this.dealIp = dealIp;
        }

        public String getOperateCondition() {
            return operateCondition;
        }

        public void setOperateCondition(String operateCondition) {
            this.operateCondition = operateCondition;
        }

        public String getDepartCode() {
            return departCode;
        }

        public void setDepartCode(String departCode) {
            this.departCode = departCode;
        }

        public String getDepartName() {
            return departName;
        }

        public void setDepartName(String departName) {
            this.departName = departName;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public List<Map<String, String>> getSqlInfoList() {
            return sqlInfoList;
        }

        public void setSqlInfoList(List<Map<String, String>> sqlInfoList) {
            this.sqlInfoList = sqlInfoList;
        }

        @Override
        public String toString() {
            return "SysLog{" +
                    "tracerId='" + tracerId + '\'' +
                    ", userName='" + userName + '\'' +
                    ", urlName='" + urlName + '\'' +
                    ", urlNameCN='" + urlNameCN + '\'' +
                    ", dealIp='" + dealIp + '\'' +
                    ", operateCondition='" + operateCondition + '\'' +
                    ", departCode='" + departCode + '\'' +
                    ", departName='" + departName + '\'' +
                    ", startTime=" + startTime +
                    '}';
        }
    }

}
