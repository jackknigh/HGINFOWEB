package com.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.dao.db1.system.SysUserComponentMapper;
import com.dao.entity.system.SysResource;
import com.dao.entity.system.SysUserComponent;
import com.service.system.SysResourceService;
import com.service.system.SysUserComponentService;
import com.utils.sys.GenUtil;
import com.utils.sys.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Transactional
@Service("sysUserComponentService")
public class SysUserComponentServiceImpl implements SysUserComponentService {
    @Autowired
    private SysUserComponentMapper sysUserComponentMapper;

    @Autowired
    private SysResourceService sysResourceService;

    @Override
    public int deleteByPrimaryKey(List<String> ids) {
        return 0;
    }

    @Override
    public int insert(SysUserComponent record) {
        record.setId(TextUtils.getUUID());
        return sysUserComponentMapper.insert(record);
    }

    @Override
    public SysUserComponent selectByPrimaryKey(String id) {
        return sysUserComponentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysUserComponent> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(SysUserComponent record) {
        return sysUserComponentMapper.updateByPrimaryKey(record);
    }

    @Override
    public int save(SysUserComponent sysUserComponent) {
        int ret = 0;

        if ((sysUserComponent != null) && (!TextUtils.isEmpty(sysUserComponent.getUpdateFlg()))) {
            // 修改
            if (sysUserComponent.getUpdateFlg().equalsIgnoreCase("update")) {
                sysUserComponent.setUpdateDate(new Date());
                return sysUserComponentMapper.updateByPrimaryKey(sysUserComponent);
            }
            // 新增
            if (sysUserComponent.getUpdateFlg().equalsIgnoreCase("add")) {
                sysUserComponent.setId(GenUtil.getUUID());
                sysUserComponent.setCreateDate(new Date());
                return sysUserComponentMapper.insert(sysUserComponent);
            }
        }
        return ret;
    }

    /**
     * 初始化用户组件
     *
     * @param userId
     * @return
     */
    @Override
    public int init(String userId) {
        List<SysResource> list = sysResourceService.selectComponent(new HashMap<>());
        Map<String, String> map = new HashMap<>();
        map.put("id", GenUtil.getUUID());
        map.put("userId", userId);
        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                SysResource resource = list.get(i);
                if (Objects.nonNull(resource)) {
                    map.put("container" + String.valueOf(i + 1), resource.getId());
                }
            }
        }
        SysUserComponent sysUserComponent = JSONObject.parseObject(JSONObject.toJSONString(map), SysUserComponent.class);
        if (Objects.nonNull(sysUserComponent)) {
            return sysUserComponentMapper.insert(sysUserComponent);
        }
        return 0;
    }

    @Override
    public int updateByUserId(SysUserComponent sysUserComponent) {
        return sysUserComponentMapper.updateByUserId(sysUserComponent);
    }
}
