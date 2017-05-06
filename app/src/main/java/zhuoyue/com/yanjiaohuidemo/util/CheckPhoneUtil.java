package zhuoyue.com.yanjiaohuidemo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by ShellJor on 2017/5/6 0006.
 * at 10:27
 */

public  class CheckPhoneUtil {
    public  static   boolean isMatchered(String patternStr,String phone){
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(phone);
        if(matcher.find()){
            return true;
        }
      return false;
    }

}
