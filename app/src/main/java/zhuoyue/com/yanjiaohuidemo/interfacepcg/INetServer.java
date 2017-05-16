package zhuoyue.com.yanjiaohuidemo.interfacepcg;

import java.io.File;
import java.net.URL;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import retrofit2.http.Url;
import zhuoyue.com.yanjiaohuidemo.entity.HeadBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginInfoEntity;
import zhuoyue.com.yanjiaohuidemo.entity.RegisterCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.ShangjiaEntity;
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

    //上传头像
    @Multipart
    @POST(UrlConfig.HEAD_PIC)
    Call<HeadBackEntity> HeadPic(@FieldMap Map<String, String>map, @Part MultipartBody.Part file);

    //上传个人信息
    @FormUrlEncoded
    @POST(UrlConfig.PERSONAL_INFO)
    Call<LoginInfoEntity> PostPersonalInfo(@FieldMap Map<String, String> map);

    @GET(UrlConfig.HOME_INFO)
    Call<ShangjiaEntity> GetHomeInfo(@Query("city_id") String city_id,@Query("xpoint")String xpoint,@Query("ypoint")String ypoint );

    //手机换帮 ,第一步,发送原手机验证码：
    @GET(UrlConfig.CHANGE_PHONE_FIRST)
    Call<SmsCallBackEntity> ChangeNum_First_oldPhone(@Query("mobile") String mobile);

    //第二步：验证原手机是否正确
    @FormUrlEncoded
    @POST(UrlConfig.CHANGE_PHONE_SECOND)
    Call<SmsCallBackEntity> ChangeNum_Second_Check_PhoneNum(@FieldMap Map<String, String> map);

    //第三步：发送新手机验证码
    @GET(UrlConfig.CHANGE_PHONE_THIRD)
    Call<SmsCallBackEntity> ChangeNum_Third_NewPhoneNum(@Query("mobile") String mobile);

    //第四步：完成
    @FormUrlEncoded
    @POST(UrlConfig.CHANGE_PHONE_FOURTH)
    Call<SmsCallBackEntity> ChangeSuccesful(@FieldMap Map<String, String> map);







}
