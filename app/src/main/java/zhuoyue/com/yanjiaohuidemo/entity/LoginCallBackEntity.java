package zhuoyue.com.yanjiaohuidemo.entity;

/**
 * Created by ShellJor on 2017/5/4 0004.
 * at 13:38
 */

public class LoginCallBackEntity {

    private String back;

    private LoginInfoEntity info;

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public LoginInfoEntity getInfo() {
        return info;
    }

    public void setInfo(LoginInfoEntity info) {

        this.info = info;
    }
}
