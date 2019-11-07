package com.utils.sys;


import com.dao.entity.spsys.SpCodeDecodes;
import com.dao.entity.spsys.SpSystemOperateLogInfo;
import com.dto.enums.SessionItem;
import com.dto.pojo.spsys.common.TreeData;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * @version version 1.0
 * @ClassName GenUtil
 * @Description: 〈通用工具类〉
 * @date 2018/10/10
 */
public class GenUtil
{

	private static Logger log = LoggerFactory.getLogger(GenUtil.class);
	private static ObjectMapper mapper = new ObjectMapper();
	/**
	 * 生成UUID
	 *
	 * @return
	 */
	public static String getUUID()
	{
		return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
	}



	/**
	 * 组装权限树结构
	 * @param spCodeDecodesList
	 * @return
	 */
	public static List<TreeData> getModuleTree(List<SpCodeDecodes> spCodeDecodesList)
	{

		List<TreeData> parents = new ArrayList<TreeData>();
		List<SpCodeDecodes> others = new ArrayList<SpCodeDecodes>();
		for (SpCodeDecodes spCodeDecodes : spCodeDecodesList)
		{
			// 如果父节点为空 则存在父节点集合中
			if (StringUtils.isEmpty(spCodeDecodes.getParentId()))
			{
				TreeData treeDataParent = new TreeData();
				treeDataParent.setId(spCodeDecodes.getId());
				treeDataParent.setName(spCodeDecodes.getName());
				treeDataParent.setShowChild(true);
				treeDataParent.setUrl(spCodeDecodes.getUrl());
				treeDataParent.setIcon(spCodeDecodes.getIcon());
				treeDataParent.setIsShow(true);
				parents.add(treeDataParent);
			}
			else
			{
				others.add(spCodeDecodes);
			}
		}

		for (TreeData parTreeData : parents)
		{
			List<TreeData> childTree = new ArrayList<>();
			for (SpCodeDecodes spCodeDecodes : others)
			{
				if (spCodeDecodes.getParentId().equals(parTreeData.getId()))
				{
					TreeData treeChild = new TreeData();
					treeChild.setId(spCodeDecodes.getId());
					treeChild.setName(spCodeDecodes.getName());
					treeChild.setShowChild(true);
					treeChild.setUrl(spCodeDecodes.getUrl());
					treeChild.setIsShow((spCodeDecodes.getIsShow().intValue()) == 0 ? true : false);
					childTree.add(treeChild);
				}
			}
			// 有子菜单则存放在children中，没有则是否显示子菜单showChild为false
			if (!childTree.isEmpty())
			{
				parTreeData.setChildren(childTree);
			}
			else
			{
				parTreeData.setShowChild(false);
			}
		}
		return parents;
	}

	/**
	 * 将bean转换为json
	 *
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static String beanToJson(Object obj) throws IOException
	{
		StringWriter writer = new StringWriter();

		JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);
		mapper.writeValue(gen, obj);
		gen.close();
		String json = writer.toString();
		writer.close();
		return json;
	}

	public static List<Object[]> mapToListObj(List<Map<String, Object>> mapList)
	{
		List<Object[]> obj = new ArrayList<Object[]>();
		for (Map<String, Object> map : mapList)
		{
			System.out.println(map.values());
			Collection values = map.values();
			List list = new ArrayList(values);
			obj.add(list.toArray());
		}
		return obj;
	}

	public static SpSystemOperateLogInfo packageSysLog(HttpServletRequest request)
	{
		SpSystemOperateLogInfo operateLogInfo = new SpSystemOperateLogInfo();
		operateLogInfo.setDiscriminator("discriminator");
		operateLogInfo.setIsLeaderForTx(1);
		operateLogInfo.setTransactionId("transactionId");
		operateLogInfo.setGenerationTimeTs(new Date());
		operateLogInfo.setAdminId((null != request.getSession().getAttribute(SessionItem.userId.name()))
				? request.getSession().getAttribute(SessionItem.userId.name()).toString()
				: "");
		operateLogInfo.setAdminName((null != request.getSession().getAttribute(SessionItem.userName.name()))
				? request.getSession().getAttribute(SessionItem.userName.name()).toString()
				: "");
		operateLogInfo.setRoleId((null != request.getSession().getAttribute(SessionItem.roleId.name()))
				? request.getSession().getAttribute(SessionItem.roleId.name()).toString()
				: "");
		operateLogInfo.setRoleName((null != request.getSession().getAttribute(SessionItem.roleName.name()))
				? request.getSession().getAttribute(SessionItem.roleName.name()).toString()
				: "");
		return operateLogInfo;
	}

}
