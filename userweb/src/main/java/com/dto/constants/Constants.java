package com.dto.constants;

/**
 * Created by CuiL on 2018-11-16.
 */
public class Constants {

    public static final String PAGE_CURRENT = "currentPage";
    public static final String PAGE_SIZE = "pageSize";
    public static final String SYS_PROJECTID = "HuaRun";

    public static final Integer AUDIT_CANCELAUDIT = -1; //退审
    public static final Integer AUDIT_BLANKOUT = -2;    //作废
    public static final Integer AUDIT_DELETE = -3;      //删除
    public static final Integer AUDIT_FINISH = -4;     //完成

    //特殊字符相关
    public static final String REGEX_SYMBOL = "[\"丿–·③④⑤`~!@#$%^&*+=|{}':;',\\[\\]<>/?~！@#� \uE009\uF8F5￥%……& _;*+|{}【】‘；：”“’。，、？.。]";     //完成
    public static final String REGEX_POSTCADE = "000000|325024|325000";
    public static final String REGEX_CHANGHAO = "\\d+号.*\\d+-\\d+";
    public static final String REGEX_CCHANGHAO = "\\d+号.*[a-zA-Z\\d一二三四五六七八九十零壹贰叁肆伍陆柒捌玖拾]+";
    public static final String REGEX_ZSHUZI = "[一二三四五六七八九十百]+";
    public static final String REGEX_DUANHAO = "\\d+号";
    public static final String REGEX_GANG = "-{2,}";
    public static final String REGEX_ZHUANYI = "(nbsp)+|(null)+|(amp)+|(undefined)+|(╲n)+|(╲r)+|\\).*";
    public static final String REGEX_NUMBER = "\\d{5,}";
    public static final String REGEX_SHI = "\\d+室";
    public static final String REGEX_GONGSI = ".*公司|.*大学";
    public static final String REGEX_SHUZI = "\\d.*|.*公司";
    public static final String REGEX_ZKH = "\\(.*";
    public static final String REGEX_HENG = "mdash";
    public static final String REGEX_KUOHAO = "\\(.*?\\)|\\（.*?\\）|\\[.*?\\]|\\【.*?\\】|\\{.*?\\}|\\<.*?\\>|\\《.*?\\》";
    public static final String REGEX_FUHAO= ",.+|，.+|;.+|；.+";
    public static final String REGEX_SHUZIYI = "(一(\\d+))";
    public static final String REGEX_KONG = "((\\d+) (\\d+))";
    public static final String REGEX_SHUZILIN = "((\\d+)o(\\d+))";
    public static final String REGEX_QUDANYUAN = "((\\d+)-((\\d+)-)+(\\d+))";
    public static final String REGEX_DANYUAN = "幢|栋|弄|单元|棟|—|～|橦";
    public static final String REGEX_LOU = "((\\d+)楼(\\d+))";
    public static final String REGEX_PHONE = "\\d{2}-1\\d{10}|1\\d{10}|\\d{3,4}-\\d{7,8}";
    public static final String REGEX_IDCARD = "\\d{17}[\\d|x|X]|\\d{15}";

    public static final long DAY = 1 * 24 * 60 * 60 * 1000;

    public static final String YES = "1";
    public static final String NO = "0";

    // -------------------------用户类型 相关---------------------------//
    public static final String C_PRE_DEFINE = "C_PRE_DEFINE"; // 预制用户
    public static final String C_USER_DEFINE = "C_USER_DEFINE"; // 自定义用户

    // -------------------------归档状态 相关---------------------------//
    public static final String LOG_ARCHIVE_RUNNING = "0"; // 归档中
    public static final String LOG_ARCHIVE_SUCCESS = "2"; // 归档成功
    public static final String LOG_ARCHIVE_FAIL = "4"; // 归档失败
    public static final String LOG_RECOVER_RUNNING = "1"; // 恢复中
    public static final String LOG_RECOVER_SUCCESS = "3"; // 恢复成功
    public static final String LOG_RECOVER_FAIL = "5"; // 恢复失败

    // -------------------------邮箱 相关------------------------------//
    public static final String EMAIL_SETTINGS = "EMAIL_SETTINGS";

    public final class EmailSettings {
        public static final String SMTP = "SMTP";
        public static final String PORT = "PORT";
        public static final String EMAIL = "EMAIL";
        public static final String PASSWORD = "PASSWORD";
        public static final String USE_SSL = "USE_SSL";
    }

    // -------------------------LICENSE 相关------------------------------//
    public static final String MCODE = "mcode";
    public static final String RES_LIMIT_COUNT = "resLimitCount";
    public static final String DEP_RES_LIMIT_COUNT = "depResLimitCount";
    public static final String SUBSYSTEM = "subsystem";
    public static final String PRODUCT_NAME = "productName";
    public static final String MODEL = "model";
    public static final String INCLUDE_MODULE = "includeModule";
    public static final String NOT_BEFORE = "notBefore";
    public static final String NOT_AFTER = "notAfter";
    public static final String LICENSE_SUBSYSTEM_SPLIT = "#";

    // -------------------------消息队列相关------------------------------//
    // 获取主机资源信息
    public static final String TARGET_RES_OPERATE = "TargetResources";
    // 部署策略消息队列
    public static final String JOB_OPERATE = "JobOperate";

    // -------------------------策略相关相关------------------------------//
    // 发现类型
    public static final String DISCOVERY = "discovery";
    // 发现数据库扫描策略
    public static final String DB_SCAN_TASK_DISCOVER = "DB_SCAN_TASK_DISCOVER";
    // 发现扫描策略
    public static final String SCAN_TASK_DISCOVER = "SCAN_TASK_DISCOVER";
    // 发现FTP扫描策略
    public static final String FTP_SCAN_TASK_DISCOVER = "FTP_SCAN_TASK_DISCOVER";
    // 发现sharePoint扫描策略
    public static final String SHAREPOINT_SCAN_TASK_DISCOVER = "SHAREPOINT_SCAN_TASK_DISCOVER";
    // 发现lotus扫描策略
    public static final String LOTUS_SCAN_TASK_DISCOVER = "LOTUS_SCAN_TASK_DISCOVER";
    // 发现exchange扫描策略
    public static final String EXCHANGE_SCAN_TASK_DISCOVER = "EXCHANGE_SCAN_TASK_DISCOVER";
    public static final String ON_POLICY_AND_FP_VERSION_UPDATE = "ON_POLICY_AND_FP_VERSION_UPDATE";

}
