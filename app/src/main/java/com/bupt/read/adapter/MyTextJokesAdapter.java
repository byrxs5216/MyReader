package com.bupt.read.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bupt.read.R;
import com.bupt.read.model.entity.TextJokeItem;
import com.bupt.read.utils.TextUtil;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

/**
 * Created by xs on 2016/6/21.
 */
public class MyTextJokesAdapter extends RecyclerView.Adapter<MyTextJokesAdapter.ViewHolder>{
    private List<TextJokeItem> list;
    private Context context;
    private int lastPosition = -1;

    public MyTextJokesAdapter(List<TextJokeItem> list, Context context) {
        this.list = list;
        this.context = context;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view =  LayoutInflater.from(context).inflate(R.layout.item_text_joke,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.card.clearAnimation();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TextJokeItem jokeItem = list.get(position);
        holder.tv_title.setText(jokeItem.getTitle());
        holder.ht_content.setHtmlFromString(jokeItem.getText(), new HtmlTextView.LocalImageGetter());
        holder.img_share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,holder.img_share);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu,popupMenu.getMenu());
                popupMenu.getMenu().removeItem(R.id.pop_copy_link);
                popupMenu.getMenu().removeItem(R.id.pop_save);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.pop_share:
                                Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                shareIntent.putExtra(Intent.EXTRA_TEXT, jokeItem.getTitle() + "\n" + jokeItem.getText() + "(分享自随阅，看你想看的!)");
                                shareIntent.setType("text/plain");
                                //设置分享列表的标题，并且每次都显示分享列表
                                context.startActivity(Intent.createChooser(shareIntent, "分享到"));
                                break;
                            case R.id.pop_copy_text:
                                TextUtil.copy(context, jokeItem.getText());
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
        private TextView tv_title;
        private HtmlTextView ht_content;
        private CardView card;
        private TextView tv_ct;
        private ImageView img_share;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            ht_content = (HtmlTextView) itemView.findViewById(R.id.ht_joke_content);
            tv_ct = (TextView) itemView.findViewById(R.id.tv_time);
            card = (CardView) itemView.findViewById(R.id.text_card);
            img_share = (ImageView) itemView.findViewById(R.id.img_text_share);

        }
    }
}
