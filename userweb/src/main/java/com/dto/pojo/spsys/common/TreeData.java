package com.dto.pojo.spsys.common;

import java.util.List;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName TreeData
 * @Description: 〈树结构体〉
 * @date 2018/10/10
 * All rights Reserved, Designed By SPINFO
 */
public class TreeData
{

	/**
	 * 树节点ID
	 */
	private String id;

	/**
	 * 树节点名称
	 */
	private String name;

	/**
	 * url
	 */
	private String url;

	/**
	 * 树节点图标
	 */
	private String icon;

	/**
	 * 是否展现子节点
	 */
	private Boolean showChild;

	/**
	 * 是否在菜单中显示
	 */
	private Boolean isShow;
	/**
	 * 子节点
	 */
	private List<TreeData> children;



	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public List<TreeData> getChildren()
	{
		return children;
	}

	public void setChildren(List<TreeData> children)
	{
		this.children = children;
	}

	public Boolean getShowChild()
	{
		return showChild;
	}

	public void setShowChild(Boolean showChild)
	{
		this.showChild = showChild;
	}

	public Boolean getIsShow()
	{
		return isShow;
	}

	public void setIsShow(Boolean isShow)
	{
		this.isShow = isShow;
	}
}
