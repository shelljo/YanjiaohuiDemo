package zhuoyue.com.yanjiaohuidemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import zhuoyue.com.yanjiaohuidemo.R;
import zhuoyue.com.yanjiaohuidemo.entity.Picature;


/**
 * Created by ShellJor on 2017/4/26 0026.
 * at 9:45
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Picature> mList = new ArrayList<>();

    public GridAdapter(Context context, String[]titles, Integer [] images ) {
        super();
        mContext = context;
        for (int i = 0; i < images.length; i++) {
            Picature picature = new Picature(titles[i], images[i]);
            mList.add(picature);
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder=null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.grid_item_iv);
            holder.mTextView = (TextView) convertView.findViewById(R.id.grid_item_tv);
            convertView.setTag(holder);
        }else {

            holder = (ViewHolder) convertView.getTag();

        }
        Picature picature = mList.get(position);
        holder.mImageView.setImageResource(picature.getImageId());
        holder.mTextView.setText(picature.getTitle());
        return convertView;
    }

    private static class ViewHolder{

        private TextView mTextView;
        private ImageView mImageView;

    }

}
