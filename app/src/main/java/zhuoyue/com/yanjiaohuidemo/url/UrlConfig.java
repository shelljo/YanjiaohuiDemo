package zhuoyue.com.yanjiaohuidemo.url;

/**
 * Created by ShellJor on 2017/5/3 0003.
 * at 19:39
 */

public class UrlConfig {

    public static final String RMB = "¥";
    //图片头文件
    public static final String IMAGE_BASE_URL = "http://47.93.112.70:8081/";

    public static final String BASE_URL = "http://47.93.112.70:8081/index.php/";

    //短信验证
    public static final String SMS_YANZHENG = "home/user/regsmssend";

    //会员注册
    public static final String NUM_REGISTER = "home/user/reg";

    //忘记密码
    public static final String FORGET_NUM = "home/user/forget";

    //check 验证码
    public static final String CHECK_NUM  = "home/user/checkcode";

    //会员登录
    public static final String NUM_LOGIN = "home/user/login";

    //验证手机是不是已经注册了
    public static final String IS_REGISTER ="home/user/number_check";

    //上传头像
    public static final String HEAD_PIC = "home/user/user_setavatar";

    //验证手机号码的正则。
    public final static String MATCH_PHONE = "^1[34578]{1}\\d{9}$";
    
    //个人信息接口
    public static final String PERSONAL_INFO = "home/user/user_change";


}
