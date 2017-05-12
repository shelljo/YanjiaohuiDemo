package com.yaotuofu.android.framework.baseutils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Felix on 2016/4/8.
 */
public class CheckUtils {
    /**
     * 验证是否为手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        // Pattern p =
        // Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
        // Matcher m = p.matcher(mobiles);
        // return m.matches();
        if (!TextUtils.isEmpty(mobiles)) {
            if (mobiles.length() == 11 && mobiles.startsWith("1")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证密码
     *
     * @param pwd
     * @return
     */
    public static boolean isPasswd(String pwd) {
//        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{6,20}");
//        Pattern p = Pattern.compile("^(?![0-9]+$)[a-zA-Z0-9]{6,20}");
        Pattern p = Pattern.compile("^(?![0-9]+$)\\S{6,20}");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 验证6位数字验证码
     *
     * @param pwd
     * @return
     */
    public static boolean isVerifycode(String pwd) {
        Pattern p = Pattern.compile("[0-9]{6}");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 验证身份证号
     *
     * @param certificate_num
     * @return
     */
    public static boolean isComplexCertificate(String certificate_num) {
        //简单验证15位全数字，18位最后一位可以是X的。不是复杂验证，不能验证真实身份证
        Pattern p = Pattern.compile("(^\\d{15}$)|(^\\d{17}(?:\\d|x|X)$)");
        Matcher m = p.matcher(certificate_num);
        return m.matches();
    }


    //截取数字
    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }


    //
    public static boolean isEmail(String text) {
        Pattern pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public static boolean isComplexPwd(String mobiles) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]{6,20}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    // 判断url 是否合法
    public static boolean isUrl(String urlString){
        //String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" ;

        if(StringUtils.isEmpty(urlString)) return  false;
        //转换为小写
        urlString = urlString.toLowerCase();
        String regex = "^((https|http|ftp|rtsp|mms)?://)"
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/{1,2}[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";


        Pattern patt = Pattern. compile(regex );
        Matcher matcher = patt.matcher(urlString);
        boolean isMatch = matcher.matches();
        if (!isMatch) {
//            System.out.println( "您输入的URL地址不正确" );
            return false;
        } else {
            return true;
        }
    }
}
