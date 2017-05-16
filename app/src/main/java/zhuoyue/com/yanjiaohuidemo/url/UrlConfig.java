package zhuoyue.com.yanjiaohuidemo.url;

/**
 * Created by ShellJor on 2017/5/3 0003.
 * at 19:39
 */

public class UrlConfig {

    public static final String RMB = "¥";
    //图片头文件
    public static final String IMAGE_BASE_URL = "http://47.93.112.70:8081/";

    //BaseUrl
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

    //首页的各种信息的url
    public static final String HOME_INFO = "home/index/index_getall";

    //下面是手机换绑的操作
    //第一步：发送原手机验证码：
    public static final String CHANGE_PHONE_FIRST="home/user/regsmssend?";

    //第二步：验证原手机是否正确：参数：
    // mobile = 手机号；
    // code = 验证码；
    public static final String CHANGE_PHONE_SECOND = "home/user/user_changemobile";

    // 第三步：发送新手机验证码
    //新手机号
    public static final String CHANGE_PHONE_THIRD = "home/user/regsmssend?";

    // step = 1
    // mobile = 新手机号；
    // code = 验证码；
    // oldphone = 原手机号
    // oldcode = 原验证码
    //第四步：更换为新手机号：
    public static final String CHANGE_PHONE_FOURTH = "home/user/user_changemobile";


}
