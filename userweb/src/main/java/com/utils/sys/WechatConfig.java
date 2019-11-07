package com.utils.sys;

import java.util.HashMap;
import java.util.Map;

public class WechatConfig {

    public static final String APP_ID = "wxda565abda7afcf41";//wx107f7a12124fdbd1
    public static final String APP_SECRET = "ba4691cda0e2f4d7812392ae468988f5";//2b96f1f14a7508627aa46eee5cfe8585

    //文件访问路径
    public static final String FILE_PATH = "";
    public static final String SOUND_PATH = FILE_PATH + "sound/";
    /*微信访问外网地址*/
    private static final String BASE_URL = "";

    /*用来做不同微信号，采用开发者模式后，通过不同的接口来处理里面的消息，若未设置，通过系统默认接口*/
    public static Map<String, String> APP_MSG_MAP = new HashMap<String, String>();

    /*微信相关的URL*/
    public final class WechatApiURL {
        /*会话中的媒体文件下载*/
        public static final String MEDIA_GET_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESSTOKEN&media_id=MEDIAID";

        /*微信把长连接转成短链接*/
        public static final String URL_LONG2SHORT = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
        /*生成二维码*/
        public static final String URL_QRCODE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
        public static final String URL_IMG_QRCODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
        // 获取access_token的接口地址（GET） 限200（次/天）
        public final static String URL_ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        /*获取jsticket的令牌*/
        public final static String URL_JSTICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESSTOKEN&type=jsapi";
        /*创建菜单接口*/
        public final static String URL_MENUCREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
        //创建个性化菜单接口
        public final static String URL_ADDCONDITIONALMENU="https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN";
        //删除菜单接口
        public final static String URL_MENUDELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
        /*微信发送客服消息*/
        public final static String URL_KF_MSG = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
        /*通过code换取网页授权access_token*/
        public final static String URL_USER_CODE = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        /*拉取用户信息(需scope为 snsapi_userinfo)*/
        public final static String URL_USERINFO_SNSAPIUSERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        public final static String URL_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        /*发送消息模板*/
        public final static String URL_TEMPMSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        //创建标签
        public  final static String URL_TAG="https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";
        //查询标签
        public  final static String URL_QUERY_TAG="https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";
        //删除标签
        public  final static String URL_REMOVE_TAG="https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN";
        //批量为用户打标签
        public final static String URL_TAGGING="https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";
        //批量为用户取消标签
        public final static String URL_UNTAGGING="https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN";
        //获取标签下对应人员
        public final static String URL_TAG_USERLIST="https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN";
        //微信验证url
        public final static String URL_PAGE_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ APP_ID
                +"&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
        //微信获取openid
        public final static String URL_GET_OPENID="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ APP_ID+"&secret=" + APP_SECRET
                +"&code=CODE&grant_type=authorization_code";
    }

}
