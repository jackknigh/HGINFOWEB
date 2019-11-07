package com.service.sys.impl;

import com.dto.pojo.spsys.LoginUserBean;
import com.dto.pojo.system.Token;
import com.service.sys.AuthSrv;
import com.utils.sys.GenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by CuiL on 2018-11-14.
 */
@Transactional
@Service("authSrv")
public class AuthSrvImpl implements AuthSrv {
    private static final Logger log = LoggerFactory.getLogger(AuthSrvImpl.class);

//    @Autowired
//    OperatorService operatorService;
    @Override
    public List<String> getHostIpByUserId(String userId) {
        return null;
    }

    @Override
    public Token getToken() {
        Token token = new Token();
        String tokenId = GenUtil.getUUID();
        token.setTokenId(tokenId);
        log.info("get a tocken success!"+tokenId);
        return token;
    }

    @Override
    public LoginUserBean getLoginUser(String accountId, String userName, String password) {
//        LoginUserBean loginUserBean = new LoginUserBean();
//        Map<String, Object> queryMap = new HashMap<String, Object>();
//        queryMap.put(Constants.PAGE_CURRENT,0);
//        queryMap.put(Constants.PAGE_SIZE,30);
//        queryMap.put("AccountId","Ourway");
//        queryMap.put("OperatorId","Admin");
//        queryMap.put("Password","Password=0x" + password);
//        if((operatorService.search(queryMap).getList() != null) && (operatorService.search(queryMap).getList().size()>0)){
//            Operator operator = operatorService.search(queryMap).getList().get(0);
//            loginUserBean.setId(operator.getOperatorid());
//            loginUserBean.setName(operator.getName());
//            loginUserBean.setUserName(operator.getName());
//            loginUserBean.setNic("");
//            loginUserBean.setPassword(operator.getPassword());
//            loginUserBean.setAccountId(operator.getAccountid());
//            return loginUserBean;
//        }




        return null;
    }
}
