package zhuoyue.com.yanjiaohuidemo.activity;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.base.BaseActivity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginInfoEntity;
import zhuoyue.com.yanjiaohuidemo.url.UrlConfig;
import zhuoyue.com.yanjiaohuidemo.util.CheckPhoneUtil;
import zhuoyue.com.yanjiaohuidemo.util.MD5util;
import zhuoyue.com.yanjiaohuidemo.util.MyLog;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;
import zhuoyue.com.yanjiaohuidemo.util.NetUtils;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;

/**
 * 这个是登录界面
 * */

public class LoginActivity extends BaseActivity {
    private EditText mLogin_Phone,mLogin_Password;
    private Button mLogin_Register,mGoto_Register;
    private NetWorkApi mNetWorkApi=new NetWorkApi();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

    }

    private void initView() {

        mLogin_Phone = (EditText) findViewById(R.id.login_phone_number);
        mLogin_Password = (EditText) findViewById(R.id.login_password);
        mLogin_Register = (Button) findViewById(R.id.login_login);
        mGoto_Register = (Button) findViewById(R.id.login_goto_register);

    }
    //三方登录
    public void Sanfang_click(View view) {
        switch (view.getId()) {
            case  R.id.login_weibo:

                MyToast.showShort(LoginActivity.this,"微博登录暂未开通");
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                weibo.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                         Iterator iterator= hashMap.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) iterator.next();
                            Object key = entry.getKey();
                            Object values = entry.getValue();
                            MyLog.d("flag++", "key:" + key);
                            MyLog.d("flag++", "values:" + values);
                        }
                    }
                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                    }
                    @Override
                    public void onCancel(Platform platform, int i) {
                    }
                });
                break;

            case  R.id.login_qq:

                MyToast.showShort(LoginActivity.this,"QQ登录暂未开通");

                Login(new QQ(this));
                break;

            case  R.id.login_weichat:
                Login(new Wechat(this));
                Login(new QQ(this));
                MyToast.showShort(LoginActivity.this,"微信登录暂未开通");

                break;
        }
    }

    private void Login(Platform platform) {
        String userId = platform.getDb().getUserId();
//        String userIcon = platform.getDb().getUserIcon();
//        String userName = platform.getDb().getUserName();
        if (!TextUtils.isEmpty(userId)) {
            MyToast.showShort(LoginActivity.this,"成功登陆");
        }else {
            platform.setPlatformActionListener(new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                    switch (i) {
                        case Platform.ACTION_USER_INFOR:
                            Set<Map.Entry<String, Object>> entries = hashMap.entrySet();
                            for (Map.Entry<String,Object>entry:entries )
                                 {
                                     String key = entry.getKey();
                                     Object value = entry.getValue();
                                     MyLog.d("flag++","key:"+key);
                                     MyLog.d("flag++","key:"+value);
                                 }
                            break;
                        case Platform.ACTION_AUTHORIZING:

                            MyToast.showShort(LoginActivity.this,"第三方登陆成功，要功能不要数据");

                            break;
                    }
                }
                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                }
                @Override
                public void onCancel(Platform platform, int i) {

                }
            });
        }
    }

    // 按钮点击事件
    public void register_click(View view) {
        switch (view.getId()) {
            case R.id.login_goto_register:
    //点击去注册页面
                startActivity(new Intent(LoginActivity.this,PhoneRegistActivity.class));

                break;

    //点击登录，上交数据,首先判断是不是有网络连接.,然后检测手机号是不是正确，然后监测手机账号还有密码是不是符合的。
            case R.id.login_login:

                if (!NetUtils.isConnected(LoginActivity.this)) {
                    MyToast.showShort(LoginActivity.this,"网络无连接，请检查网络");

                }else{

                    boolean matchered = CheckPhoneUtil.isMatchered(UrlConfig.MATCH_PHONE, mLogin_Phone.getText().toString());

                    if (!matchered){

                        MyToast.showShort(LoginActivity.this,"手机号码输入有误");

                    }else {


                        mNetWorkApi.PostLoginData(mLogin_Phone.getText().toString(), Md5Handle(mLogin_Password.getText().toString()),
                                new Callback<LoginCallBackEntity>() {

                                    @Override
                                    public void onResponse(Call<LoginCallBackEntity> call, Response<LoginCallBackEntity> response) {
                                        LoginCallBackEntity body = response.body();
                                        MyToast.showLong(LoginActivity.this,"手机 ："+mLogin_Phone.getText().toString()+",密码 ："+mLogin_Password.getText().toString());
                                        if (body != null) {
                                            if (body.getBack().equals("true")) {
                                                MyToast.showShort(LoginActivity.this,"登录成功");
                                                LoginInfoEntity info = body.getInfo();
                                                MyLog.d("flag","用户信息："+info.getUser_name());
                                                MyLog.d("flag","用户信息："+info.toString());
                                                //其它程序不能访问,保存用户名还有密码.
                                                SharedPreferences preferences = getSharedPreferences("perinfo", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor edit = preferences.edit();
                                                edit.putString("id", info.getId());
                                                edit.putString("mobile", mLogin_Phone.getText().toString());
                                                edit.putString("user_pwd", Md5Handle(mLogin_Password.getText().toString()));
                                                edit.putString("nickname",info.getUser_nick());
                                                edit.putString("sex",info.getSex());
                                                edit.putString("year", info.getByear());
                                                edit.putString("month", info.getBmonth());
                                                edit.putString("mday", info.getBday());
                                                edit.putString("per_provinc",info.getProvince_id());
                                                edit.putString("Per_city", info.getCity_id());

                                                MyLog.d("flag","mobile:"+mLogin_Phone.getText().toString());
                                                MyLog.d("flag","user_pwd:"+ mLogin_Password.getText().toString());
                                                edit.commit();
                                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                            }else {
                                                if (body.getBack().equals("false")) {
                                                    MyToast.showShort(LoginActivity.this,body.getError()+"");
                                                }
                                            }
                                        }else {

                                            MyLog.d("flag","账号密码不符");

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<LoginCallBackEntity> call, Throwable t) {
                               //TODO 在这里判断手机密码是不是正确有点问题。密码位数有问题，没有判断
                                        MyLog.d("flag error","error :"+t.getMessage());
                                        MyToast.showShort(LoginActivity.this,"账号密码不符");

                                    }
                                });
                    }
                }

                break;
        }
    }

    //忘记密码，点击事件。
    public void forget_password(View view) {
        startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
    }

    public String Md5Handle(String string){
        String encrypt = MD5util.encrypt(string);
        String s = encrypt + "yanjiaohui".toString();
        String encrypt1 = MD5util.encrypt(s);
        return encrypt1;
    }

}
