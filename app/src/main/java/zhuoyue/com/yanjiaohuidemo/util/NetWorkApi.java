package zhuoyue.com.yanjiaohuidemo.util;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
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


    public NetWorkApi() {
        mRetrofit=new Retrofit.Builder()
                .baseUrl(UrlConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mServer = mRetrofit.create(INetServer.class);
    }
    public void PostRegisterData(String mobile, String code, String user_pwd,Callback<RegisterCallBackEntity>callback){
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("user_pwd", user_pwd);

        mRegisterCallBackEntityCall=mServer.PostRegistData(map);
        mRegisterCallBackEntityCall.enqueue(callback);
    }
    public void GetSmsNum(String phone,Callback<SmsCallBackEntity>callback){
        mSmsCallBackEntityCall = mServer.GetSmsNum(phone);
        mSmsCallBackEntityCall.enqueue(callback);
    }


}
