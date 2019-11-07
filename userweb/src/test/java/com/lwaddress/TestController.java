package com.lwaddress;

import com.service.antiEncode.EncodeStartWayService;
import com.service.antiEncode.InsertEncodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private EncodeStartWayService encodeStartWayService;
    @Autowired
    private InsertEncodeService insertEncodeService;

    @Test
    public void test() {
        log.debug("========================测试开始==========================");
        insertEncodeService.errorProcess();
        log.debug("========================测试结束==========================");
    }
}
