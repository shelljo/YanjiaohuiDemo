package zhuoyue.com.yanjiaohuidemo.interfacepcg;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import zhuoyue.com.yanjiaohuidemo.entity.HeadBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.RegisterCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.SmsCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.url.UrlConfig;

/**
 * Created by ShellJor on 2017/5/3 0003.
 * at 19:44
 */


//这个是Retrofit的接口类
public interface INetServer {

    //post请求手机号
    @GET(UrlConfig.SMS_YANZHENG)
    Call<SmsCallBackEntity> GetSmsNum(@Query("phone") String phone);
    //check验证码
    // http://47.93.112.70:8081/index.php/home/user/checkcode
    @FormUrlEncoded
    @POST(UrlConfig.CHECK_NUM)
    Call<SmsCallBackEntity> CheckNum(@FieldMap Map<String, String> params);
    //提交注册信息。
    @FormUrlEncoded
    @POST(UrlConfig.NUM_REGISTER)
    Call<RegisterCallBackEntity>PostRegistData(@FieldMap Map<String,String>params );
    //忘记密码
    @FormUrlEncoded
    @POST(UrlConfig.FORGET_NUM)
    Call<RegisterCallBackEntity>ForgetPassword(@FieldMap Map<String,String>params );
    //登录返回
    @FormUrlEncoded
    @POST(UrlConfig.NUM_LOGIN)
    Call<LoginCallBackEntity> LoginCallBack(@FieldMap Map<String, String>map);
    //监测验证信息
    @GET(UrlConfig.CHECK_NUM)
    Call<SmsCallBackEntity> ChechRegister(@Query("phone") String phone);

    @POST(UrlConfig.HEAD_PIC)
    Call<HeadBackEntity> HeadPic(@FieldMap Map<String, String> map);





}
