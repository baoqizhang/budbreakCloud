package com.budbreak.pan.common;

import java.util.Random;
import java.util.UUID;

/**
 * 常量
 */
public class Constants {

    //短信发送前缀（保存1分钟）
    public static final String PHONE_SEND_PRE = "S";
    //短信获取前缀（保存5分钟）
    public static final String PHONE_QUERY_PRE = "Q";
    //短信发送累计数前缀（保存到当天0点）
    public static final String SMS_SEND_SUM = "SUM";
    //REDIS间隔符
    public static final String SPACE = ":";
    //MAC分割符
    public static final String MAC_SPACE = ",";
    //自动生成邮箱的前缀
    public static final String AUTO_EMAIL_PRE = "auto_";
    //自动生成邮箱的后缀
    public static final String AUTO_EMAIL_SUF = "@homelink.com";
    //默认字符编码
    public static final String CHARSET = "UTF-8";
    //DES指定秘钥
    public static final String SALT_KEY = "fUjIaMi";
    //限定字符
    public static final String STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    //正则匹配数字串
    public static final String REG = "^[0-9]+(.[0-9]+)?$";
    //黑名单登录提示语
    public static final String BLACKLIST = "您的账号已被冻结，请联系管理员";
    //游客账号已升级登录提示语
    public static final String UPGRADED = "您的账号已升级，请使用账号密码登录";
    //赠送产品ID
    public static final Integer PID = 1;
    //路由器产品ID
    public static final Integer ROUTER_PID = 3;
    //同步到router的redis队列key
    public static final String USERINFO_TO_ROUTERUSER = "router_user";
    //sendActiveEmail key
    public static final String SEND_EMAIL_KEY = "sendEmailNum:";
    //sendActiveEmail num
    public static final Integer SEND_EMAIL_NUM = 5;
    //邮箱验证码 key
    public static final String SEND_VERIFY_CODE = "sendVerifyCode:";
    public static final Integer VERIFY_CODE_SEC = 60 * 10;

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    /**
     * 服务状态: ACTIVE(激活), PAUSE(终止)
     */
    public static enum ServeStateEnum {

        ACTIVE("ACTIVE"), PAUSE("PAUSE");

        private String enumVal;
        ServeStateEnum(String enumVal) {
            this.enumVal = enumVal;
        }
        public String getEnumVal() {
            return enumVal;
        }
    }

    /**
     * 订单状态: 10(未支付); 30(已生效);
     *  暂未使用: 20(已支付); 31(退款中); 50(已取消)
     */
    public static enum OrderStateEnum {

        PAYING(10), EFFECTIVE(30), REFUND(40), CANCELED(50);

        private int enumVal;
        OrderStateEnum(int enumVal) {
            this.enumVal = enumVal;
        }
        public int getEnumVal() {
            return enumVal;
        }

    }

    /**
     * 订单类型: 1.正式; 2.试用; 3.签到赠送; 4.评分赠送; 5.人工赠送; 6.邀请赠送; 7.升级赠送; 8.看广告赠送
     */
    public static enum OrderTypeEnum {

        FORMAL(1), TRIAL(2), SIGN(3), SCORE(4), GIVE_AWAY(5), INVITE_GIFT(6), UPGRADE_GIFT(7), AD(8);

        private int enumVal;
        OrderTypeEnum(int enumVal) {
            this.enumVal = enumVal;
        }
        public int getEnumVal() {
            return enumVal;
        }
    }

    /**
     * 用户状态 ACTIVE(已激活), INACTIVE(未激活), INFO_INCOMPLETE(信息不完整)
     */
    public static enum UserStateEnum {

        ACTIVE("ACTIVE"), INACTIVE("INACTIVE"), INFO_INCOMPLETE("INFO_INCOMPLETE");

        private String enumVal;
        UserStateEnum(String enumVal) {
            this.enumVal = enumVal;
        }
        public String getEnumVal() {
            return enumVal;
        }
    }

    /**
     * 用户类型: 1.邮箱密码；2.微信；3.微博；4.游客（升级后为新的类型）
     */
    public static enum UserTypeEnum {

        EMAIL(1), WEIXIN(2), WEIBO(3),TOURIST(4);

        private int enumVal;
        UserTypeEnum(int enumVal) {
            this.enumVal = enumVal;
        }
        public int getEnumVal() {
            return enumVal;
        }
    }

    /**
     * 注册类型: 1.正常注册；2.游客升级注册
     */
    public static enum RegisterTypeEnum {

        formal(1), upgrade(2);

        private int enumVal;
        RegisterTypeEnum(int enumVal) {
            this.enumVal = enumVal;
        }
        public int getEnumVal() {
            return enumVal;
        }
    }

    /**
     * 支付类型
     */
    public static enum PayTypeEnum{

        ALIPAY("alipay"), GOOGLEPAY("googlePay"),APPLEPAY("applePay"),PAYPAL("paypal");

        private String enumVal;
        PayTypeEnum(String enumVal) {
            this.enumVal = enumVal;
        }
        public String getEnumVal() {
            return enumVal;
        }
    }

    /**
     * 支付回调结果
     */
    public static enum PayCallBackResultEnum
    {
        SUCCESS("success"), FAIL("fail");

        private String enumVal;
        PayCallBackResultEnum(String enumVal) {
        this.enumVal = enumVal;
        }
        public String getEnumVal() {
            return enumVal;
        }
    }

    /**
     * 终端类型
     */
    public static enum TerminalTypeEnum{
        IOS("ios"), ANDROID("android");
        private String enumVal;
        TerminalTypeEnum(String enumVal) {this.enumVal = enumVal;}
        public String getEnumVal() {return enumVal;}
    }

    /**
     * 产品标记符
     */
    public static enum ProjectTagEnum{
        ROUTER("ROUTER"), APP("APP");
        private String enumVal;
        ProjectTagEnum(String enumVal) {this.enumVal = enumVal;}
        public String getEnumVal() {return enumVal;}
    }

    /**
     * 生成随机字符串 (10位数邀请码)
     * @return
     */
    public static String createRandomStr(){
        //生成随机邮箱
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++){
            int number=random.nextInt(STR.length());
            char charAt = STR.charAt(number);
            sb.append(charAt);
        }
        return sb.toString();
    }

    /**
     * 生成随机位数字符串
     * @param length
     * @return
     */
    public static String createRandomStr(Integer length){
        //生成随机邮箱
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++){
            int number=random.nextInt(STR.length());
            char charAt = STR.charAt(number);
            sb.append(charAt);
        }
        return sb.toString();
    }

    /**
     * 自动生成UUID唯一邮箱
     * @return
     */
    public static String autoCreateEmail(){
        StringBuffer sb = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16); //16进制字符串转10进制int型
            sb.append(chars[x % 0x3E]);
        }
        return sb.toString();
    }

}
