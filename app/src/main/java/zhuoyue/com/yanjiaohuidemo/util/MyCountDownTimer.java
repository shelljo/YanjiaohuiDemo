package zhuoyue.com.yanjiaohuidemo.util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by ShellJor on 2017/4/30 0030.
 * at 10:19
 */

public class MyCountDownTimer extends CountDownTimer {

    private Button mButton;

    public MyCountDownTimer(long millisInFuture, long countDownInterval, Button button) {
        super(millisInFuture, countDownInterval);
        mButton = button;
    }

    //在倒计时过程中显示的
    @Override
    public void onTick(long millisUntilFinished) {
        mButton.setClickable(false);
        mButton.setText(millisUntilFinished/100+"s后获取");
        mButton.setTextColor(Color.RED);
    }

    //在倒计时结束的时候显示
    @Override
    public void onFinish() {

        mButton.setText("重新获取");
        mButton.setClickable(true);
        mButton.setTextColor(Color.argb(255,109,179,149));

    }
}
