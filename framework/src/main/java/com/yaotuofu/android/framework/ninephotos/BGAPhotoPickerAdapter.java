package com.yaotuofu.android.framework.ninephotos;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.yaotuofu.android.framework.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/1.
 */

public class BGAPhotoPickerAdapter extends BGARecyclerViewAdapter<String> {
    private ArrayList<String> mSelectedImages = new ArrayList<>();
    private int mImageWidth;
    private int mImageHeight;
    private boolean mTakePhotoEnabled;
    private Activity mActivity;

    public BGAPhotoPickerAdapter(Activity activity, RecyclerView recyclerView) {
        super(recyclerView, R.layout.bga_pp_item_photo_picker);
        mImageWidth = BGAPhotoPickerUtil.getScreenWidth(recyclerView.getContext()) / 6;
        mImageHeight = mImageWidth;
        mActivity = activity;
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper helper) {
        helper.setItemChildClickListener(R.id.iv_item_photo_picker_flag);
        helper.setItemChildClickListener(R.id.iv_item_photo_picker_photo);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {
        if (mTakePhotoEnabled && position == 0) {
            helper.setVisibility(R.id.tv_item_photo_picker_tip, View.VISIBLE);
            helper.getImageView(R.id.iv_item_photo_picker_photo).setScaleType(ImageView.ScaleType.CENTER);
            helper.setImageResource(R.id.iv_item_photo_picker_photo, R.mipmap.bga_pp_ic_gallery_camera);

            helper.setVisibility(R.id.iv_item_photo_picker_flag, View.INVISIBLE);
            helper.getImageView(R.id.iv_item_photo_picker_photo).setColorFilter(null);
        } else {
            helper.setVisibility(R.id.tv_item_photo_picker_tip, View.INVISIBLE);
            helper.getImageView(R.id.iv_item_photo_picker_photo).setScaleType(ImageView.ScaleType.CENTER_CROP);
            BGAImage.displayImage(mActivity, helper.getImageView(R.id.iv_item_photo_picker_photo), model, R.mipmap.bga_pp_ic_holder_dark, R.mipmap.bga_pp_ic_holder_dark, mImageWidth, mImageHeight, null);

            helper.setVisibility(R.id.iv_item_photo_picker_flag, View.VISIBLE);

            if (mSelectedImages.contains(model)) {
                helper.setImageResource(R.id.iv_item_photo_picker_flag, R.mipmap.bga_pp_ic_cb_checked);
                helper.getImageView(R.id.iv_item_photo_picker_photo).setColorFilter(helper.getConvertView().getResources().getColor(R.color.bga_pp_photo_selected_mask));
            } else {
                helper.setImageResource(R.id.iv_item_photo_picker_flag, R.mipmap.bga_pp_ic_cb_normal);
                helper.getImageView(R.id.iv_item_photo_picker_photo).setColorFilter(null);
            }
        }
    }

    public void setSelectedImages(ArrayList<String> selectedImages) {
        if (selectedImages != null) {
            mSelectedImages = selectedImages;
        }
        notifyDataSetChanged();
    }

    public ArrayList<String> getSelectedImages() {
        return mSelectedImages;
    }

    public int getSelectedCount() {
        return mSelectedImages.size();
    }

    public void setImageFolderModel(BGAImageFolderModel imageFolderModel) {
        mTakePhotoEnabled = imageFolderModel.isTakePhotoEnabled();
        setDatas(imageFolderModel.getImages());
    }
}
