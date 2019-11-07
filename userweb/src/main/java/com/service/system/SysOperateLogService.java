package com.service.system;

import com.dao.entity.system.SysOperateLog;
import com.dao.entity.system.SysOperateLogOrderId;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * SysOperateLog 行为日志接口
 * Created by Xiezx on 2019-01-14.
 */
public interface SysOperateLogService {
    /**
     * 新增行为日志
     *
     * @param sysOperateLog
     * @return 1代表成功
     * @date 2019-01-14
     */
    int insert(SysOperateLog sysOperateLog);

    /**
     * 根据id查看行为日志详情
     *
     * @param id
     * @return 实体类
     * @date 2019-01-14
     */
    SysOperateLog selectByPrimaryKey(String id);

    /**
     * 查询所有行为日志
     *
     * @param
     * @return
     * @date 2019-01-14
     */
    List<SysOperateLog> selectAll();

    /**
     * 保存行为日志
     *
     * @param sysOperateLog
     * @return 1代表成功
     * @date 2019-01-14
     */
    int save(SysOperateLog sysOperateLog);

    /***
     *<p>方法:save 保存行为日志--后台使用，全参数版 </p>
     *<ul>
     *<li> @param userName 用户姓名</li>
     *<li> @param userId 用户编号</li>
     *<li> @param sort 自定义排序</li>
     *<li> @param parentId 父单据编号</li>
     *<li> @param orderId 单据编号</li>
     *<li> @param functionModule 功能模块</li>
     *<li> @param operateName 操作类型名</li>
     *<li> @param systemName 系统名</li>
     *<li> @param depart 机构名</li>
     *<li> @param depCode 机构编号</li>
     *<li> @param operateType 操作类型0: '登录',  1: '查询',  2: '新增', 3: '修改', 4: '删除',  5: '打印',6: '导出',7: '启用',8: '停用'，9: '审核' ，-1:""系统</li>
     *<li> @param operateResult 操作结果</li>
     *<li> @param content 内容</li>
     *<li> @param terminalId 终端编号</li>
     *<li> @param errorCode 错误编号</li>
     *<li> @param operateCondition 操作条件</li>
     *<li> @param creator 创建人编号</li>
     *<li> @param remark 备注</li>
     *<li>@return int  </li>
     *<li>@author CuiLiang </li>
     *<li>@date 2019-03-20 23:34  </li>
     *</ul>
     */
    int save(String userName, String userId, Integer sort, String parentId, String orderId, String functionModule,
             String systemName, String depart, String depCode, String operateName, Integer operateType, Integer operateResult, String content, String terminalId,
             String errorCode, String operateCondition, String creator, String remark);

    /**
     * <p>方法:save 使用系统session赋值的简化版 </p>
     * <ul>
     * <li> @param parentId 父单据编号</li>
     * <li> @param orderId 单据编号</li>
     * <li> @param functionModule 功能模块</li>
     * <li> @param operateName 操作类型名</li>
     * <li> @param systemName 系统名</li>
     * <li> @param operateType 操作类型0: '登录',  1: '查询',  2: '新增', 3: '修改', 4: '删除',  5: '打印',6: '导出',7: '启用',8: '停用'，9: '审核'，-1:""系统</li>
     * <li> @param content 内容</li>
     * <li> @param remark 备注</li>
     * <li>@return int  </li>
     * <li>@author CuiLiang </li>
     * <li>@date 2019-03-20 23:56  </li>
     * </ul>
     */
    int save(String parentId, String orderId, String operateName, String functionModule,
             Integer operateType, String content, String remark);

    /**
     * 分页查询行为日志
     *
     * @param queryMap
     * @return 实体类集合
     * @date 2019-01-14
     */
    PageInfo<SysOperateLog> search(Map<String, Object> queryMap);

    /**
     * 查询改地区操作日志次数_柱状图
     *
     * @param queryMap
     * @return int 操作次数
     * @date 2019-01-14
     */
    List<Map<String, Object>> departHistogram(Map<String, Object> queryMap);

    /**
     * 查询改地区操作日志次数_饼图
     *
     * @param queryMap
     * @return int 操作次数
     * @date 2019-01-14
     */
    Map<String, Object> departPie(Map<String, Object> queryMap);

}

