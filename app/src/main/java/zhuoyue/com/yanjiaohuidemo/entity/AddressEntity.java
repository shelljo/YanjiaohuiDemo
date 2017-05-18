package zhuoyue.com.yanjiaohuidemo.entity;

import java.util.List;

/**
 * Created by ShellJor on 2017/5/17 0017.
 * at 10:45
 */

public class AddressEntity {

    private String back;
    private String error;
    private List<UserAddressCallBackEntity> info;

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<UserAddressCallBackEntity> getInfo() {
        return info;
    }

    public void setInfo(List<UserAddressCallBackEntity> info) {
        this.info = info;
    }
}
