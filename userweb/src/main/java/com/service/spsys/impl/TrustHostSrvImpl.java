package com.service.spsys.impl;

import com.dao.db1.spsys.TrustHostDao;
import com.dao.entity.spsys.SpAdminHostSetting;
import com.dto.pojo.spsys.system.TrustHostBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.spsys.ITrustHostSrv;
import com.utils.sys.GenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName SystemSecHostSrvImpl
 * @Description: 〈主机管理业务实现类〉
 * @date 2018/10/15
 * All rights Reserved, Designed By SPINFO
 */
@Transactional
@Service("trustHostSrv")
public class TrustHostSrvImpl implements ITrustHostSrv
{

	@Autowired
	private TrustHostDao trustHostDao;


	/**
	 * 分页查询信任主机信息
	 * @param queryMap
	 * @return
	 */
	@Override
	public PageInfo<TrustHostBean> querByPage(Map<String, Object> queryMap)
	{
		Integer pageNum = Integer.valueOf(queryMap.get("currentPage").toString());
		Integer pageSize = Integer.valueOf(queryMap.get("pageSize").toString());
		List<TrustHostBean> spAdminHostSettingList = trustHostDao.querByPage(queryMap);
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<TrustHostBean> pageList = new PageInfo<>(spAdminHostSettingList);
		return pageList;
	}

	/**
	 * 获取所有信任主机信息
	 * @return
	 */
	@Override
	public List<SpAdminHostSetting> getAllSecHost()
	{
		return trustHostDao.getAllSecHost();
	}

	/**
	 * 根据id删除对应主机
	 * @param ids
	 */
	@Override
	public void deleteHostSec(String ids)
	{
		String[] idArr = ids.split(",");
		for (String id : idArr)
		{
			// 删除用户和主机关联
			trustHostDao.deldeteAdminTrustHostByHostId(id);
			// 再删除该主机
			trustHostDao.deleteHostSecById(id);
		}

	}

	/**
	 * 保存主机管理
	 * @param spAdminHostSetting
	 */
	@Override
	public void saveHostSec(SpAdminHostSetting spAdminHostSetting)
	{
		String id = GenUtil.getUUID();
		spAdminHostSetting.setId(id);
		spAdminHostSetting.setCreateDate(new Date());
		spAdminHostSetting.setCreatedBy("eb8f71082a85469ea3868dfb5cd59500");
		trustHostDao.saveHostSec(spAdminHostSetting);
	}

	/**
	 * 更新主机管理
	 * @param spAdminHostSetting
	 */
	@Override
	public void updateHostSec(SpAdminHostSetting spAdminHostSetting)
	{
		trustHostDao.updateHostSec(spAdminHostSetting);
	}

	/**
	 * 根据ID获取对应信任主机信息
	 * @param id
	 * @return
	 */
	@Override
	public SpAdminHostSetting getHostSettingById(String id)
	{
		return trustHostDao.getHostSettingById(id);
	}
}
