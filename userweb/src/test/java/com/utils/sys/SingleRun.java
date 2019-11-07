package com.utils.sys;

import com.alibaba.fastjson.JSON;
import com.dao.entity.zjreport.RepDepConfig;
import com.dao.entity.zjreport.RepMonth;
import com.dao.entity.zjreport.RepMonthDetail;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by CuiL on 2018-12-29.
 */
public class SingleRun {

    private static String _type = "34";
    private static String _tableName = "34KS0004";
    private static String _tableNo = "公 业   32   表 ";
    private static String _docNo = "公通字[2014]49号";

    private static Integer rowCount = 23;//第24行为特殊处理，到时候注意！！！！！

    private static String _sheetName = "sheet1";
    private static String _sysFlg = "SYS";
    private static String _editFlg = "EDIT";
    private static String _updateFlg = "add";
    private static String _updateFlgEdit = "update";

    @Test
    public void listTest(){
//此处根据是否根节点判断，不是所有的单位都生成的
        List<RepDepConfig> repDepConfigs = null;
        Integer _nowCount = 0;
        List<RepMonth> repMonths_sys = new ArrayList<>();
        List<RepMonth> repMonths_edit = new ArrayList<>();
        List<RepMonthDetail> repMonthDetails_sys = new ArrayList<>();
        List<RepMonthDetail> repMonthDetails_edit = new ArrayList<>();

        RepMonth repMonth_sys;
        RepMonth repMonth_edit;
        RepMonthDetail repMonthDetail_sys;
        RepMonthDetail repMonthDetail_edit;
        for (RepDepConfig repDepConfig : repDepConfigs) {
            //2、初始化报表实体list
            repMonth_sys = new RepMonth();
            repMonth_edit = new RepMonth();

            String _sysId = GenUtil.getUUID();
            String _editId = GenUtil.getUUID();
            Date _date = new Date();
            //3、汇总数据
            //4、报表实体赋值

            repMonth_sys.setId(_sysId);
            repMonth_sys.setCreatetime(_date);
            repMonth_sys.setLoaddata(_date);
            repMonth_sys.setFinaldata(_date);
            repMonth_sys.setFinalName("");
            repMonth_sys.setDepCode(repDepConfig.getDepCode());
            repMonth_sys.setCreator(repDepConfig.getDepCode());
            repMonth_sys.setDocNo(_docNo);
            repMonth_sys.setTabName(_tableName);
            repMonth_sys.setTableNo(_tableNo);
            repMonth_sys.setBakName("");
            repMonth_sys.setInputdate(_date);
            repMonth_sys.setLinkowid(_editId);
            repMonth_sys.setRemark(_sysFlg);
            repMonth_sys.setType(_type);
            repMonth_sys.setState(0);
            repMonth_sys.setUpdateFlg(_updateFlg);

            repMonth_edit.setId(_editId);
            repMonth_edit.setCreatetime(_date);
            repMonth_edit.setLoaddata(_date);
            repMonth_edit.setFinaldata(_date);
            repMonth_edit.setFinalName("");
            repMonth_edit.setDepCode(repDepConfig.getDepCode());
            repMonth_edit.setCreator(repDepConfig.getDepCode());
            repMonth_edit.setDocNo(_docNo);
            repMonth_edit.setTabName(_tableName);
            repMonth_edit.setTableNo(_tableNo);
            repMonth_edit.setBakName("");
            repMonth_edit.setInputdate(_date);
            repMonth_edit.setLinkowid(_sysId);
            repMonth_edit.setRemark(_editFlg);
            repMonth_edit.setType(_type);
            repMonth_edit.setState(0);
            repMonth_edit.setUpdateFlg(_updateFlg);
            //表格矩形区域1为20*20的矩形
            for (int i = 1; i <= rowCount; i++) {
                String _sysDetailId = GenUtil.getUUID();
                String _editDetailId = GenUtil.getUUID();
                repMonthDetail_sys = new RepMonthDetail();
                repMonthDetail_edit = new RepMonthDetail();
                repMonthDetail_sys.setId(_sysDetailId);
                repMonthDetail_sys.setRepMonthRefId(_sysId);
                repMonthDetail_sys.setSheetName(_sheetName);
                repMonthDetail_sys.setSheetSort(1);
                repMonthDetail_sys.setSort(i);
                repMonthDetail_sys.setLinkowid(_editDetailId);
                repMonthDetail_sys.setType(_type);
                repMonthDetail_sys.setRemark(_sysFlg);
                repMonthDetail_sys.setDepCode(repDepConfig.getDepCode());
                repMonthDetail_sys.setCreatetime(_date);
                repMonthDetail_sys.setCreator(repDepConfig.getDepCode());
                repMonthDetail_sys.setState(0);
                repMonthDetail_sys.setValue2(Integer.valueOf((int) (Math.random() * 100)).toString());
                repMonthDetail_sys.setValue3(Integer.valueOf((int) (Math.random() * 100)).toString());
                repMonthDetail_sys.setValue4(Integer.valueOf((int) (Math.random() * 100)).toString());
                repMonthDetail_sys.setValue5(Integer.valueOf((int) (Math.random() * 100)).toString());
                repMonthDetail_sys.setValue1(MathUtils.strAddStr(new String[]{repMonthDetail_sys.getValue3(), repMonthDetail_sys.getValue4(), repMonthDetail_sys.getValue5()}));
                repMonthDetail_sys.setUpdateFlg(_updateFlg);

                repMonthDetail_edit.setId(_editDetailId);
                repMonthDetail_edit.setRepMonthRefId(_editId);
                repMonthDetail_edit.setSheetName(_sheetName);
                repMonthDetail_edit.setSheetSort(1);
                repMonthDetail_edit.setSort(i);
                repMonthDetail_edit.setLinkowid(_sysDetailId);
                repMonthDetail_edit.setType(_type);
                repMonthDetail_edit.setRemark(_editFlg);
                repMonthDetail_edit.setDepCode(repDepConfig.getDepCode());
                repMonthDetail_edit.setCreatetime(_date);
                repMonthDetail_edit.setCreator(repDepConfig.getDepCode());
                repMonthDetail_edit.setState(0);
                repMonthDetail_edit.setValue2(Integer.valueOf((int) (Math.random() * 100)).toString());
                repMonthDetail_edit.setValue3(Integer.valueOf((int) (Math.random() * 100)).toString());
                repMonthDetail_edit.setValue4(Integer.valueOf((int) (Math.random() * 100)).toString());
                repMonthDetail_edit.setValue5(Integer.valueOf((int) (Math.random() * 100)).toString());
                repMonthDetail_edit.setValue1(MathUtils.strAddStr(new String[]{repMonthDetail_edit.getValue3(), repMonthDetail_edit.getValue4(), repMonthDetail_edit.getValue5()}));

                repMonthDetail_edit.setUpdateFlg(_updateFlg);

                repMonthDetails_sys.add(repMonthDetail_sys);
                repMonthDetails_edit.add(repMonthDetail_edit);
            }
            //24行特殊处理



            repMonths_sys.add(repMonth_sys);
            repMonths_edit.add(repMonth_edit);
            _nowCount++;
        }


    }

