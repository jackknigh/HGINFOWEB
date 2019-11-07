package com.service.spsys;


import com.dto.pojo.spsys.common.CodeRsp;
import com.dto.pojo.spsys.system.LotusDatabaseDto;

import java.util.List;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName ILotusConfigSrv
 * @Description: 〈Lotusp配置业务处理接口〉
 * @date 2018/11/16
 * All rights Reserved, Designed By SPINFO
 */
public interface ILotusConfigSrv
{
	/**
	 * 获取Lotus邮箱类型配置
	 * @return
	 */
	LotusDatabaseDto getLotusMailConfig();

	/**
	 * 更新Lotus邮箱类型配置
	 * @param lotusDatabaseDto
	 * @return
	 */
	CodeRsp updateLotusMailConfig(LotusDatabaseDto lotusDatabaseDto);

	/**
	 * 获取Lotus文档类型配置
	 * @return
	 */
	List<LotusDatabaseDto> getLotusDocumentConfig();

	/**
	 * 更新Lotus文档类型配置
	 * @param lotusDatabaseDtos
	 * @return
	 */
	CodeRsp updateLotusDocumentConfig(List<LotusDatabaseDto> lotusDatabaseDtos);
}
