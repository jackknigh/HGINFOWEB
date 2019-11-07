package com.service.system;

import com.dao.entity.system.SysDepart;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * SysDepart接口
 * Created by Xiezx on 2019-01-09.
 */
public interface SysDepartService {
    /**
     * 根据id批量删除机构
     *
     * @param ids
     * @return 1代表成功
     * @date 2019-01-9
     */
    int deleteByPrimaryKey(List<String> ids);

    /**
     * 新增机构
     *
     * @param record 实体类
     * @return 1代表成功
     * @date 2019-01-9
     */
    int insert(SysDepart record);

    /**
     * 根据id查询机构
     *
     * @param id
     * @return 1代表成功
     * @date 2019-01-9
     */
    List<Object> selectByPrimaryKey(String id);

    /**
     * 修改机构
     *
     * @param sysDepart 实体类
     * @return 1代表成功
     * @date 2019-01-8
     */
    int updateByPrimaryKey(SysDepart sysDepart);

    /**
     * 保存机构
     *
     * @param sysDepart
     * @return 1代表成功
     * @date 2019-01-09
     */
    int save(SysDepart sysDepart);

    /**
     * 根据条件分页查询机构
     *
     * @param queryMap 集合
     * @return 实体类集合
     * @date 2019-01-09
     */
    PageInfo<SysDepart> search(Map<String, Object> queryMap);

    /**
     * 查询所有顶级机构名称
     *
     * @param queryMap
     * @return list集合
     * @date 2019-01-09
     */
    List<Object> selectAllTopDepart(Map<String, Object> queryMap);

    /**
     * 查询所有顶级机构名称
     *
     * @return list集合
     * @date 2019-01-09
     */
    List<SysDepart> selectAllTopDepart();

    /**
     * 根据id查询所有子机构名称
     *
     * @param id
     * @return list集合
     * @date 2019-01-09
     */
//    List<SysDepart> selectAllChildren(String id);

    //*********************************************************部门编码*******************************************************
    /***
     *<p>方法:查询所有的子机构代码,不包含自身 TODO </p>
     *<ul>
     *<li> @param  当前机构编号12位</li>
     *<li>@return java.util.List<com.dao.entity.zjreport.RepDepConfig>  </li>
     *<li>@author CuiLiang </li>
     *<li>@date 2018-12-12 8:39  </li>
     *</ul>
     */
    List<SysDepart> selectAll(String depCode);
    /***
     *<p>方法:查询所有的子机构代码,包含自身 TODO </p>
     *<ul>
     *<li> @param  当前机构编号12位</li>
     *<li>@return java.util.List<com.dao.entity.zjreport.RepDepConfig>  </li>
     *<li>@author CuiLiang </li>
     *<li>@date 2018-12-12 8:39  </li>
     *</ul>
     */
    List<SysDepart> selectAllWithSelf(String depCode);
    /***
    *<p>方法:selectAllLikeWithSelf 包含自身的like查询 </p>
    *<ul>
     *<li> @param childDepCodeLikeAndDeep 直接传带%的字符串</li>
    *<li>@return java.util.List<com.dao.entity.system.SysDepart>  </li>
    *<li>@author CuiLiang @date 2019-03-25 11:29  </li>
    *</ul>
    */
    List<SysDepart> selectAllLikeWithSelf(String depCodeLike);
    /***
     *<p>方法:selectAllLikeWithSelf 不包含自身的like查询 </p>
     *<ul>
     *<li> @param childDepCodeLikeAndDeep 直接传带%的字符串</li>
     *<li>@return java.util.List<com.dao.entity.system.SysDepart>  </li>
     *<li>@author CuiLiang @date 2019-03-25 11:29  </li>
     *</ul>
     */
    List<SysDepart> selectAllLike(String depCodeLike,String depCode);
    /***
     *<p>方法:查询所有的子机构代码层级嵌套 TODO </p>
     *<ul>
     *<li> @param  当前机构编号12位</li>
     *<li>@return java.util.List<com.dao.entity.zjreport.RepDepConfig>  </li>
     *<li>@author CuiLiang </li>
     *<li>@date 2018-12-12 8:39  </li>
     *</ul>
     */
   SysDepart selectAllTree(String depCode);

    /***
     *<p>方法:查询所有的子机构代码 TODO </p>
     *<ul>
     *<li> @param  当前机构编号12位</li>
     *<li>@return java.util.List<com.dao.entity.zjreport.RepDepConfig>  </li>
     *<li>@author CuiLiang </li>
     *<li>@date 2018-12-12 8:39  </li>
     *</ul>
     */
    List<SysDepart> selectFirstChildAll(String depCode);
    /***
     *<p>方法:selectAllRepCode 查询所有后台自动生成期初报表的叶子单位 </p>
     *<ul>
     *<li> @param  TODO</li>
     *<li>@return java.util.List<com.dao.entity.zjreport.RepDepConfig>  </li>
     *<li>@author CuiLiang </li>
     *<li>@date 2018-12-12 8:40  </li>
     *</ul>
     */
    List<SysDepart> selectAllRepCode();

    /***
     *<p>方法:selectByDepCode 查询详情 </p>
     *<ul>
     *<li> @param depCode TODO</li>
     *<li>@author CuiLiang </li>
     *<li>@date 2018-12-12 8:51  </li>
     *</ul>
     */
    SysDepart selectByDepCode( String depCode);

    String updateStateCloseByCodes(List<String> ids);

    String updateStateOpenByCodes(List<String> ids);

    SysDepart listAll(String depcode);


}
