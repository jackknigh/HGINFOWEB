package com.service.syscomp.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import com.dao.entity.syserp.DataDictDetail;
import com.dao.entity.syserp.DataDictFoot;
import com.dao.entity.syserp.DataDictMain;
import com.dao.entity.syserp.DbgridColor;
import com.dto.enums.SessionItem;
import com.dto.pojo.sys.LoginUserBean;
import com.service.syscomp.SqlCommand;
import com.utils.sys.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 * Created by CuiL on 2018-11-28.
 */
@Transactional
@Service("sysCommand")
public class SqlCommandImpl implements SqlCommand {


    @Autowired
    DruidDataSource  dataSource;

    private static final Logger log = LoggerFactory.getLogger(SqlCommand.class);

    public DataDictMain GetDataDict(String dataDictId) {
        log.debug("SQLCommand.GetDataDict instances");
        long a = System.currentTimeMillis();
        DataDictMain mainDataDictSet = new DataDictMain();
        ArrayList<DataDictDetail> detailDataDictSet = new ArrayList<DataDictDetail>();
        ArrayList<DataDictFoot> footDataDictSet = new ArrayList<DataDictFoot>();
        ArrayList<DbgridColor> colorDataDictSet = new ArrayList<DbgridColor>();
        Connection conn = null;
        CallableStatement call = null;
        try {
            conn = dataSource.getConnection();

            call = conn.prepareCall("{call OW_Get_DataDict (?,?,?,?,?,?)}");
            LoginUserBean loginUserBean = ((LoginUserBean) SessionUtils.getSessionAttribute(SessionItem.loginUserBean.name()));
            call.setString(1, loginUserBean.getAccountId());
            call.setString(2, loginUserBean.getId());
            call.setObject(3, loginUserBean.getPassword());
            call.setString(4, loginUserBean.getNic());
            call.setString(5, dataDictId);
            call.setString(6, loginUserBean.getId());

            boolean results = call.execute();
            int rsCount = 0;
            do {
                if (results) {
                    ResultSet rs = call.getResultSet();
                    if (rsCount == 0) {
                        mainDataDictSet = (DataDictMain) resultSetToList(rs, DataDictMain.class).get(0);
                    } else if (rsCount == 1) {
                        detailDataDictSet = (ArrayList<DataDictDetail>) resultSetToList(rs, DataDictDetail.class);
                        mainDataDictSet.setDataDictDetails(detailDataDictSet);
                    } else if (rsCount == 2) {
                        footDataDictSet = (ArrayList<DataDictFoot>) resultSetToList(rs, DataDictDetail.class);
                        mainDataDictSet.setDataDictFoots(footDataDictSet);
                    } else if (rsCount == 3) {
                        colorDataDictSet = (ArrayList<DbgridColor>) resultSetToList(rs, DbgridColor.class);
                        mainDataDictSet.setDbgridColors(colorDataDictSet);
                    }
                    rsCount++;
                    rs.close();
                }
                results = call.getMoreResults();
            } while (results);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("GetDataDict:" + e.getMessage());
        }finally {
            closeAll(conn,call,null);
        }
        log.debug("GetDateDict总耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
        return mainDataDictSet;
    }

    public ArrayList<Object> ExecSelfSQL(String selfSql, List<Object> parms) {
        log.debug("SQLCommand.ExecSelfSQL instances");
        ResultSet reslist = null;
        ArrayList<Object> resList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs =null;
        try {
            conn = dataSource.getConnection();
            pstat = conn.prepareStatement(selfSql);
            for (int i = 1; i <= parms.size(); i++) {
                pstat.setObject(i, parms.get(i - 1));
            }
            reslist = pstat.executeQuery();
            resList = (ArrayList<Object>) resultSetToArrayList(reslist);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ExecSelfSQL:" + e.getMessage());
        }finally {
            closeAll(conn,pstat,reslist);
        }
        return resList;
    }


    public ArrayList<Object> ExecSQL(String sqlId, ArrayList<Object> parms) {
        ArrayList<Object> resList = new ArrayList<>();
        log.info("SQLCOMMAND：sqlId："+sqlId+" parms:"+ JSONObject.toJSONString(parms));
        Connection conn = null;
        CallableStatement call = null;
        ResultSet reslist =null;
        try {
            LoginUserBean loginUserBean = ((LoginUserBean) SessionUtils.getSessionAttribute(SessionItem.loginUserBean.name()));
            conn = dataSource.getConnection();
            call = conn.prepareCall("{call OW_Public_Execute(?,?,?,?,?" +
                    ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
                    ",?,?,?,?,?,?,?,?,?,?,?,?)}");
            call.setString(1, loginUserBean.getAccountId());
            call.setString(2, loginUserBean.getId());
            call.setObject(3, loginUserBean.getPassword());
            call.setString(4, loginUserBean.getNic());
            call.setString(5, sqlId);
            for (int i = 1; i < 33; i++) {
                if (i <= parms.size()) {
                    call.setObject(i + 5, parms.get(i - 1));
                } else {
                    call.setObject(i + 5, null);
                }
            }
//            log.info("ExecSQL  Parms:"+ JSONObject.toJSONString(call.getParameterMetaData()));
            reslist = call.executeQuery();
            resList = (ArrayList<Object>) resultSetToArrayList(reslist);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ExecSQL:" + e.getMessage());
        }finally {
            closeAll(conn,call,reslist);
        }
        return resList;
    }
    public ArrayList<Object> ExecSQLNoSession(String sqlId,String accountId,String operatorId, ArrayList<Object> parms) {
        ArrayList<Object> resList = new ArrayList<>();
        log.info("系统非登录情况下的SQL执行情况：sqlId："+sqlId+"  accountId:"+accountId+"  operatorId:"+operatorId+ " parms:"+ JSONObject.toJSONString(parms));
        Connection conn = null;
        CallableStatement call = null;
        ResultSet reslist =null;
        try {
            //LoginUserBean loginUserBean = ((LoginUserBean) SessionUtils.getSessionAttribute(SessionItem.loginUserBean.name()));
            conn = dataSource.getConnection();
            call = conn.prepareCall("{call OW_Public_Execute(?,?,?,?,?" +
                    ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
                    ",?,?,?,?,?,?,?,?,?,?,?,?)}");
            call.setString(1, accountId);
            call.setString(2, operatorId);
            call.setObject(3, "".getBytes());
            call.setString(4, null);
            call.setString(5, sqlId);
            for (int i = 1; i < 33; i++) {
                if (i <= parms.size()) {
                    call.setObject(i + 5, parms.get(i - 1));
                } else {
                    call.setObject(i + 5, null);
                }
            }
//            log.info("ExecSQL  Parms:"+ JSONObject.toJSONString(call.getParameterMetaData()));
            reslist = call.executeQuery();
            resList = (ArrayList<Object>) resultSetToArrayList(reslist);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ExecSQL:" + e.getMessage());
        }finally {
            closeAll(conn,call,reslist);
        }
        return resList;
    }

    @Override
    public boolean ExecSQLNoResult(String sqlId, ArrayList<Object> parms) {
        boolean resList = false;
        Connection conn = null;
        CallableStatement call = null;
        ResultSet rs =null;
        try {
            LoginUserBean loginUserBean = ((LoginUserBean) SessionUtils.getSessionAttribute(SessionItem.loginUserBean.name()));
            conn = dataSource.getConnection();
            call = conn.prepareCall("{call OW_Public_Execute(?,?,?,?,?" +
                    ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
                    ",?,?,?,?,?,?,?,?,?,?,?,?)}");
            call.setString(1, loginUserBean.getAccountId());
            call.setString(2, loginUserBean.getId());
            call.setObject(3, loginUserBean.getPassword());
            call.setString(4, loginUserBean.getNic());
            call.setString(5, sqlId);
            for (int i = 1; i < 33; i++) {
                if (i <= parms.size()) {
                    call.setObject(i + 5, parms.get(i - 1));
                } else {
                    call.setObject(i + 5, null);
                }
            }
//            log.info("ExecSQL  Parms:"+ JSONObject.toJSONString(call.getParameterMetaData()));
            resList = call.execute();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ExecSQL:" + e.getMessage());
        }finally {
            closeAll(conn,call,null);
        }
        return resList;
    }


    /**
     * 将resultSet转换为List需要对应的modal
     *
     * @param rs  jsbc获取的查询结果集
     * @param cls 结果集对应的实体类模型
     * @return resultSet的实体类
     * @throws Exception
     */
    private List resultSetToList(ResultSet rs, Class cls) throws Exception {
        //取得Method
        Method[] methods = cls.getDeclaredMethods();
        List lst = new ArrayList();
        // 用于获取列数、或者列类型
        ResultSetMetaData meta = rs.getMetaData();
        Object obj = null;
        while (rs.next()) {
            // 获取formbean实例对象
            obj = cls.newInstance(); // 用Class.forName方法实例化对象和new创建实例化对象是有很大区别的，它要求JVM首先从类加载器中查找类，然后再实例化，并且能执行类中的静态方法。而new仅仅是新建一个对象实例
            // 循环获取指定行的每一列的信息
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                // 当前列名
                String colName = meta.getColumnName(i);
                // 设置方法名
                String setMethodName = "set" + colName;
                //遍历Method
                for (int j = 0; j < methods.length; j++) {
                    if (methods[j].getName().equalsIgnoreCase(setMethodName)) {
                        setMethodName = methods[j].getName();
//                        log.debug("resultSetToList:setMethodName=" + setMethodName);
                        // 获取当前位置的值，返回Object类型
                        Object value = rs.getObject(colName);
                        if (value == null) {
                            continue;
                        }
                        //实行Set方法
                        try {
                            //// 利用反射获取对象
                            //JavaBean内部属性和ResultSet中一致时候
                            Method setMethod = obj.getClass().getMethod(
                                    setMethodName, value.getClass());
                            setMethod.invoke(obj, value);
                        } catch (Exception e) {
                            //JavaBean内部属性和ResultSet中不一致时候，使用String来输入值。
                            e.printStackTrace();
                            log.error("resultSetToList" + e.getMessage());
                        }
                    }
                }
            }
            lst.add(obj);
        }
        return lst;

    }

    private List resultSetToArrayList(ResultSet rs) throws SQLException {
        if (rs == null)
            return Collections.EMPTY_LIST;
        ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等
        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数
        List list = new ArrayList();
        Map rowData = new HashMap();
        while (rs.next()) {
            rowData = new HashMap(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                if (rs.getMetaData().getColumnType(i) == 93) {//datetime
                    String date = rs.getString(i) == null ? "" : rs.getString(i);
                    rowData.put(md.getColumnName(i), date.length() > 19 ? date.substring(0, 19) : date);
                } else {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
            }
            list.add(rowData);
        }
        return list;
    }

    public static void closeAll(Connection conn, CallableStatement cs, ResultSet rs) {
        log.info("closeAll resource.");
        try {
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void closeAll(Connection conn, PreparedStatement cs, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (cs != null) {
                cs.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
