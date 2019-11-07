package com.dao.entity.spsys;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @title [权限模块表]
 * @ClassName: SpCodeDecodes
 * @description [权限模块表]
 * @author ank
 * @version v 1.0
 * @create 2018/9/6 16:03
 * @copyright Copyright(C) 2018 SHIPING INFO Corporation. All rights reserved.
 */
public class SpCodeDecodes implements Serializable
{

    private static final long serialVersionUID = 4581261519948434092L;
    /**
     * 主键
     */
    private String id;
    /**
     * 模块名
     */
    private String name;
    /**
     * 父节点
     */
    private String parentId;
    /**
     * 路径
     */
    private String url;
    /**
     * 排序
     */
    private String order;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 是否在菜单中显示
	 */
	private BigDecimal isShow;

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


    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }


    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }


    public String getOrder()
    {
        return order;
    }

    public void setOrder(String order)
    {
        this.order = order;
    }

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public BigDecimal getIsShow()
	{
		return isShow;
	}

	public void setIsShow(BigDecimal isShow)
	{
		this.isShow = isShow;
	}
}
