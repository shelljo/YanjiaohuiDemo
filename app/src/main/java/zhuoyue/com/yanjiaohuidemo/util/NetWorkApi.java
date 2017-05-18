package zhuoyue.com.yanjiaohuidemo.util;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zhuoyue.com.yanjiaohuidemo.entity.AddressEntity;
import zhuoyue.com.yanjiaohuidemo.entity.HeadBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginInfoEntity;
import zhuoyue.com.yanjiaohuidemo.entity.RegisterCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.ShangjiaEntity;
import zhuoyue.com.yanjiaohuidemo.entity.SmsCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.UserAddressCallBackEntity;
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
    private Call<AddressEntity> mAddressEntityCall;
    private Call<UserAddressCallBackEntity>mUserAddressCallBackEntityCall;


    //这个是商家列表
    private Call<ShangjiaEntity>mShangjiaEntityCall;


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

    public void HeadPic(String phone, String pwd, MultipartBody.Part file, Callback<HeadBackEntity>callback){
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("user_pwd", pwd);

        mHeadBackEntityCall = mServer.HeadPic(map,file);
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

        mLoginInfoEntityCall=mServer.PostPersonalInfo(map);
        mLoginInfoEntityCall.enqueue(callback);
    }

    public void GetHomeInfo(String city,String xpoint,String ypoint,Callback<ShangjiaEntity>callback){

        mShangjiaEntityCall = mServer.GetHomeInfo(city, xpoint, ypoint);
        mShangjiaEntityCall.enqueue(callback);
    }

    //  手机换帮第一步发送原手机验证码？
    public void ChangeNum_First_oldPhone(String mobile,Callback<SmsCallBackEntity>callback){

        mSmsCallBackEntityCall=mServer.ChangeNum_First_oldPhone(mobile);
        mSmsCallBackEntityCall.enqueue(callback);

    }

   //  手机换帮 第二步 验证原手机是不是正确
    public void ChangeNum_Second_Check_PhoneNum(String old_phone,String code, Callback<SmsCallBackEntity>callback){

        Map<String, String> map = new HashMap<>();
        map.put("mobile", old_phone);
        map.put("code", code);
        mSmsCallBackEntityCall=mServer.ChangeNum_Second_Check_PhoneNum(map);
        mSmsCallBackEntityCall.enqueue(callback);

    }

    //第三步 发送新手机验证码
    public void ChangeNum_Third_NewPhoneNum(String phone, Callback<SmsCallBackEntity>callback){
        mSmsCallBackEntityCall=mServer.ChangeNum_Third_NewPhoneNum(phone);
        mSmsCallBackEntityCall.enqueue(callback);

    }

    //第四步，更换新手机号
    public void ChangeSuccesful(String mobile,String code,String oldphone ,String oldcode , Callback<SmsCallBackEntity>callback){
        Map<String, String> map = new HashMap<>();
        map.put("step", "1");
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("oldphone", oldphone);
        map.put("oldcode", oldcode);
        mSmsCallBackEntityCall = mServer.ChangeSuccesful(map);
        mSmsCallBackEntityCall.enqueue(callback);
    }

    // 获取用户地址
    // mobile：登录手机
    // user_pwd：登录密码
    public void GetUserAddress(String mobile,String pwd, Callback<AddressEntity>callback){

        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("user_pwd", pwd);

        mAddressEntityCall= mServer.GetUserAddress(map);
        mAddressEntityCall.enqueue(callback);

    }

    //增加用户地址
    public void AddUserAddress(String mobile,String user_pwd, String name,String mobile1,String address,String sex,
                               String xpoint,String ypoint,Callback<SmsCallBackEntity>callback){

        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("mobile", mobile);
        map.put("user_pwd", user_pwd);
        map.put("mobile1", mobile1);
        map.put("address", address);
        map.put("sex", sex);
        map.put("xpoint", xpoint);
        map.put("ypoint", ypoint);

        mSmsCallBackEntityCall = mServer.AddUserAddress(map);
        mSmsCallBackEntityCall.enqueue(callback);
    }


    //修改用户地址
    public void ChangeUserAddress(String id, String mobile,String user_pwd, String name,String mobile1,String address,String sex,
                                  String xpoint,String ypoint, Callback<SmsCallBackEntity>callback){
        Map<String, String> map = new HashMap<>();

        map.put("aid", id);
        map.put("name", name);
        map.put("mobile", mobile);
        map.put("user_pwd", user_pwd);
        map.put("mobile1", mobile1);
        map.put("address", address);
        map.put("sex", sex);
        map.put("xpoint", xpoint);
        map.put("ypoint", ypoint);
//        map.put("is_default", isdefault);

        mSmsCallBackEntityCall = mServer.ChangeUserAddress(map);
        mSmsCallBackEntityCall.enqueue(callback);

    }

    //删除用户地址
    public void DeleteUserAddress(String mobile,String user_pwd,String id, Callback<SmsCallBackEntity>callback){
        Map<String, String> map = new HashMap<>();

        map.put("id", id);
        map.put("mobile", mobile);
        map.put("user_pwd", user_pwd);

        mSmsCallBackEntityCall = mServer.DeleteUserAddress(map);
        mSmsCallBackEntityCall.enqueue(callback);


    }

}
