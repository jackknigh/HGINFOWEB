package com.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.dao.db1.system.SysDepartMapper;
import com.dao.entity.system.SysDepart;
import com.dto.constants.Constants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.system.SysDepartService;
import com.utils.sys.DepCodeUtils;
import com.utils.sys.GenUtil;
import com.utils.sys.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SysDepart接口实现类
 *
 * @author Xiezx
 * @date 2019-01-09
 */
@Transactional
@Service("sysDepartService")
public class SysDepartServiceImpl implements SysDepartService {
    @Autowired
    private SysDepartMapper sysDepartMapper;

    @Override
    public int deleteByPrimaryKey(List<String> ids) {

        return sysDepartMapper.deleteByPrimaryKey(ids);
    }

    @Override
    public int insert(SysDepart record) {
        return sysDepartMapper.insert(record);
    }

    @Override
    public List<Object> selectByPrimaryKey(String id) {
        List<Object> parentIdList = new ArrayList<>();
        SysDepart sysDepart = sysDepartMapper.selectByPrimaryKey(id);
        parentIdList.add(sysDepart);
        if ("0".equals(sysDepart.getParentId())) {
        } else {
            List<Object> parent = getParent(sysDepart.getParentId());
            for (int i = 0; i < parent.size(); i++) {
                parentIdList.add(parent.get(i));
            }
        }
        return parentIdList;
    }

    @Override
    public List<SysDepart> selectAll(String depCode) {
        return sysDepartMapper.selectAll(DepCodeUtils.getAllChildDepCodeLike(depCode), depCode);
    }

    @Override
    public List<SysDepart> selectAllWithSelf(String depCode) {
        return sysDepartMapper.selectAll(DepCodeUtils.getAllChildDepCodeLike(depCode), null);
    }
    @Override
    public List<SysDepart> selectAllLikeWithSelf(String depCodeLike) {
        return sysDepartMapper.selectAll(depCodeLike, null);
    }

    @Override
    public List<SysDepart> selectAllLike(String depCodeLike, String depCode) {
        return sysDepartMapper.selectAll(depCodeLike, depCode);
    }

    @Override
    public int updateByPrimaryKey(SysDepart sysDepart) {
        return sysDepartMapper.updateByPrimaryKey(sysDepart);
    }

    @Override
    public int save(SysDepart sysDepart) {
        int ret = 0;

        if ((sysDepart != null) && (!TextUtils.isEmpty(sysDepart.getUpdateFlg()))) {
            sysDepart.setState(1);
            SysDepart exists = sysDepartMapper.selectByDepCode(sysDepart.getDepCode());
            // 修改
            if (sysDepart.getUpdateFlg().equalsIgnoreCase("update")) {
                if (null != exists && !exists.getId().equals(sysDepart.getId())) {
                    return -1;
                }
                sysDepart.setUpdateDate(new Date());
                return sysDepartMapper.updateByPrimaryKey(sysDepart);
            }
            // 新增
            if (sysDepart.getUpdateFlg().equalsIgnoreCase("add")) {
                if (null != exists) {
                    return -1;
                }
                sysDepart.setId(GenUtil.getUUID());
                sysDepart.setCreateDate(new Date());
                return sysDepartMapper.insert(sysDepart);
            }
        }
        return ret;
    }

    @Override
    public PageInfo<SysDepart> search(Map<String, Object> queryMap) {
        Integer pageNUm = (Integer) queryMap.get(Constants.PAGE_CURRENT);
        Integer pageSize = (Integer) queryMap.get(Constants.PAGE_SIZE);
        PageHelper.startPage(pageNUm, pageSize);
        List<SysDepart> search = sysDepartMapper.search(queryMap);
        PageInfo<SysDepart> pageList = new PageInfo<>(search);
        return pageList;
    }

    @Override
    public List<Object> selectAllTopDepart(Map<String, Object> queryMap) {
        List<SysDepart> sysDepartList = sysDepartMapper.selectAllTopDepart(queryMap);
        List<Object> list = new ArrayList<>();
        for (SysDepart sysDepart : sysDepartList) {
            JSONObject treeObject = new JSONObject(true);
            treeObject.put("label", sysDepart.getName());
            treeObject.put("value", sysDepart.getId());
            treeObject.put("depCode", sysDepart.getDepCode());
            treeObject.put("isDefault", false);
            treeObject.put("isDisabled", true);
            treeObject.put("checked", false);
            List<SysDepart> children = (List<SysDepart>) getChildren(sysDepart.getId());
            if (children.size() == 0) {
            } else {
                treeObject.put("children", children);
            }
            list.add(treeObject);
        }
        return list;
    }

    /**
     * 查询所有顶级机构名称
     *
     * @return list集合
     * @date 2019-01-09
     */
    @Override
    public List<SysDepart> selectAllTopDepart() {
        return sysDepartMapper.selectAllTopDepart(new HashMap<String, Object>());
    }

//    @Override
//    public List<SysDepart> selectAllChildren(String depcode) {
//        return sysDepartMapper.selectAllChildren(depcode);
//    }

