package zhuoyue.com.yanjiaohuidemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.activity.EditAddressActivity;
import zhuoyue.com.yanjiaohuidemo.entity.SmsCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.entity.UserAddressCallBackEntity;
import zhuoyue.com.yanjiaohuidemo.util.MyLog;
import zhuoyue.com.yanjiaohuidemo.util.MyToast;
import zhuoyue.com.yanjiaohuidemo.util.NetWorkApi;

/**
 * Created by ShellJor on 2017/5/17 0017.
 * at 10:07
 */

public class MyAddresslvadapter extends BaseAdapter {

    private Context mContext;
    private List<UserAddressCallBackEntity>mList;

    public MyAddresslvadapter(Context context, List<UserAddressCallBackEntity> list) {
        mContext = context;
        mList = list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
         ViewHolder holder=null ;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.myaddress_item, parent, false);
            holder.address = (TextView) convertView.findViewById(R.id.my_address);
            holder.name = (TextView) convertView.findViewById(R.id.my_name);
            holder.sex = (TextView) convertView.findViewById(R.id.my_sex);
            holder.phone = (TextView) convertView.findViewById(R.id.my_phone);
            holder.change = (ImageView) convertView.findViewById(R.id.my_change);
            holder.delete = (ImageView) convertView.findViewById(R.id.my_delete);

            holder.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.my_show_change_delete);


            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.address.setText(mList.get(position).getAddress());
        holder.name.setText(mList.get(position).getName());
        holder.phone.setText(mList.get(position).getMobile());

        if(mList.get(position).getSex().equals("0")){
            holder.sex.setText("女士");
        }else if (mList.get(position).getSex().equals("1")){
            holder.sex.setText("先生");
        }else if(mList.get(position).getSex().equals("-1")){
            holder.sex.setText("保密");
        }

        holder.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.showShort(mContext,"点击了改变");

                Intent intent = new Intent(mContext, EditAddressActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("aid", mList.get(position).getId());
                intent.putExtra("phone", mList.get(position).getMobile());
                intent.putExtra("name", mList.get(position).getName());
                intent.putExtra("address", mList.get(position).getAddress());
                intent.putExtra("sex", mList.get(position).getSex());

                mContext.startActivity(intent);

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO  在这里添加一个dialog
                //点击弹框是不是删除，

                mList.get(position).getId();

                SharedPreferences sp = mContext.getSharedPreferences("perinfo", Context.MODE_PRIVATE);
                NetWorkApi netWorkApi=new NetWorkApi();
                netWorkApi.DeleteUserAddress(sp.getString("mobile", null), sp.getString("user_pwd", null),
                        mList.get(position).getId(), new Callback<SmsCallBackEntity>() {
                            @Override
                            public void onResponse(Call<SmsCallBackEntity> call, Response<SmsCallBackEntity> response) {
                                SmsCallBackEntity body = response.body();
                                if (body != null) {
                                    if (body.getBack().equals("true")) {
                                        MyToast.showShort(mContext,"删除成功");
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<SmsCallBackEntity> call, Throwable t) {
                                MyLog.d("flag error","T "+t.getMessage());
                            }
                        });
                mList.remove(position);
                notifyDataSetChanged();
                MyToast.showShort(mContext,"点击了删除");
            }
        });

        return convertView;

    }

    static class ViewHolder{

         LinearLayout mLinearLayout;
         TextView address,name,sex,phone;
         ImageView change,delete;

    }

}
