package com.service.spsys.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dao.db1.spsys.AdminDao;
import com.dao.entity.spsys.SpCodeDecodes;
import com.dto.constants.Constants;
import com.dto.pojo.spsys.common.CodeRsp;
import com.service.spsys.ILicenseSrv;
import com.spinfosec.system.MemInfo;
import com.spinfosec.system.RspCode;
import com.utils.sys.DateUtil;
import com.utils.sys.LicenseManagerHolder;
import com.utils.sys.LicenseUtil;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName LicenseSrvImpl
 * @Description: 〈License许可管理业务处理实现类〉
 * @date 2018/11/8
 * All rights Reserved, Designed By SPINFO
 */
//@Service("licenseSrv")
public class LicenseSrvImpl implements ILicenseSrv
{
	public static final long MONTH = 30 * 24 * 60 * 60 * 1000L;
	public static final long DAY = 24 * 60 * 60 * 1000L;

	private Logger log = LoggerFactory.getLogger(LicenseSrvImpl.class);

	@Autowired
	private AdminDao adminDao;

	@Override
	public JSONObject getCurrentLicenseInfo()
	{
		JSONObject licenseInfo = new JSONObject();
		try
		{
			LicenseManager licenseManager = LicenseManagerHolder.getInstance().getLicenseManager();
			LicenseContent content = licenseManager.verify();
			Date notAfter = content.getNotAfter();
			Date notBefore = content.getNotBefore();
			String extra = content.getExtra().toString();
			JSONObject jsonObject = JSON.parseObject(extra);
			String resLimitCount = null != jsonObject.get(Constants.RES_LIMIT_COUNT)
					? jsonObject.get(Constants.RES_LIMIT_COUNT).toString()
					: "";
			String subsystem = null != jsonObject.get(Constants.SUBSYSTEM)
					? jsonObject.get(Constants.SUBSYSTEM).toString()
					: "";
			String productName = null != jsonObject.get(Constants.PRODUCT_NAME)
					? jsonObject.get(Constants.PRODUCT_NAME).toString()
					: "";
			String model = null != jsonObject.get(Constants.MODEL) ? jsonObject.get(Constants.MODEL).toString() : "";
			String includeModule = null != jsonObject.get(Constants.INCLUDE_MODULE)
					? jsonObject.get(Constants.INCLUDE_MODULE).toString()
					: "";
			licenseInfo.put(Constants.NOT_BEFORE, DateUtil.dateToString(notBefore, DateUtil.DATE_FORMAT_PATTERN));
			licenseInfo.put(Constants.NOT_AFTER, DateUtil.dateToString(notAfter, DateUtil.DATE_FORMAT_PATTERN));
			licenseInfo.put(Constants.RES_LIMIT_COUNT, resLimitCount);
			licenseInfo.put(Constants.SUBSYSTEM, subsystem);
			licenseInfo.put(Constants.PRODUCT_NAME, productName);
			licenseInfo.put(Constants.MODEL, model);
			licenseInfo.put(Constants.INCLUDE_MODULE, includeModule);

		}
		catch (Exception e)
		{
			log.error("当前License许可获取失败！", e);
		}
		return licenseInfo;
	}

	@Override
	public CodeRsp installAndVerifyLicense()
	{
		CodeRsp codeRsp = new CodeRsp(RspCode.SUCCESS);
		/************** 证书使用者端执行 ******************/
		LicenseManager licenseManager = LicenseManagerHolder.getInstance().getLicenseManager();
		// 安装证书
		try
		{
			File licDir = new File(LicenseManagerHolder.getInstance().getLicPath());
			if (licDir.exists())
			{
				File[] licFileArr = licDir.listFiles();
				if (null != licFileArr && licFileArr.length != 0)
				{
					File licFile = licFileArr[0];
					if (null != licFile && licFile.isFile())
					{
						// 安装前先校验待安装的license是否有效合法
						LicenseContent licenseContent = licenseManager.verify(FileUtils.readFileToByteArray(licFile));
						codeRsp = isLicenseValid(licenseContent);
						if (codeRsp.getCode().equalsIgnoreCase(RspCode.SUCCESS.getCode()))
						{
							licenseManager.install(licFile);
							log.debug("install license ok!");
						}
					}
					else
					{
						log.debug("license not exist!");
						codeRsp = new CodeRsp(RspCode.LICENSE_NOT_EXIST);
						return codeRsp;
					}
				}
				else
				{
					log.debug("license not exist!");
					codeRsp = new CodeRsp(RspCode.LICENSE_NOT_EXIST);
					return codeRsp;
				}
			}
		}
		catch (Exception e)
		{
			log.error("install license fail!", e);
			codeRsp = new CodeRsp(RspCode.LICENSE_INSTALL_FAIL);
			return codeRsp;
		}
		return codeRsp;
	}

	private CodeRsp isLicenseValid(LicenseContent content) throws Exception
	{
		CodeRsp codeRsp = new CodeRsp(RspCode.SUCCESS);
		if (null != MemInfo.getServletContext())
		{
			// 验证前先清除
			MemInfo.getServletContext().removeAttribute("licenseLive");
		}

		String extra = content.getExtra().toString();
		JSONObject jsonObject = JSON.parseObject(extra);
		String mcode = jsonObject.get(Constants.MCODE).toString();
		String subsystem = jsonObject.get(Constants.SUBSYSTEM).toString();
		String resLimitCount = jsonObject.get(Constants.RES_LIMIT_COUNT).toString();

		if (StringUtils.isEmpty(subsystem) || StringUtils.isEmpty(resLimitCount))
		{
			log.debug("verify license fail, the license is not completed!");
			codeRsp = new CodeRsp(RspCode.LICENSE_VERIFY_FAIL);
			return codeRsp;
		}
		if (!mcode.equals(LicenseUtil.getLocalMcode()))
		{
			log.debug("verify license fail, the license is not belong to this machine!");
			codeRsp = new CodeRsp(RspCode.LICENSE_NOT_MATCH_MACHINE);
		}
		else
		{
			Date notAfter = content.getNotAfter();
			Date notBefore = content.getNotBefore();
			if (notAfter.getTime() - System.currentTimeMillis() > 0
					&& System.currentTimeMillis() - notBefore.getTime() > 0)
			{
				if (null != MemInfo.getServletContext())
				{
					if (notAfter.getTime() - System.currentTimeMillis() < MONTH)
					{
						long day = (notAfter.getTime() - System.currentTimeMillis()) / DAY;
						MemInfo.getServletContext().setAttribute("licenseLive", day + 1);
					}
				}
				log.debug("verify license ok!");

			}
			else
			{
				log.debug("license out of date!");
				codeRsp = new CodeRsp(RspCode.LICENSE_OUT_OF_DATE);
			}

		}
		return codeRsp;
	}

	/**
	 * 校验许可
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean checkLicense() throws Exception
	{
		boolean b = isModuleAndAuthOk();
		if (!b)
		{
			return false;
		}
		CodeRsp codeRsp = isLicenseValid(LicenseManagerHolder.getInstance().getLicenseManager().verify());
		if (RspCode.SUCCESS.getCode().equals(codeRsp.getCode()))
		{
			return true;
		}
		return false;
	}

	/**
	 * 查询是否存在权限
	 * @return
	 */
	private boolean isModuleAndAuthOk()
	{
		List<SpCodeDecodes> allCodeDecodes = adminDao.getAllCodeDecodes();
		return !allCodeDecodes.isEmpty();
	}
}
