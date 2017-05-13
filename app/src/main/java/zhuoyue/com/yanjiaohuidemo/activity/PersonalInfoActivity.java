package zhuoyue.com.yanjiaohuidemo.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.entity.HeadBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.LoginInfoEntity;
import zhuoyue.com.yanjiaohuidemo.util.MyLog;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;
import zhuoyue.com.yanjiaohuidemo.util.SaveToImageUtils;

/**
 * 这个是个人信息页面。
 * */

public class PersonalInfoActivity extends AppCompatActivity implements View.OnClickListener {
//这个是上传头像需要用的东西。下面都是
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;


    private ImageView mPer_back;
    private Button mPer_save;
    private EditText mPer_data_born, mPer_province,mPer_nickname,mPer_sex,mPer_city;
    private String mYear,mMonth,mDay,mLocation;
    private NetWorkApi mNetWorkApi=new NetWorkApi();
    private String mMobile;
    private String mUser_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        initView();

        //设置编辑个人资料
        initPerInfo();


        mPer_back.setOnClickListener(this);
        mPer_save.setOnClickListener(this);
        mPer_data_born.setOnClickListener(this);

        initEditText();

    }

    //用sp保存个人信息，然后保存，在退出的时候清空sp.
    private void initPerInfo() {
        SharedPreferences sp = getSharedPreferences("perinfo", Context.MODE_PRIVATE);
        mPer_nickname.setText(sp.getString("nickname",""));
        mPer_sex.setText(sp.getString("sex",""));
        mYear= sp.getString("year", "");
        mMonth=sp.getString("month", "");
        mDay=sp.getString("mday", "");
        mPer_province.setText( sp.getString("per_provinc", ""));
        mPer_city.setText(   sp.getString("Per_city", ""));
        mPer_data_born.setText(mYear+"-"+mMonth+"-"+mDay);

    }


    private void initEditText() {

        mNetWorkApi.PostLoginData(mMobile, mUser_pwd, new Callback<LoginCallBackEntity>() {
            @Override
            public void onResponse(Call<LoginCallBackEntity> call, Response<LoginCallBackEntity> response) {
                LoginCallBackEntity body = response.body();
                if (body != null) {
                    LoginInfoEntity info = body.getInfo();
                    if(info.getUser_nick()==null){
                      mPer_nickname.setText(info.getUser_nick());
                    }
                    mPer_sex.setText(info.getSex());
                    mPer_province.setText(info.getProvince_id());
                    mPer_city.setText(info.getCity_id());
                    mPer_data_born.setText(info.getByear()+"-"+info.getBmonth()+"-"+info.getBday());
                }
            }
            @Override
            public void onFailure(Call<LoginCallBackEntity> call, Throwable t) {

            }
        });

    }
    private void initView() {


        mPer_city = (EditText) findViewById(R.id.per_city);
        mPer_sex = (EditText) findViewById(R.id.per_sex);
        mPer_save = (Button) findViewById(R.id.per_save);
        mPer_back = (ImageView) findViewById(R.id.per_back);
        mPer_data_born = (EditText) findViewById(R.id.per_data_born);
        mPer_province = (EditText) findViewById(R.id.per_province);
        mPer_nickname = (EditText) findViewById(R.id.per_nickname);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击回退按钮
           case  R.id.per_back:
                finish();
            break;
           //点击出生日期
            case R.id.per_data_born:
                DatePickerDialog dialog=new DatePickerDialog(PersonalInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        MyToast.showShort(PersonalInfoActivity.this,"您选择的时间是 "+year+"年，"+(month+1)+"月"+dayOfMonth+"日");

                        mYear = year + "";
                        mMonth = (month+1) +"";
                        mDay = dayOfMonth + "";
                        mPer_data_born.setText(mYear+"-"+mMonth+"-"+mDay);

                    }
                },1990,1,1);
                dialog.show();
                break;

            //点击保存
            case R.id.per_save:

                SharedPreferences sp = getSharedPreferences("perinfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();

                edit.putString("nickname", mPer_nickname.getText().toString());
                edit.putString("sex", mPer_sex.getText().toString());
                edit.putString("year", mYear);
                edit.putString("month", mMonth);
                edit.putString("mday", mDay);
                edit.putString("per_provinc", mPer_province.getText().toString());
                edit.putString("Per_city", mPer_city.getText().toString());
                edit.commit();

                mMobile = sp.getString("mobile", "");
                mUser_pwd = sp.getString("user_pwd","");

                mNetWorkApi.PostPersonalInfo(
                        mPer_nickname.getText().toString(),
                        mPer_sex.getText().toString(),
                        mYear, mMonth, mDay, mPer_province.getText().toString(),mPer_city.getText().toString(),

                    //    "花花","女","1994","2","11","福建","厦门",

                        mMobile, mUser_pwd, new Callback<LoginInfoEntity>() {

                    @Override
                    public void onResponse(Call<LoginInfoEntity> call, Response<LoginInfoEntity> response) {

                        MyLog.d("flag","nickname"+mPer_nickname.getText().toString());
                        MyLog.d("flag","sex"+ mPer_sex.getText().toString());
                        MyLog.d("flag","year"+mYear);
                        MyLog.d("flag","month"+mMonth);
                        MyLog.d("flag","mday"+mDay);
                        MyLog.d("flag","per_provinc"+mPer_province.getText().toString());
                        MyLog.d("flag","Per_city"+mPer_city.getText().toString());
                        MyLog.d("flag","user_pwd:"+ mUser_pwd);
                        MyLog.d("flag","mobile:"+ mMobile);
                        LoginInfoEntity body = response.body();
                        
                        if (body != null) {
                        MyLog.d("flag+","back"+ body.getBack());

                            if (body.getBack().equals("true")) {
                                MyToast.showShort(PersonalInfoActivity.this,"保存成功");
                                finish();
                            }else {
                                MyToast.showShort(PersonalInfoActivity.this,"保存失败");
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginInfoEntity> call, Throwable t) {

                        MyLog.d("flag error"+"error "+t.getMessage());

                    }
                });
                break;
            case R.id.per_icon:
                showChoosePicDialog();
                break;
        }
    }

    //点击选择照片或者调用拍照。
    private void showChoosePicDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择头像");
        String[] items = { "相册", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE:
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE:
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri);
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData());
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data);
                    }
                    break;
            }
        }
    }

    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = SaveToImageUtils.toRoundBitmap(photo, tempUri);