    @Test
    public void rowTest(){

    }

    @Test
    public void cloneTest(){
        RepMonth repMonth_sys;
        RepMonth repMonth_edit;
        String _sysId = GenUtil.getUUID();
        String _editId = GenUtil.getUUID();
        Date _date = new Date();
        repMonth_sys = new RepMonth();
        repMonth_edit = new RepMonth();
        repMonth_sys.setId(_sysId);
        repMonth_sys.setCreatetime(_date);
        repMonth_sys.setLoaddata(_date);
        repMonth_sys.setFinaldata(_date);
        repMonth_sys.setFinalName("");
        repMonth_sys.setDocNo(_docNo);
        repMonth_sys.setTabName(_tableName);
        repMonth_sys.setTableNo(_tableNo);
        repMonth_sys.setBakName("");
        repMonth_sys.setInputdate(_date);
        repMonth_sys.setLinkowid(_editId);
        repMonth_sys.setRemark(_sysFlg);
        repMonth_sys.setType(_type);
        repMonth_sys.setState(0);
        repMonth_sys.setUpdateFlg(_updateFlg);
        System.out.println(JSON.toJSONString(repMonth_sys));
        BeanUtils.copyProperties(repMonth_sys,repMonth_edit);

        repMonth_edit.setId("new 123");
        repMonth_edit.setCreatetime(_date);
        repMonth_edit.setLoaddata(_date);
        repMonth_edit.setFinaldata(_date);
        repMonth_edit.setFinalName("33333333333333");
        repMonth_edit.setDocNo(_docNo);
        repMonth_edit.setTabName(_tableName);
        repMonth_edit.setTableNo(_tableNo);
        repMonth_edit.setBakName("");
        repMonth_edit.setInputdate(_date);
        repMonth_edit.setLinkowid(_editId);
        repMonth_edit.setRemark("32123123123");
        repMonth_edit.setType("4444444");
        repMonth_edit.setState(99999999);
        repMonth_edit.setUpdateFlg(_updateFlg);

        System.out.println(JSON.toJSONString(repMonth_edit));

    }
}
