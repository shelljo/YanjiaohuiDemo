package zhuoyue.com.yanjiaohuidemo.base;

import android.app.Application;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by ShellJor on 2017/5/3 0003.
 * at 14:08
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.initSDK(getApplicationContext());

    }

}