//            mPer_icon.setImageBitmap(photo);

            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {

        String imagePath = SaveToImageUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        String path = Environment.getExternalStorageDirectory()+File.separator;

        Log.e("imagePath", imagePath+"");
        if(imagePath != null){

            // ����imagePath
            // ...
            File file = new File(imagePath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", file.getName(),requestBody);

            SharedPreferences sp = getSharedPreferences("perinfo", Context.MODE_PRIVATE);
            mMobile = sp.getString("mobile", "");
            mUser_pwd = sp.getString("user_pwd","");
            MyLog.d("flag", "=========mMobile:"+mMobile);
            MyLog.d("flag", "=========mUser_pwd:"+mUser_pwd);
            mNetWorkApi.HeadPic(mMobile, mUser_pwd,body, new Callback<HeadBackEntity>() {
               @Override
               public void onResponse(Call<HeadBackEntity> call, Response<HeadBackEntity> response) {
                   HeadBackEntity body = response.body();
                   if (body != null) {
                       MyLog.d("flag","up icon :"+body.getBack());
                       MyLog.d("flag","up icon :"+body.getImgurl());

                   }else {
                       MyLog.d("flag","up icon :body null");
                   }

               }

               @Override
               public void onFailure(Call<HeadBackEntity> call, Throwable t) {

                      MyLog.d("flag","up Icon :"+t.getMessage());

               }
           });

        }
    }


}
