package com.dto.pojo.spsys;

import com.dto.enums.RspCode;

import java.io.Serializable;


public class ResponseMessage implements Serializable {

    private static final long serialVersionUID = -3542357415146381433L;
    private String msg;
    private String code;
    private Object bean;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    /***
    *<p>方法:sendError 失败响应结果 </p>
    *<ul>
     *<li> @param rspCode TODO</li>
    *<li>@return com.dto.pojo.sys.ResponseMessage  </li>
    *<li>@author CuiLiang </li>
    *<li>@date 2018-11-12 12:52  </li>
    *</ul>
    */
    public static ResponseMessage sendError(){
        ResponseMessage message = new ResponseMessage();
        message.setCode(RspCode.FAILURE.getCode());
        message.setMsg(RspCode.FAILURE.getDescription());
        message.setBean("");
        return message;
    }
    /***
     *<p>方法:sendOK 成功响应结果 </p>
     *<ul>
     *<li> @param bean TODO</li>
     *<li>@return com.dto.pojo.sys.ResponseMessage  </li>
     *<li>@author CuiLiang </li>
     *<li>@date 2018-11-12 12:51  </li>
     *</ul>
     */
    public static ResponseMessage sendOK(){
        ResponseMessage message = new ResponseMessage();
        message.setCode(RspCode.SUCCESS.getCode());
        message.setMsg(RspCode.SUCCESS.getDescription());
        message.setBean("");
        return message;
    }
    /***
    *<p>方法:sendOK 成功响应结果 </p>
    *<ul>
     *<li> @param bean TODO</li>
    *<li>@return com.dto.pojo.sys.ResponseMessage  </li>
    *<li>@author CuiLiang </li>
    *<li>@date 2018-11-12 12:51  </li>
    *</ul>
    */
    public static ResponseMessage sendOK(Object bean){
        ResponseMessage message = new ResponseMessage();
        message.setCode(RspCode.SUCCESS.getCode());
        message.setMsg(RspCode.SUCCESS.getDescription());
        message.setBean(bean);
        return message;
    }
    /***
     *<p>方法:sendDefined 自定义响应结果，仅带消息 </p>
     *<ul>
     *<li> @param rspCode TODO</li>
     *<li> @param bean TODO</li>
     *<li>@return com.dto.pojo.sys.ResponseMessage  </li>
     *<li>@author CuiLiang </li>
     *<li>@date 2018-11-12 12:52  </li>
     *</ul>
     */
    public static ResponseMessage sendDefined(RspCode rspCode){
        ResponseMessage message = new ResponseMessage();
        message.setCode(rspCode.getCode());
        message.setMsg(rspCode.getDescription());
        message.setBean("");
        return message;
    }
    /***
    *<p>方法:sendDefined 自定义响应结果，带实体 </p>
    *<ul>
     *<li> @param rspCode TODO</li>
     *<li> @param bean TODO</li>
    *<li>@return com.dto.pojo.sys.ResponseMessage  </li>
    *<li>@author CuiLiang </li>
    *<li>@date 2018-11-12 12:52  </li>
    *</ul>
    */
    public static ResponseMessage sendDefined(RspCode rspCode, Object bean){
        ResponseMessage message = new ResponseMessage();
        message.setCode(rspCode.getCode());
        message.setMsg(rspCode.getDescription());
        message.setBean(bean);
        return message;
    }
  
}
