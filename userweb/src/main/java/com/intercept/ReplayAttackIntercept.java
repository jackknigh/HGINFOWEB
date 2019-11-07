package com.intercept;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ank
 * @version v 1.0
 * @title [重复攻击拦截器]
 * @ClassName: com.spinfosec.intercept.ReplayAttackIntercept
 * @description： 防重放攻击机制：客户端在请求的head中增加参数noncetimestamp，该参数的值为AES(timestamp#md5(uuid)),后台接收到该参数后，AES解密后获得时间戳和md5(uuid)，
 * 通过时间戳过滤掉60s以外的请求(前后端的时间需要同步)， 通过查询redis缓存来判断60s内的请求是否有效，判断方式：redis中存在传入的md5(uuid)，则是重复攻击，否则，
 * 不是并将md5(uuid)存入到redis缓存中，设置60s失效时间
 * @create 2018/10/9 14:16
 * @copyright Copyright(C) 2018 SHIPING INFO Corporation. All rights reserved.
 */
public class ReplayAttackIntercept implements HandlerInterceptor
{

    public static final String SPLIT_CHAR = "#";
    public static final Long MINUTE = 60 * 1000L;
    public static final String NAME_FOR_PARAM_AND_REDIS_SET_KEY = "noncetimestamp";

    private Logger logger = LoggerFactory.getLogger(ReplayAttackIntercept.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        return true;
//        // 获取重放攻击参数
//        String nonceTimestamp = request.getHeader(NAME_FOR_PARAM_AND_REDIS_SET_KEY);
//        // 解密重复攻击参数
//        if (StringUtils.isNotEmpty(nonceTimestamp))
//        {
//            logger.info("请求" + request.getRequestURI() + "的参数" + NAME_FOR_PARAM_AND_REDIS_SET_KEY + " : " + nonceTimestamp);
////            byte[] bytes = SM4Util.decryptEcbPadding(Utils.hexStringToBytes(SM4Util.KEY_TO_SM4), nonceTimestamp);
////            String decryptString = new String(bytes);
//            String decryptString = AESPython.Decrypt(nonceTimestamp, AESPython.SKEY);
//            String[] split = decryptString.split(SPLIT_CHAR);
//            String timestamp = split[0];
//            String md5Value = split[1];
//            // 比较当前时间和时间戳上的时间
//            long currentTimeMillis = System.currentTimeMillis();
//            long duringTime = Math.abs(currentTimeMillis - Long.parseLong(timestamp));
//            logger.info("请求时间和当前时间差：" +duringTime);
//            if (duringTime - MINUTE <= 0)
//            {
//                // 查询redis中数据
//                Object val = redisTemplate.opsForValue().get(md5Value);
//                if (null == val)
//                {
//                    // 将请求写入redis
//                    redisTemplate.opsForValue().set(md5Value, System.currentTimeMillis());
//                    redisTemplate.expire(md5Value, MINUTE, TimeUnit.MILLISECONDS);
//                    logger.info("请求" + request.getRequestURI() + "非重放攻击，通过！");
//                    return true;
//                }
//            }
//        }
//        logger.info("请求" + request.getRequestURI() + "属于重放攻击，被拦截");
//        return false;
    }

}