    private Object getChildren(String depcode) {
        List<Object> list = new ArrayList<>();
        List<SysDepart> children = sysDepartMapper.selectAll(depcode,null);
        for (SysDepart sysDepart : children) {
            if (depcode.equals(sysDepart.getParentId())) {
                JSONObject obj = new JSONObject(true);
                obj.put("label", sysDepart.getName());
                obj.put("value", sysDepart.getId());
                obj.put("depCode", sysDepart.getDepCode());
                obj.put("isDefault", false);
                obj.put("isDisabled", true);
                obj.put("checked", false);
                List<SysDepart> _children = (List<SysDepart>) getChildren(sysDepart.getId());
                if (_children.size() == 0) {
                } else {
                    obj.put("children", _children);
                }
                list.add(obj);
            }
        }
        return list;


    }

    private List<Object> getParent(String id) {
        SysDepart sysDepart = sysDepartMapper.selectByPrimaryKey(id);
        List<Object> list = new ArrayList<>();
        list.add(sysDepart);
        if ("0".equals(sysDepart.getParentId())) {

        } else {
            List<Object> parent = getParent(sysDepart.getParentId());
            for (int i = 0; i < parent.size(); i++) {
                list.add(parent.get(i));
            }
        }
        return list;
    }
    //*****************************************************************部门编码***************************************

    @Override
    public SysDepart selectAllTree(String depCode) {
        List<SysDepart> sysDeparts = sysDepartMapper.selectAll(DepCodeUtils.getAllChildDepCodeLike(depCode), depCode);
        /***
         *********待开发的功能代码区域******************
         *<p>入参说明:TODO</p>
         *<p>说明:根据depCode的规则，将List转换为嵌套的树形RepDepConfig</p>
         *<p>出参说明:TODO</p>
         *bean=
         *@author CuiLiang
         */
        SysDepart sysDepart = sysDepartMapper.selectByDepCode(depCode);

        return listToTree(sysDepart, sysDeparts);

    }

    //根据depCode的规则，将List转换为嵌套的树形RepDepConfig
    private SysDepart listToTree(SysDepart sysDepart, List<SysDepart> sysDeparts) {

        //获取编号的头
        String allChildDepCode = DepCodeUtils.getAllChildDepCode(sysDepart.getDepCode());

        //确定该对象不存在子集直接返回
        if (allChildDepCode.length() >= 12) {//如果确认后两位肯定为0可以改成10，但是效率没有明显提升
            return sysDepart;
        }
        //生成这个编号对应的正则表达式
        String regex = "^" + allChildDepCode + "([1-9a-zA-Z][0-9a-zA-Z]|[0-9a-zA-Z][1-9a-zA-Z])[0-9a-zA-Z]{" + (10 - allChildDepCode.length()) + "}$";

        //声明一个集合存储当前对象的子集
        List<SysDepart> childRepDepConfigs = new ArrayList<>();
        //遍历整个集合寻找符合正则表达式的所有子集
        for (int i = 0; i < sysDeparts.size(); i++) {
            //通过比对正则表达式寻找子集
            if (sysDeparts.get(i).getDepCode().matches(regex)) {
                //添加到集合
                childRepDepConfigs.add(sysDeparts.get(i));

                //再通过递归找到子集的子集
                listToTree(sysDeparts.get(i), sysDeparts);
            }
        }
        //把子集添加到对应的父对象
        sysDepart.setChildRepDepConfigs(childRepDepConfigs);
        //打完收工
        return sysDepart;

    }

    @Override
    public List<SysDepart> selectFirstChildAll(String depCode) {
        String depCodeLike = DepCodeUtils.getFirstChildDepCodeLike(depCode);
        return sysDepartMapper.selectAll(depCodeLike, depCode);
    }

    @Override
    public List<SysDepart> selectAllRepCode() {
        return sysDepartMapper.selectAllRepCode();
    }

    @Override
    public SysDepart selectByDepCode(String depCode) {
        return sysDepartMapper.selectByDepCode(depCode);
    }

    @Override
    public String updateStateCloseByCodes(List<String> depCodes) {
        String resStr = "success";
        sysDepartMapper.updateStateByCodes(depCodes, 0);

        return resStr;
    }

    @Override
    public String updateStateOpenByCodes(List<String> depCodes) {
        String resStr = "success";
        sysDepartMapper.updateStateByCodes(depCodes, 1);
        return resStr;
    }

    @Override
    public SysDepart listAll(String depcode) {
        String depCode = "330000000000";
        SysDepart sysDepart = sysDepartMapper.selectByDepCode(depCode);
        List<SysDepart> sysDeparts = sysDepartMapper.selectAll(DepCodeUtils.getFirstChildDepCodeLike(depCode), depCode);
        sysDepart.setChildRepDepConfigs(sysDeparts);
        return sysDepart;
    }
}
