package com.dto.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;

public enum JqlbEnum {

    CUSTOM_ANJIAN("-101", "自定义案件"),
    CUSTOM_JINGQING("-102", "自定义警情"),
    CUSTOM_SHIJIAN("-103", "自定义事件");

    /**
     * 状态码
     */
    private String code;
    /**
     * 状态描述
     */
    private String description;

    private JqlbEnum(String code, String description)
    {
        this.code = code;
        this.description = description;
    }

    public String getCode()
    {
        return code;
    }

    public String getDescription()
    {
        return description;
    }

    public static JqlbEnum getJqlbEnumByCode(String code)
    {
        EnumSet<JqlbEnum> enumSet = EnumSet.allOf(JqlbEnum.class);
        for (JqlbEnum jqlbEnum : enumSet)
        {
            if (StringUtils.isNotEmpty(code) && code.equals(jqlbEnum.getCode()))
            {
                return jqlbEnum;
            }
        }
        return null;
    }
}
