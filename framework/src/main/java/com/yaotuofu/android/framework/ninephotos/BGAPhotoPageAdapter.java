package com.yaotuofu.android.framework.ninephotos;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yaotuofu.android.framework.R;
import com.yaotuofu.android.framework.ninephotos.widget.BGABrowserPhotoViewAttacher;
import com.yaotuofu.android.framework.ninephotos.widget.BGAImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/1.
 */

public class BGAPhotoPageAdapter extends PagerAdapter {


    private ArrayList<String> mPreviewImages;
    private PhotoViewAttacher.OnViewTapListener mOnViewTapListener;
    private Activity mActivity;

    public BGAPhotoPageAdapter(Activity activity, PhotoViewAttacher.OnViewTapListener onViewTapListener, ArrayList<String> previewImages) {
        mOnViewTapListener = onViewTapListener;
        mPreviewImages = previewImages;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mPreviewImages == null ? 0 : mPreviewImages.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        final BGAImageView imageView = new BGAImageView(container.getContext());
        container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final BGABrowserPhotoViewAttacher photoViewAttacher = new BGABrowserPhotoViewAttacher(imageView);
        photoViewAttacher.setOnViewTapListener(mOnViewTapListener);
        imageView.setDelegate(new BGAImageView.Delegate() {
            @Override
            public void onDrawableChanged(Drawable drawable) {
                if (drawable != null && drawable.getIntrinsicHeight() > drawable.getIntrinsicWidth() && drawable.getIntrinsicHeight() > BGAPhotoPickerUtil.getScreenHeight(imageView.getContext())) {
                    photoViewAttacher.setIsSetTopCrop(true);
                    photoViewAttacher.setUpdateBaseMatrix();
                } else {
                    photoViewAttacher.update();
                }
            }
        });

        BGAImage.displayImage(mActivity, imageView, mPreviewImages.get(position), R.mipmap.bga_pp_ic_holder_dark, R.mipmap.bga_pp_ic_holder_dark, BGAPhotoPickerUtil.getScreenWidth(imageView.getContext()), BGAPhotoPickerUtil.getScreenHeight(imageView.getContext()), null);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public String getItem(int position) {
        return mPreviewImages == null ? "" : mPreviewImages.get(position);
    }
}
