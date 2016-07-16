package com.bupt.read.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.read.R;
import com.bupt.read.model.entity.BeautyPicItem;
import com.bupt.read.ui.activity.ImageDetailActivity;
import com.bupt.read.utils.ImageUtils;
import com.bupt.read.utils.MeasureUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Random;

/**
 * Created by xs on 2016/6/22.
 */
public class MyBeautyPicAdapter extends RecyclerView.Adapter<MyBeautyPicAdapter.ViewHolder> {
    private List<BeautyPicItem> list;
    private Context context;
    private int lastPosition = -1;

    public MyBeautyPicAdapter(List<BeautyPicItem> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @Override
    public MyBeautyPicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view =  LayoutInflater.from(context).inflate(R.layout.item_beauty,parent,false);
        return new ViewHolder(view);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R
                    .anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onBindViewHolder(MyBeautyPicAdapter.ViewHolder holder, final int position) {
        final BeautyPicItem beautyPicItem = list.get(position);
        holder.tv_phto_summary.setText(beautyPicItem.getTitle());
        final ImageView imageView = holder.iv_photo;
        final ViewGroup.LayoutParams params = imageView.getLayoutParams();
        int picWidth = MeasureUtils.getScreenSize(context).x / 2 - MeasureUtils
                    .dip2px(context, 4) * 2 - MeasureUtils.dip2px(context, 2);
        int picHeight = (int) (picWidth * (new Random().nextFloat() / 2 + 1));
        params.width = picWidth;
        params.height = picHeight;
        imageView.setLayoutParams(params);
        final String pic_url = beautyPicItem.getPicUrl();
        if(!TextUtils.isEmpty(pic_url)){
            ImageLoader.getInstance().displayImage(pic_url,holder.iv_photo, ImageUtils.getOptions());
        }else{
            holder.iv_photo.setImageResource(R.mipmap.ic_launcher);
        }
        holder.iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageDetailActivity.class);
                intent.putExtra("beauty_info", beautyPicItem);
                intent.putExtra("isGif",false);
                intent.putExtra("from","beauty");
                //让新的Activity从一个小的范围扩大到全屏
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeScaleUpAnimation(imageView, imageView.getWidth() / 2, imageView.getHeight() / 2, 0,
                                0);
                ActivityCompat.startActivity((Activity) context, intent, options.toBundle());

            }
        });
        setAnimation(holder.ll_container, position);

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.ll_container.clearAnimation();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_photo;
        private TextView tv_phto_summary;
        private CardView card;
        private LinearLayout ll_container;

        public ViewHolder(View beautyPicItemView) {
            super(beautyPicItemView);
            tv_phto_summary = (TextView) beautyPicItemView.findViewById(R.id.tv_photo_summary);
            iv_photo = (ImageView) beautyPicItemView.findViewById(R.id.iv_photo);
            //card = (CardView) beautyPicItemView.findViewById(R.id.beatuy_card);
            ll_container = (LinearLayout) itemView.findViewById(R.id.ll_container);

        }
    }
}
