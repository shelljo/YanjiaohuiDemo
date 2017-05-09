package zhuoyue.com.yanjiaohuidemo.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.widget.RadioGroup;

import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.base.BaseActivity;
import zhuoyue.com.yanjiaohuidemo.fragment.HomeFragment;
import zhuoyue.com.yanjiaohuidemo.fragment.MineFragment;
import zhuoyue.com.yanjiaohuidemo.fragment.SmsFragment;
import zhuoyue.com.yanjiaohuidemo.fragment.UnknowFragment;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup mRadioGroup;
    private HomeFragment mHomeFragment;
    private SmsFragment mSmsFragment;
    private UnknowFragment mUnknowFragment;
    private MineFragment mMineFragment;
    //记录当前的fragment.
    private Fragment mCurrentFragment;
    private FragmentManager mFragmentManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mHomeFragment = new HomeFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(R.id.main_fragment, mHomeFragment)
                .commit();
        mCurrentFragment = mHomeFragment;
        //点击每一个按钮对应一个fragment。
        mRadioGroup.setOnCheckedChangeListener(this);

    }

    private void initView() {

        mRadioGroup = (RadioGroup) findViewById(R.id.main_radio_group);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        mFragmentManager = getSupportFragmentManager();
        switch (checkedId) {
            case R.id.main_home:
                mFragmentManager.beginTransaction()
                        .hide(mCurrentFragment)
                        .show(mHomeFragment)
                        .commit();
                mCurrentFragment = mHomeFragment;

                break;
            case R.id.main_sms:
                if (mSmsFragment == null) {
                    mSmsFragment = new SmsFragment();
                    mFragmentManager.beginTransaction()
                            .hide(mCurrentFragment)
                            .add(R.id.main_fragment, mSmsFragment)
                            .commit();
                } else {
                    mFragmentManager.beginTransaction()
                            .hide(mCurrentFragment)
                            .show(mSmsFragment)
                            .commit();
                }
                mCurrentFragment = mSmsFragment;
                break;
            case R.id.main_fun:
                if (mUnknowFragment == null) {
                    mUnknowFragment = new UnknowFragment();
                    mFragmentManager.beginTransaction()
                            .hide(mCurrentFragment)
                            .add(R.id.main_fragment, mUnknowFragment)
                            .commit();
                } else {
                    mFragmentManager.beginTransaction()
                            .hide(mCurrentFragment)
                            .show(mUnknowFragment)
                            .commit();
                }
                mCurrentFragment = mUnknowFragment;


                break;
            case R.id.main_mine:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    mFragmentManager.beginTransaction()
                            .hide(mCurrentFragment)
                            .add(R.id.main_fragment, mMineFragment)
                            .commit();
                } else {
                    mFragmentManager.beginTransaction()
                            .hide(mCurrentFragment)
                            .show(mMineFragment)
                            .commit();
                }
                mCurrentFragment = mMineFragment;

                break;
        }
    }
    //监听回退按钮
    private int lastTime = 0;
    @Override
    public void onBackPressed() {
//         super.onBackPressed();//程序退出

//        if ((System.currentTimeMillis() - lastTime) > 2000) {// 两次点击之间的间隔大于2秒
//
////            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
//            MyToast.showShort(this,"再按一次退出");
//
//            // 记录这次点击时间
//            lastTime = (int) System.currentTimeMillis();
//        } else {
//            finish();
//        }

        System.currentTimeMillis();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

//        builder.setIcon(R.drawable.ic_launcher);

        builder.setTitle("亲，是否要退出");

//        builder.setMessage("亲，您不留下看一看？");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                MainActivity.this.finish();
                System.exit(0);

            }
        });

        builder.setNegativeButton("取消", null);

        builder.setNeutralButton("再看看", null);

         builder.create().show();

    }



}
