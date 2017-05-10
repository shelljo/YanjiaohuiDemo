package zhuoyue.com.yanjiaohuidemo.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zhuoyue.com.yanjiaohuidemo.entity.HeadBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginInfoEntity;
import zhuoyue.com.yanjiaohuidemo.entity.RegisterCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.SmsCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.interfacepcg.INetServer;
import zhuoyue.com.yanjiaohuidemo.url.UrlConfig;

/**
 * Created by ShellJor on 2017/5/3 0003.
 * at 19:50
 */

public class NetWorkApi {
    private Retrofit mRetrofit;
    private INetServer mServer;
    private Call<RegisterCallBackEntity>mRegisterCallBackEntityCall;
    private Call<SmsCallBackEntity>mSmsCallBackEntityCall;
    private Call<LoginCallBackEntity>mLoginCallBackEntityCall;
    private Call<HeadBackEntity>mHeadBackEntityCall;
    private Call<LoginInfoEntity>mLoginInfoEntityCall;


    public NetWorkApi() {
        mRetrofit=new Retrofit.Builder()
                .baseUrl(UrlConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mServer = mRetrofit.create(INetServer.class);
    }
    //提交注册信息，包括电话，验证码，密码,还有忘记密码都是这个。
    public void PostRegisterData(String mobile, String code, String user_pwd,Callback<RegisterCallBackEntity>callback){
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("user_pwd", user_pwd);

        mRegisterCallBackEntityCall=mServer.PostRegistData(map);
        mRegisterCallBackEntityCall.enqueue(callback);
    }
    //提交注册信息，包括电话，验证码，密码,还有忘记密码都是这个。
    public void ForgetPassword(String mobile, String code, String user_pwd,Callback<RegisterCallBackEntity>callback){
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("user_pwd", user_pwd);

        mRegisterCallBackEntityCall=mServer.ForgetPassword(map);
        mRegisterCallBackEntityCall.enqueue(callback);
    }

    //获取短信验证码
    public void GetSmsNum(String phone,Callback<SmsCallBackEntity>callback){
        mSmsCallBackEntityCall = mServer.GetSmsNum(phone);
        mSmsCallBackEntityCall.enqueue(callback);
    }

    public void CheckNum(String mobile,String code,Callback<SmsCallBackEntity>callback){
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", code);
        mSmsCallBackEntityCall = mServer.CheckNum(map);
        mSmsCallBackEntityCall.enqueue(callback);
    }
    public void PostLoginData(String mobile,String user_pwd,Callback<LoginCallBackEntity>callback){

        Map<String, String> map = new HashMap<>();
        map.put("name", mobile);
        map.put("user_pwd",user_pwd);
        mLoginCallBackEntityCall=mServer.LoginCallBack(map);
        mLoginCallBackEntityCall.enqueue(callback);

    }
    //监测是否注册
    public void  CheckRegister(String phone,Callback<SmsCallBackEntity>callback){

        mSmsCallBackEntityCall = mServer.ChechRegister(phone);
        mSmsCallBackEntityCall.enqueue(callback);

    }

    //上传头像


    public void HeadPic(String phone,String pwd,Callback<HeadBackEntity>callback){
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("user_pwd", pwd);

        mHeadBackEntityCall = mServer.HeadPic(map);
        mHeadBackEntityCall.enqueue(callback);
    }
    //上传个人信息



    public void PostPersonalInfo(String user_nick,String sex,
                                 String byear,String bmonth,String bday,
                                 String province_id,String city_id,
                                 String mobile,String user_pwd,
                                 Callback<LoginInfoEntity>callback){
        Map<String, String> map = new HashMap<>();

        map.put("user_nick", user_nick);
        map.put("sex", sex);
        map.put("byear", byear);
        map.put("bmonth", bmonth);
        map.put("bday", bday);
        map.put("province_id", province_id);
        map.put("city_id", city_id);
        map.put("mobile", mobile);
        map.put("user_pwd", user_pwd);


//        map.put("user_nick", "蛋蛋");
//        map.put("sex", "女");
//        map.put("byear", "1991");
//        map.put("bmonth", "5");
//        map.put("bday", "5");
//        map.put("province_id", "河北");
//        map.put("city_id", "邯郸");
//        map.put("mobile", "18613317507");
//        map.put("user_pwd", "1");

        mLoginInfoEntityCall=mServer.PostPersonalInfo(map);
        mLoginInfoEntityCall.enqueue(callback);

    }



}
