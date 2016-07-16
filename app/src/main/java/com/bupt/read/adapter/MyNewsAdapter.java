package com.bupt.read.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bupt.read.R;
import com.bupt.read.callback.OnItemClickListener;
import com.bupt.read.model.entity.NewsItem;
import com.bupt.read.utils.ImageUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by xs on 2016/5/31.
 */
public class MyNewsAdapter extends RecyclerView.Adapter<MyNewsAdapter.ViewHolder>{

    private List<NewsItem> list;
    private Context context;
    private int lastPosition = -1;
    private OnItemClickListener mOnItemClickListener;

    public MyNewsAdapter(List<NewsItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       final View view =  LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
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
    public void onBindViewHolder(MyNewsAdapter.ViewHolder holder, int position) {
        NewsItem news = list.get(position);
        holder.tv_title.setText(news.getTitle());
        holder.tv_date.setText(news.getCtime());
        holder.tv_content.setText(news.getDescriotion());

          String pic_url = news.getPicUrl();
            if(!TextUtils.isEmpty(pic_url)){
                ImageLoader.getInstance().displayImage(pic_url,holder.iv_newspic, ImageUtils.getOptions());
            }else{
                holder.iv_newspic.setImageResource(R.drawable.loading_img);
            }
        setAnimation(holder.card,position);


    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.card.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public NewsItem getItem(int position) {
        return list == null ? null : list.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tv_title,tv_content,tv_date;
        public ImageView iv_newspic;
        public CardView card;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_news_title);
            tv_content = (TextView) itemView.findViewById(R.id.tv_news_summary);
            tv_date = (TextView) itemView.findViewById(R.id.tv_time);
            iv_newspic = (ImageView) itemView.findViewById(R.id.iv_news_img);
            card = (CardView) itemView.findViewById(R.id.card);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(itemView, this.getPosition());
            }
        }
    }
}
