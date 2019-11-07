package com.service.spsys;


import com.dao.entity.spsys.SpOrgUnitDict;
import com.dto.pojo.spsys.common.CodeRsp;
import com.dto.pojo.spsys.system.OrgTreeBean;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName IOrgService
 * @Description: 〈组织业务接口〉
 * @date 2018/10/16
 * All rights Reserved, Designed By SPINFO
 */
public interface IOrgService
{
	/**
	 * 获取所有组织信息
	 * @return
	 */
	List<OrgTreeBean> getOrg(Map<String, Object> queryMap);

	/**
	 * 查询部门ID关联的用户
	 * @param orgId
	 * @return
	 */
	List<Object[]> getUserByOrgId(String orgId);

	/**
	 * 根据ID获取组织信息
	 * @param orgId
	 */
	SpOrgUnitDict getOrgById(String orgId);

	/**
	 * 保存组织
	 * @param orgTreeBean
	 */
	void saveOrgUnit(OrgTreeBean orgTreeBean);

	/**
	 * 更新组织
	 * @param orgTreeBean
	 */
	void updateOrgUnit(OrgTreeBean orgTreeBean);

	/**
	 * 删除组织
	 * @param id
	 * @return
	 */
	CodeRsp delOrg(String id);
}
