package zhuoyue.com.yanjiaohuidemo.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.RadioGroup;

import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.base.BaseActivity;
import zhuoyue.com.yanjiaohuidemo.fragment.HomeFragment;
import zhuoyue.com.yanjiaohuidemo.fragment.MineFragment;
import zhuoyue.com.yanjiaohuidemo.fragment.SmsFragment;
import zhuoyue.com.yanjiaohuidemo.fragment.UnknowFragment;

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
}
