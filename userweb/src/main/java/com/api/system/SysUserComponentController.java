package com.api.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dao.db1.system.StatChartConfigMapper;
import com.dao.db1.system.SysComponentMessageMapper;
import com.dao.db1.system.SysUserComponentMapper;
import com.dao.db1.zjstat.entity.StatChartConfig;
import com.dao.entity.system.SysComponentMessage;
import com.dao.entity.system.SysUserComponent;
import com.dto.pojo.spsys.ResponseMessage;
import com.service.system.SysUserComponentService;
import io.swagger.annotations.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 用户关联组件接口控制类
 * Created by Xiezx on 2019-01-21.
 */
@Api(tags = "用户关联组件资源")
@RestController
@RequestMapping("sysapi/UserComponent")
public class SysUserComponentController {
    @Autowired
    private SysUserComponentService sysUserComponentService;
    @Autowired
    private StatChartConfigMapper chartConfigMapper;

    @Autowired
    SysUserComponentMapper sysUserComponentMapper;

    @Autowired
    SysComponentMessageMapper sysComponentMessageMapper;


    @ApiOperation(value = "用户组件详情，保存", notes = "用户组件详情，保存")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage insert(@RequestBody String data, HttpServletRequest req) {
        JSONObject jsonObject = JSON.parseObject(data);
        String userId = jsonObject.getString("userId");
        //根据用户id查询该用户是否已经存在
        SysUserComponent sysUserComponent_user = sysUserComponentService.selectByPrimaryKey(userId);
        SysUserComponent sysUserComponent = JSON.parseObject(data, new TypeReference<SysUserComponent>() {
        });
        //用户不存在，新增
        if (sysUserComponent_user == null) {
            int insert = sysUserComponentService.insert(sysUserComponent);
            if (insert == 1) {
                return ResponseMessage.sendOK(sysUserComponent);
            } else {
                return ResponseMessage.sendError();
            }
        }
        //存在，修改
        else {
            int update = sysUserComponentService.updateByPrimaryKey(sysUserComponent);
            if (update == 1) {
                return ResponseMessage.sendOK(sysUserComponent);
            } else {
                return ResponseMessage.sendError();
            }
        }
    }

