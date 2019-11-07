package com.service.lwaddress;

import java.util.Map;

public interface StringParsingService {
    //字符串处理
    Map stringParse(String shortAddress);
    /*繁简体转化*/
    String   charBig5ToChinese(String s);

}
