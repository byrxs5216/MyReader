package com.bupt.read.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bupt.read.R;
import com.bupt.read.model.entity.AnimationJokeItem;
import com.bupt.read.ui.activity.ImageDetailActivity;
import com.bupt.read.ui.activity.MainActivity;
import com.bupt.read.utils.ImageUtils;
import com.bupt.read.utils.TextUtil;
import com.bupt.read.view.ShowMaxImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by xs on 2016/6/19.
 */
public class MyAnimaJokesAdapter extends RecyclerView.Adapter<MyAnimaJokesAdapter.ViewHolder> {
    private List<AnimationJokeItem> list;
    private Context context;
    private boolean isGif = false;
    private Activity activity;
    private int lastPosition = -1;

    public MyAnimaJokesAdapter(List<AnimationJokeItem> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view =  LayoutInflater.from(context).inflate(R.layout.item_anima_jokes,parent,false);
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
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.card.clearAnimation();
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AnimationJokeItem animationJokeItem = list.get(position);
        holder.tv_title.setText(animationJokeItem.getTitle());
        holder.tv_time.setText(animationJokeItem.getCt());
        String pic_url = animationJokeItem.getImg();
        if(pic_url.endsWith(".gif")){
            isGif = true;
            holder.img_gif.setVisibility(View.VISIBLE);

        }else{
            isGif = false;
            holder.img_gif.setVisibility(View.GONE);
        }

        //加载缩略图
        pic_url = pic_url.replace("mw600", "small").replace("mw1200", "small").replace
                ("large", "small");

        if(!TextUtils.isEmpty(pic_url)){
            ImageLoader.getInstance().displayImage(pic_url,holder.img, ImageUtils.getOptions());
        }else{
            holder.img.setImageResource(R.mipmap.ic_launcher);
        }
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageDetailActivity.class);
                intent.putExtra("img_jokes",animationJokeItem);
                intent.putExtra("isGif",isGif);
                intent.putExtra("from","joke");
                context.startActivity(intent);
            }
        });

        holder.img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,holder.img_share);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu,popupMenu.getMenu());
                popupMenu.getMenu().removeItem(R.id.pop_copy_text);
                popupMenu.getMenu().removeItem(R.id.pop_save);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.pop_share:
                                Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                shareIntent.putExtra(Intent.EXTRA_TEXT, animationJokeItem.getTitle() + " " + animationJokeItem.getImg() + "(分享自随阅，看你想看的!)");
                                shareIntent.setType("text/plain");
                                //设置分享列表的标题，并且每次都显示分享列表
                                context.startActivity(Intent.createChooser(shareIntent, "分享到"));
                                break;
                            case R.id.pop_copy_link:
                                TextUtil.copy(context,animationJokeItem.getImg());
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        setAnimation(holder.card, position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_title,tv_time;
        public ImageView img_gif,img_share;
        public ShowMaxImageView img;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            img_gif = (ImageView) itemView.findViewById(R.id.img_gif);
            img_share = (ImageView) itemView.findViewById(R.id.img_share);
            img = (ShowMaxImageView) itemView.findViewById(R.id.img);
            card = (CardView) itemView.findViewById(R.id.card);
        }
    }
}