    @ApiOperation(value = "用户组件列表，根据用户id查询", notes = "用户组件列表，根据用户id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "用户主键id")
    })
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public ResponseMessage detail(String id) {
        SysUserComponent sysUserComponent = sysUserComponentService.selectByPrimaryKey(id);
        List<String> containerId = new ArrayList<>();
        if (sysUserComponent != null) {
            containerId.add(sysUserComponent.getContainer1());
            containerId.add(sysUserComponent.getContainer2());
            containerId.add(sysUserComponent.getContainer3());
            containerId.add(sysUserComponent.getContainer4());
            containerId.add(sysUserComponent.getContainer5());
            containerId.add(sysUserComponent.getContainer6());
            containerId.add(sysUserComponent.getContainer7());
            containerId.add(sysUserComponent.getContainer8());
            containerId.add(sysUserComponent.getContainer9());
        }
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < containerId.size(); i++) {
//            JSONObject treeObject = new JSONObject(true);
//            if (containerId.get(i)==null){
//                treeObject.put("containerId",null);
//                treeObject.put("order",i+1);
//            }else {
//                SysResource sysResource = sysResourceService.selectByPrimaryKey(containerId.get(i));
//                treeObject.put("containerId",containerId.get(i));
//                treeObject.put("userId",id);
//                treeObject.put("name",sysResource.getName());
//                treeObject.put("component",sysResource.getComponent());
//                treeObject.put("order",i+1);
//        }
//            list.add(treeObject);
            if (containerId.get(i) == null) {
                list.add(null);
            } else {
                StatChartConfig statChartConfig = chartConfigMapper.selectByPrimaryKey(containerId.get(i));
                list.add(statChartConfig);
            }
        }

        return ResponseMessage.sendOK(list);
    }

    @ApiOperation(value = "首页删除功能", notes = "首页删除功能")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", required = true, dataType = "String", value = ""),
            @ApiImplicitParam(paramType = "query", name = "index", required = true, dataType = "int", value = "")
    })
    @RequestMapping(value = "detele", method = RequestMethod.POST)
    public ResponseMessage detele(String id, int index) {
        SysUserComponent sysUserComponent = new SysUserComponent();
        sysUserComponent.setUserId(id);
        if (Integer.valueOf(index) == 1) {
            sysUserComponent.setContainer1("");
        } else if (Integer.valueOf(index) == 2) {
            sysUserComponent.setContainer2("");
        } else if (Integer.valueOf(index) == 3) {
            sysUserComponent.setContainer3("");
        } else if (Integer.valueOf(index) == 4) {
            sysUserComponent.setContainer4("");
        } else if (Integer.valueOf(index) == 5) {
            sysUserComponent.setContainer5("");
        } else if (Integer.valueOf(index) == 6) {
            sysUserComponent.setContainer6("");
        } else if (Integer.valueOf(index) == 7) {
            sysUserComponent.setContainer7("");
        } else if (Integer.valueOf(index) == 8) {
            sysUserComponent.setContainer8("");
        } else if (Integer.valueOf(index) == 9) {
            sysUserComponent.setContainer9("");
        }

        if (sysUserComponentService.updateByUserId(sysUserComponent) == 1) {
            return ResponseMessage.sendOK();
        } else {
            return ResponseMessage.sendError();
        }

    }

    @ApiOperation(value = "用户组件详情，保存具体信息", notes = "用户组件详情，保存具体信息")
    @RequestMapping(value = "insertMessage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage insertMessage(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
        //String str = JSON.toJSONString(data);
        JSONObject jsonObject = JSON.parseObject(data);
        Object jsonArray = jsonObject.get("arr");
        List<SysComponentMessage> list1 = JSON.parseArray(jsonArray + "", SysComponentMessage.class);

        String userId = jsonObject.getString("userId");

        SysComponentMessage sysComponentMessage = new SysComponentMessage();
        sysComponentMessage.setUserid(userId);


        for (SysComponentMessage sys:list1) {
            if (sys.getContainer1() != null) {
                sysComponentMessage.setContainer("container1");
            } else if (sys.getContainer2() != null) {
                sysComponentMessage.setContainer("container2");
            }
            else if (sys.getContainer3() != null) {
                sysComponentMessage.setContainer("container3");
            }
            else if (sys.getContainer4() != null) {
                sysComponentMessage.setContainer("container4");
            }
            else if (sys.getContainer5() != null) {
                sysComponentMessage.setContainer("container5");
            }
            else if (sys.getContainer6() != null) {
                sysComponentMessage.setContainer("container6");
            }
            else if (sys.getContainer7() != null) {
                sysComponentMessage.setContainer("container7");
            }
            else if (sys.getContainer8() != null) {
                sysComponentMessage.setContainer("container8");
            }
            else if (sys.getContainer9() != null) {
                sysComponentMessage.setContainer("container9");
            }
            sysComponentMessage.setW(sys.getW());
            sysComponentMessage.setI(sys.getI());
            sysComponentMessage.setH(sys.getH());
            sysComponentMessage.setX(sys.getX());
            sysComponentMessage.setY(sys.getY());

            SysComponentMessage sysComponentMessage2 = sysComponentMessageMapper.selectByUserId(sysComponentMessage.getUserid(), sysComponentMessage.getContainer());
            if (sysComponentMessage2 != null) {
                int i = sysComponentMessageMapper.updateByUserid(sysComponentMessage);
                if (i != 1) {
                    return ResponseMessage.sendError();
                }
            } else {
                //sysComponentMessage.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
                int i = sysComponentMessageMapper.insert(sysComponentMessage);
                if (i != 1) {
                    return ResponseMessage.sendError();
                }
            }
        }
        /*for (int j = 1;j <= 9;j++) {

            sysComponentMessage.setContainer("container" + j);
            sysComponentMessage.setX(list1.get(j).getX().toString());
            sysComponentMessage.setY(list1.get(j).getY().toString());
            sysComponentMessage.setH(list1.get(j).getH().toString());
            sysComponentMessage.setI(list1.get(j).getI().toString());
            sysComponentMessage.setW(list1.get(j).getW().toString());
            SysComponentMessage sysComponentMessage2 = sysComponentMessageMapper.selectByUserId(sysComponentMessage.getUserid(), sysComponentMessage.getContainer());
            if (sysComponentMessage2 != null) {
                int i = sysComponentMessageMapper.updateByUserid(sysComponentMessage);
                if (i != 1) {
                    return ResponseMessage.sendError();
                }
            } else {
                //sysComponentMessage.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
                int i = sysComponentMessageMapper.insert(sysComponentMessage);
                if (i != 1) {
                    return ResponseMessage.sendError();
                }
            }

        }*/
        return ResponseMessage.sendOK();
    }

    @ApiOperation(value = "用户组件列表，根据用户id查询具体信息", notes = "用户组件列表，根据用户id查询具体信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, dataType = "String", value = "用户主键id")
    })
    @RequestMapping(value = "detailMessage", method = RequestMethod.POST)
    public ResponseMessage detailMessage(String id) {
        SysUserComponent sysUserComponent = sysUserComponentService.selectByPrimaryKey(id);
        if(sysUserComponent == null) {
            SysUserComponent sysUserComponent1 = new SysUserComponent();
            sysUserComponent1.setUserId(id);
            sysUserComponent1.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            int i = sysUserComponentMapper.insert(sysUserComponent1);
            sysUserComponent = sysUserComponentService.selectByPrimaryKey(id);
        }
            List<String> containerId = new ArrayList<>();
            containerId.add(sysUserComponent.getContainer1());
            containerId.add(sysUserComponent.getContainer2());
            containerId.add(sysUserComponent.getContainer3());
            containerId.add(sysUserComponent.getContainer4());
            containerId.add(sysUserComponent.getContainer5());
            containerId.add(sysUserComponent.getContainer6());
            containerId.add(sysUserComponent.getContainer7());
            containerId.add(sysUserComponent.getContainer8());
            containerId.add(sysUserComponent.getContainer9());

            List<Map> list = new ArrayList<>();
            Map map = new HashMap();
            for (int i = 0; i < containerId.size(); i++) {

                /* if (containerId.get(i) == null) {
                 *//* if (sysComponentMessage == null) {
                    map.put("x", Integer.parseInt(sysComponentMessage.getX()));
                    map.put("y", Integer.parseInt(sysComponentMessage.getY()));
                    map.put("h", Integer.parseInt(sysComponentMessage.getH()));
                    map.put("i", Integer.parseInt(sysComponentMessage.getI()));
                    map.put("w", Integer.parseInt(sysComponentMessage.getW()));
                }*//*
                map.put("x", "null");
                map.put("y", "null");
                map.put("h", "null");
                map.put("i", "null");
                map.put("w", "null");
                map.put("option", null);
                list.add(JSON.parseObject(JSON.toJSONString(map), new TypeReference<Map>() {
                }));
                map.clear();
            } else {*/
                StatChartConfig statChartConfig = chartConfigMapper.selectByPrimaryKey(containerId.get(i));
                SysComponentMessage sysComponentMessage = sysComponentMessageMapper.selectByUserId(id,"container" + (i + 1));
                //System.out.println(sysComponentMessage.toString());
                if (sysComponentMessage != null) {
                    map.put("x", Integer.parseInt(sysComponentMessage.getX()));
                    map.put("y", Integer.parseInt(sysComponentMessage.getY()));
                    map.put("h", Integer.parseInt(sysComponentMessage.getH()));
                    map.put("i", Integer.parseInt(sysComponentMessage.getI()));
                    map.put("w", Integer.parseInt(sysComponentMessage.getW()));
                } else {
                    map.put("x", "null");
                    map.put("y", "null");
                    map.put("h", "null");
                    map.put("i", "null");
                    map.put("w", "null");
                }
               /* if (statChartConfig != null) {
                    map.put("option", JSON.parseObject(JSON.toJSONString(statChartConfig), new TypeReference<StatChartConfig>() {
                    }));
                } else {
                    map.put("option","null");
                }*/
                map.put("option", JSON.parseObject(JSON.toJSONString(statChartConfig), new TypeReference<StatChartConfig>() {
                }));
                list.add(JSON.parseObject(JSON.toJSONString(map), new TypeReference<Map>() {
                }));
                map.clear();

                /*}*/
            }
        return ResponseMessage.sendOK(JSON.parseObject(JSON.toJSONString(list), new TypeReference<List<Map>>() {
        }));

    }

    @ApiOperation(value = "用户组件详情，更新", notes = "用户组件详情，更新")
    @RequestMapping(value = "saveByContain", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateContain(@RequestBody String data, HttpServletRequest req) {
        //JSONObject jsonObject = JSON.parseObject(data);
        SysUserComponent sysUserComponent = JSON.parseObject(data, new TypeReference<SysUserComponent>(){});
        System.out.println(sysUserComponent.toString());
        //String userId = jsonObject.getString("userId");
        //根据用户id查询该用户是否已经存在
        int i  = sysUserComponentMapper.updateByUserId(sysUserComponent);
        String str = null;
        if (sysUserComponent.getContainer1() != null) {
            str = sysUserComponent.getContainer1();
        } else if (sysUserComponent.getContainer2() != null) {
            str = sysUserComponent.getContainer2();
        }else if (sysUserComponent.getContainer3() != null) {
            str = sysUserComponent.getContainer3();
        }else if (sysUserComponent.getContainer4() != null) {
            str = sysUserComponent.getContainer4();
        }else if (sysUserComponent.getContainer5() != null) {
            str = sysUserComponent.getContainer5();
        }else if (sysUserComponent.getContainer6() != null) {
            str = sysUserComponent.getContainer6();
        }else if (sysUserComponent.getContainer7() != null) {
            str = sysUserComponent.getContainer7();
        }else if (sysUserComponent.getContainer8() != null) {
            str = sysUserComponent.getContainer8();
        }else if (sysUserComponent.getContainer9() != null) {
            str = sysUserComponent.getContainer9();
        }
        StatChartConfig statChartConfig = chartConfigMapper.selectByPrimaryKey(str);
        Map map = new HashMap();
        map.put("option",statChartConfig);
        return ResponseMessage.sendOK(JSON.parseObject(JSON.toJSONString(map), new TypeReference<Map>() {}));
    }
}
