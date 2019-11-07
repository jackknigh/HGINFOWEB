package com.intercept;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInterceptor;
import com.utils.sys.SysLogUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
)
        , @Signature(type = StatementHandler.class, method = "update", args = {Statement.class})
        , @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
@Component("SqlStatementInterceptor")
public class SqlStatementInterceptor extends PageInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlStatementInterceptor.class);
    @Resource(name = "db2SqlSessionFactory")
    SqlSessionFactory sqlSessionFactory;

    @Override
    public Object intercept(Invocation invocation) {



        // 开始时间
        long start = System.currentTimeMillis();
        Object count1 = null;

        try {
            //update 和batch 的拦截不走PageInterceptor的拦截器
            if (invocation.getArgs().length == 1){
                count1 = invocation.proceed();
            }
            else{
                count1 = super.intercept(invocation);
            }
            getSql (invocation);
        } catch (Throwable throwable) {
            LOGGER.error("执行失败！", throwable);
            throwable.printStackTrace();
        } finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            LOGGER.info("--------------->cost time {}ms", time);
        }
        return count1;
    }

     private void getSql (Invocation invocation){
         String sql = "";
         Object params = "";
         String method = "";
         Object[] args = invocation.getArgs();
         if ("query".equalsIgnoreCase(invocation.getMethod().getName())) {
             MappedStatement ms = (MappedStatement) args[0];
             Object parameter = args[1];
             RowBounds rowBounds = (RowBounds) args[2];
             ResultHandler resultHandler = (ResultHandler) args[3];
             BoundSql boundSql = ms.getBoundSql(parameter);
             method = ms.getId();
             sql = boundSql.getSql();
             params = boundSql.getParameterObject();
         } else if ("update".equalsIgnoreCase(invocation.getMethod().getName())) {
             MetaObject metaStatementHandler = SystemMetaObject.forObject(invocation.getTarget());
             while (metaStatementHandler.hasGetter("h")) {
                 Object object = metaStatementHandler.getValue("h");
                 metaStatementHandler = SystemMetaObject.forObject(object);
             }
             sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
             params = metaStatementHandler.getValue("delegate.boundSql.parameterObject");
             method = (String)metaStatementHandler.getValue("delegate.mappedStatement.id");
         }
         if (!sql.contains("sys_operate_log") && !sql.contains("sys_port_log") || !sql.contains("sys_operate_log_orderId")) {
             SysLogUtil.SysLog sysLog = SysLogUtil.getThreadLocalLog();
             if (null != sysLog &&  StringUtils.isNotBlank(sysLog.getTracerId())) {
                 List<Map<String, String>> sqlInfoList = sysLog.getSqlInfoList() == null ? new ArrayList<>() : sysLog.getSqlInfoList();
                 Map<String, String> map = new HashMap();
                 map.put("sql", sql);
                 map.put("params", JSONObject.toJSONString(params));
                 map.put("method", method);
                 sqlInfoList.add(map);
                 sysLog.setSqlInfoList(sqlInfoList);
                 SysLogUtil.setThreadLocalLog(sysLog);
             }
         }
     }

}
