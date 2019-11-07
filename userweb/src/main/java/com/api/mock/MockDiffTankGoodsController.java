package com.api.mock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.config.HgApplicationProperty;
import com.dao.db1.mock.*;
import com.dao.entity.mock.*;
import com.dto.pojo.sys.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@Api(tags = "决策系统MOCK数据接口")
@RestController
@RequestMapping("sysapi/Mock")
public class MockDiffTankGoodsController {
    @Autowired
    MockGoodsMapper mockGoodsMapper;
    @Autowired
    MockGuestMapper mockGuestMapper;
    @Autowired
    MockMessageMapper mockMessageMapper;
    @Autowired
    MockDateMapper mockDateMapper;
    @Autowired
    MockTmessageMapper mockTmessageMapper;

    @Autowired
    MockGoodMapper mockGoodMapper;
    @Autowired
    private HgApplicationProperty applicationProperty;



    @ApiOperation(value = "每日储罐货物计量表，Mock数据", notes = "每日储罐货物计量表，Mock数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "beginTime", required = true, dataType = "String", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", required = true, dataType = "String", value = "结束时间")
    })
    @RequestMapping(value = "mockDiffTankGoods", method = RequestMethod.POST)
    public ResponseMessage mockDiffTankGoods(String begainTime,String endTime) {

        List<MockGoods> listGoods = mockGoodsMapper.selectAll();
        List<MockGuest> listGuest = mockGuestMapper.selectAll();
        List<MockMessage> listMessage = mockMessageMapper.selectAll();
        List<MockGood> listGood = mockGoodMapper.selectAll();

        List listL = new ArrayList();
        List<Map<String,Object>> list = new ArrayList();
        List listM = new ArrayList();

        Map mapL = new HashMap();
        Map mapM = new HashMap();

       for (MockGoods goods: listGoods) {
          /* if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
               mapL.put("GoodsName",goods.getEnglish());
           } else {
               mapL.put("GoodsName",goods.getName());
           }*/
           mapL.put("GoodsEngName",goods.getEnglish());
           mapL.put("GoodsName",goods.getName());
           mapL.put("SumNumber",0);
           mapL.put("SumTotalqty",0);
           mapL.put("SumDiffTNum",0);
           mapL.put("SumPhysicalStock",0);
           for (MockGuest guest:listGuest) {
              for (MockMessage message : listMessage) {

                  if (message.getChinese().equals(goods.getName()) && message.getName().equals(guest.getName())) {
                     /* if (Integer.valueOf(applicationProperty.getChinOrEng().toString()) == 1) {
                          message.setChinese(message.getEnglish());
                      }*/
                      listM.add(message);
                  }
              }
              mapM.put("GuestName",guest.getName());
              mapM.put("DataList",JSON.parseObject(JSON.toJSONString(listM),new TypeReference<List>(){}));
              listL.add(JSON.parseObject(JSON.toJSONString(mapM),new TypeReference<Map>(){}));
              listM.clear();
              mapM.clear();
           }
           for (MockMessage message : listMessage) {
               if (message.getId().equals("2019-05-01") && message.getChinese().equals(goods.getName())) {
                   mapL.put("SumNumber", Double.parseDouble(mapL.get("SumNumber").toString()) + Double.parseDouble(message.getNum().toString()));
                   mapL.put("SumTotalqty", Double.parseDouble(mapL.get("SumTotalqty").toString()) + Double.parseDouble(message.getTota().toString()));
                   mapL.put("SumDiffTNum", Double.parseDouble(mapL.get("SumDiffTNum").toString()) + Double.parseDouble(message.getDiff().toString()));
                   mapL.put("SumPhysicalStock", Double.parseDouble(mapL.get("SumPhysicalStock").toString()) + Double.parseDouble(message.getPhy().toString()));
               }
           }
           mapL.put("SumNumber",new BigDecimal(Double.parseDouble(mapL.get("SumNumber").toString())).setScale(3,BigDecimal.ROUND_HALF_UP));
           mapL.put("SumTotalqty",new BigDecimal(Double.parseDouble(mapL.get("SumTotalqty").toString())).setScale(3,BigDecimal.ROUND_HALF_UP));
           mapL.put("SumDiffTNum",new BigDecimal(Double.parseDouble(mapL.get("SumDiffTNum").toString())).setScale(3,BigDecimal.ROUND_HALF_UP));
           mapL.put("SumPhysicalStock",new BigDecimal(Double.parseDouble(mapL.get("SumPhysicalStock").toString())).setScale(3,BigDecimal.ROUND_HALF_UP));
           mapL.put("GuestList", JSON.parseObject(JSON.toJSONString(listL),new TypeReference<List>(){}));
           list.add(JSON.parseObject(JSON.toJSONString(mapL),new TypeReference<Map>(){}));
           listL.clear();
           mapL.clear();
       }
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("SumNumber").toString()) ;//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("SumNumber").toString()) ; //name1是从你list里面拿出来的第二个name
                return -(name1.compareTo(name2));
            }
        });

        return ResponseMessage.sendOK(JSON.parseObject(JSON.toJSONString(list),new TypeReference<List>(){}));
    }

    @ApiOperation(value = "每日储罐货物计量表，每日不同罐", notes = "每日储罐货物计量表，每日不同罐")
    @RequestMapping(value = "mockDiffTankGoodsByTank", method = RequestMethod.POST)
    public ResponseMessage mockDiffTankGoodsByTank() {

        List<MockDate> listDate = mockDateMapper.selectAll();
        List<MockTmessage> listTmessage = mockTmessageMapper.selectAll();

        List listL = new ArrayList();
        List<Map<String,Object>> list = new ArrayList();

        Map mapL = new HashMap();
        Map mapM = new HashMap();

        for (MockDate date: listDate) {
            mapL.put("Date",date.getId());
            mapL.put("Sum",new BigDecimal(Double.parseDouble(date.getId().toString().substring(8).toString())).setScale(0,BigDecimal.ROUND_HALF_UP));
            for (MockTmessage message : listTmessage) {
                if (message.getDatem().equals(date.getId())) {
                    listL.add(message);
                }
            }
            mapL.put("TKList",JSON.parseObject(JSON.toJSONString(listL),new TypeReference<List>(){}));
            list.add(JSON.parseObject(JSON.toJSONString(mapL),new TypeReference<Map>(){}));
            mapL.clear();
            listL.clear();
        }
        for (MockDate date: listDate) {
            if (date.getId().equals("2019-05-15")) {
                mapL.put("Date","当日");
                mapL.put("Sum",0);
                for (MockTmessage message : listTmessage) {
                    if (message.getDatem().equals(date.getId())) {
                        listL.add(message);
                    }
                }
                mapL.put("TKList",JSON.parseObject(JSON.toJSONString(listL),new TypeReference<List>(){}));
                list.add(JSON.parseObject(JSON.toJSONString(mapL),new TypeReference<Map>(){}));
                mapL.clear();
                listL.clear();
            }
        }
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Double name1 = Double.valueOf(o1.get("Sum").toString()) ;//name1是从你list里面拿出来的一个
                Double name2 = Double.valueOf(o2.get("Sum").toString()) ; //name1是从你list里面拿出来的第二个name
                return (name1.compareTo(name2));
            }
        });

        return ResponseMessage.sendOK(JSON.parseObject(JSON.toJSONString(list),new TypeReference<List>(){}));
    }
}
