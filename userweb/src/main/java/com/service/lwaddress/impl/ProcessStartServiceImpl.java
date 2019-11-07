package com.service.lwaddress.impl;

import com.config.HgApplicationProperty;
import com.dao.db2.lwaddress.Base_addrMapper;
import com.dto.pojo.lwaddr.AddressName;
import com.service.lwaddress.ProcessInterfService;
import com.service.lwaddress.ProcessStartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProcessStartServiceImpl implements ProcessStartService {

    @Autowired
    private HgApplicationProperty applicationProperty;
    @Autowired
    private ProcessInterfService processInterfService;
    @Autowired
    private Base_addrMapper bs_addrMapper;

    private static final Logger log = LoggerFactory.getLogger(ProcessStartServiceImpl.class);


    /**
     *
     * @param start 开始下标
     * @param total 处理量
     * @param batchcCount 步进值
     */
    @Override
    public void startway(int start, int total, int batchcCount) {
//        String insertindex=applicationProperty.getInsertIndex();
//        String delectTableIndex=applicationProperty.getdelectTableIndex();

        log.info("start susscces");
        String reg = getReg();
        BigDecimal n=new BigDecimal(applicationProperty.getInsertWeight());
        for (int j = 0; j < total / batchcCount+1; j++) {
            log.info("current j:" + j);
            if (batchcCount != 1) {
                if (j == total / batchcCount ) {
                    if(total%batchcCount==0 && total-start/batchcCount==1){
                        processInterfService.processInterf(start, batchcCount,n,reg);
                        log.info("finish susscces");
                        break;
                    }
                    batchcCount = total - j  * batchcCount;
                }
                if (batchcCount == 0 || start ==total) {
                    log.info("success");
                    break;
                }
                processInterfService.processInterf(start, batchcCount,n,reg);
                start = start + batchcCount;
                log.info("finish susscces");
            }
            else{
                if(total-start / batchcCount==0){
                    log.info("success");
                    break;
                }
                processInterfService.processInterf(start, batchcCount,n,reg);
                start = start + batchcCount;
                log.info("finish susscces");
            }
        }
        log.info("finish susscces");
         /*   if (delectTableIndex.equals("1"))
        base_addrMapper.truncateTable("sec_addr");
*/
    }

    //配置的区的省市区街道关键字正则生成，并去除对应参数中包含的关键字
    public String getReg() {
        //配置的市的编号
        String cityCode = applicationProperty.getCityCode();
        StringBuilder regex = new StringBuilder();

        //查询市
        AddressName cityName = bs_addrMapper.getCity(cityCode);
        regex.append(cityName.getName()).append("|").append(cityName.getShortName());

        //查询省
        AddressName provinceName = bs_addrMapper.getProvince(cityName.getCode());
        regex.append("|").append(provinceName.getName()).append("|").append(provinceName.getShortName());

        //查询区
        List<AddressName> areaNames = bs_addrMapper.getArea(cityCode);

        for (AddressName areaName : areaNames) {
            regex.append("|").append(areaName.getName()).append("|").append(areaName.getShortName());
            //查询街道
            List<AddressName> streetNames = bs_addrMapper.getStreetName(areaName.getCode());
            for (AddressName streetName : streetNames) {
                regex.append("|").append(streetName.getName()).append("|").append(streetName.getShortName());
            }
        }
        log.info(regex.toString());
        return regex.toString();
    }
}
