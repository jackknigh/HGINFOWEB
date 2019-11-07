package com.utils.sys;


import com.dao.entity.spsys.SpConfigProperties;
import com.dto.constants.Constants;
import com.dto.pojo.spsys.system.EmailConnectData;
import com.spinfosec.system.MemInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName EmailUtil
 * @Description: 〈邮件工具类〉
 * @date 2018/11/1
 * All rights Reserved, Designed By SPINFO
 */
public class EmailUtil
{
	private static Logger log = LoggerFactory.getLogger(EmailUtil.class);

	/**
	 * 发送文本格式邮件
	 * @param settings
	 * @param toEmail
	 * @param ccEmail
	 * @param msg
	 * @param subject
	 * @throws EmailException
	 */
	public static void sendTextMail(List<SpConfigProperties> settings, String toEmail, String ccEmail, String msg,
									String subject) throws EmailException
	{
		try
		{
			EmailConnectData connectData = packEmailConfig(settings);
			String sendEmail = connectData.getEmail();
			String password = connectData.getPassword();
			Email email = new SimpleEmail();
			email.setHostName(connectData.getIp());
			email.setSmtpPort(Integer.parseInt(connectData.getPort()));
			email.setAuthenticator(new DefaultAuthenticator(sendEmail, password));
			email.setFrom(sendEmail);
			email.addTo(toEmail);
			email.setContent(msg, EmailConstants.TEXT_PLAIN);
			email.setCharset("utf-8");
			email.setSubject(subject);
			if (StringUtils.isNotEmpty(ccEmail))
			{
				email.addCc(ccEmail);
			}
			email.send();
			log.info("邮件发送成功...");
		}
		catch (EmailException e)
		{
			log.error("邮件发送失败！", e);
			throw e;
		}

	}

	public static void sendTextMail(String toEmail, String ccEmail, String msg, String subject) throws EmailException
	{
		try
		{
			// 从数据库查发送邮件的配置信息
			Map<String, String> emailInfo = MemInfo.getEmailInfo();

			String smtp = emailInfo.get(Constants.EmailSettings.SMTP);
			int port = Integer.parseInt(emailInfo.get(Constants.EmailSettings.PORT));
			String sendmail = emailInfo.get(Constants.EmailSettings.EMAIL);
			String passwrod = emailInfo.get(Constants.EmailSettings.PASSWORD);

			Email email = new SimpleEmail();
			email.setHostName(smtp);
			email.setSmtpPort(port);
			email.setAuthenticator(new DefaultAuthenticator(sendmail, passwrod));
			email.setFrom(sendmail);
			email.addTo(toEmail);
			email.setContent(msg, EmailConstants.TEXT_PLAIN);
			email.setCharset("utf-8");
			email.setSubject(subject);
			if (StringUtils.isNotEmpty(ccEmail))
			{
				email.addCc(ccEmail);
			}
			email.send();
			log.info("邮件发送成功...");

		}
		catch (Exception e)
		{
			log.error("邮件发送失败！", e);
			throw e;
		}

	}

	public static EmailConnectData packEmailConfig(List<SpConfigProperties> settings)
	{
		EmailConnectData connectData = new EmailConnectData();
		for (SpConfigProperties configProperties : settings)
		{
			if (configProperties.getName().equalsIgnoreCase("SMTP"))
			{
				connectData.setIp(configProperties.getValue());
			}
			else if (configProperties.getName().equalsIgnoreCase("PORT"))
			{
				connectData.setPort(configProperties.getValue());
			}
			else if (configProperties.getName().equalsIgnoreCase("EMAIL"))
			{
				connectData.setEmail(configProperties.getValue());
			}
			else if (configProperties.getName().equalsIgnoreCase("PASSWORD"))
			{
				connectData.setPassword(configProperties.getValue());
			}
		}
		return connectData;
	}
}
