package zhuoyue.com.yanjiaohuidemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.entity.ShangjiaEntity;
import zhuoyue.com.yanjiaohuidemo.url.UrlConfig;
import zhuoyue.com.yanjiaohuidemo.util.MyGlide;

/**
 * Created by ShellJor on 2017/5/13 0013.
 * at 15:53
 */

public class ShangjialvAdapter extends BaseAdapter {

    private Context mContext;
    private List<ShangjiaEntity.SupplierListBean>mShangjiaEntities;

    public ShangjialvAdapter(Context context, List<ShangjiaEntity.SupplierListBean> shangjiaEntities) {
        mContext = context;
        mShangjiaEntities = shangjiaEntities;
    }

    @Override
    public int getCount() {
        return mShangjiaEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mShangjiaEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.buss_lv_item, parent, false);
//            holder.mImageView = (ImageView) convertView.findViewById(R.id.shangjia_im);
//            holder.mName = (TextView) convertView.findViewById(R.id.shagnjia_name);
//            holder.mTextView = (TextView) convertView.findViewById(R.id.shagnjia_type);

            holder.buss_item_iv = (ImageView) convertView.findViewById(R.id.buss_item_iv);
            holder.buss_item_distance = (TextView) convertView.findViewById(R.id.buss_item_distance);
            holder.buss_item_per_people = (TextView) convertView.findViewById(R.id.buss_item_per_people);
            holder.buss_item_title_tv = (TextView) convertView.findViewById(R.id.buss_item_title_tv);
            holder.buss_item_zan = (TextView) convertView.findViewById(R.id.buss_item_zan);
            holder.buss_item_type = (TextView) convertView.findViewById(R.id.buss_item_type);
            holder.buss_item_ratingbar= (RatingBar) convertView.findViewById(R.id.buss_item_ratingbar);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //这个就定义为地址吧

//        holder.mTextView.setText(mShangjiaEntities.get(position).getSupplier_list().get(position).getAddress());
//        holder.mTextView.setText(mShangjiaEntities.get(position).getAddress());
//        MyGlide.load(mContext, UrlConfig.IMAGE_BASE_URL+mShangjiaEntities.get(position).getIndex_img(),holder.mImageView);
//        holder.mName.setText(mShangjiaEntities.get(position).getName());
        MyGlide.load(mContext,UrlConfig.IMAGE_BASE_URL+mShangjiaEntities.get(position).getIndex_img(),holder.buss_item_iv);
        holder.buss_item_title_tv.setText(mShangjiaEntities.get(position).getName());

//        holder.buss_item_ratingbar.setNumStars(Integer.parseInt(mShangjiaEntities.get(position).getGood_rate()));


        holder.buss_item_per_people.setText("￥"+mShangjiaEntities.get(position).getRef_avg_price()+"/人");
        holder.buss_item_type.setText(mShangjiaEntities.get(position).getTypename());
        holder.buss_item_distance.setText(mShangjiaEntities.get(position).getJuli());
        holder.buss_item_zan.setText(mShangjiaEntities.get(position).getGood_dp_count());


        return convertView;

    }

    static class ViewHolder{
//        private TextView mTextView,mName;
//        private ImageView mImageView;
        private TextView buss_item_title_tv,buss_item_per_people,buss_item_zan,buss_item_type,buss_item_distance;
        private ImageView buss_item_iv;
        private RatingBar buss_item_ratingbar;


    }
}
