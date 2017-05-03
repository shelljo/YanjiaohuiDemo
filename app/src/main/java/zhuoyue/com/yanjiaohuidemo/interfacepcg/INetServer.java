package zhuoyue.com.yanjiaohuidemo.interfacepcg;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import zhuoyue.com.yanjiaohuidemo.entity.RegisterCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.SmsCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.url.UrlConfig;

/**
 * Created by ShellJor on 2017/5/3 0003.
 * at 19:44
 */


//这个是Retrofit的接口类
public interface INetServer {


    @GET(UrlConfig.SMS_YANZHENG)
    Call<SmsCallBackEntity> GetSmsNum(@Query("phone") String phone);

    @FormUrlEncoded
    @POST(UrlConfig.NUM_REGISTER)
    Call<RegisterCallBackEntity>PostRegistData(@FieldMap Map<String,String>params );



}
