package com.bupt.read.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bupt.read.Config.Constant;
import com.bupt.read.Config.LoadType;
import com.bupt.read.R;
import com.bupt.read.base.BaseActivity;
import com.bupt.read.model.entity.ImgEntity;
import com.bupt.read.model.entity.NewsDetailBody;
import com.bupt.read.model.entity.NewsItem;
import com.bupt.read.presenter.NewsDetailPresenterImpl;
import com.bupt.read.utils.ImageUtils;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.utils.ToastUtil;
import com.bupt.read.view.mvpview.NewsDetailView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

/**
 * Created by xs on 2016/6/26.
 */
public class NewsDetailsActivity extends BaseActivity implements NewsDetailView{
    private HtmlTextView ht_textView;
    private ImageView iv_news_pic;
    private TextView tv_source,tv_title;
    private CollapsingToolbarLayout toolbarLayout;
    private Toolbar toolbar;
    private String pic_url;
    private String link;
    private String source;
    private String time;
    private NewsDetailPresenterImpl newsDetailPresenter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initViews() {
        LogUtil.i("init NewsDetailActivity View..");
        ht_textView = (HtmlTextView) findViewById(R.id.ht_news_content);
        iv_news_pic = (ImageView) findViewById(R.id.iv_new_pic);
        tv_source = (TextView) findViewById(R.id.tv_news_source);
        tv_title = (TextView) findViewById(R.id.tv_news_title);
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(
                R.id.collapsing_toolbar);
        toolbarLayout.setTitle("详情");
        context = NewsDetailsActivity.this;
        dialog = createLoadingDialog(context, "正在加载", true);
        NewsItem news= (NewsItem) getIntent().getSerializableExtra("news");
        link = news.getUrl();
        source = news.getDescriotion();
        LogUtil.i("link:" + link);
        newsDetailPresenter = new NewsDetailPresenterImpl(context,"883-1", Constant.APP_ID,Constant.APP_SECRET,link);
        newsDetailPresenter.attachView(this);
        newsDetailPresenter.requestDetailInfo();

    }

    @Override
    protected void setListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_details;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void updateNewsDetail(NewsDetailBody body, int load_type) {
        switch (load_type){
            case LoadType.TYPE_REQUEST_SUCCESS:
                List<ImgEntity> entityList = body.getImg_list();
                if(entityList.size() == 0){
                    iv_news_pic.setImageResource(R.mipmap.ic_launcher);
                }else{
                    ImageLoader.getInstance().displayImage(entityList.get(0).getUrl(),iv_news_pic, ImageUtils.getOptions());
                }
                String content = body.getHtml();
                if(!TextUtils.isEmpty(content)){
                    ht_textView.setHtmlFromString(content,new HtmlTextView.LocalImageGetter());
                }else{
                    ht_textView.setText("没有数据哦");
                }
                tv_title.setText(body.getTitle());
                tv_source.setText("来源:"+source+" "+body.getTime());


                break;
            case LoadType.TYPE_REQUEST_FAILED:

                ToastUtil.Short("暂无数据..");
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newsDetailPresenter.detachView();
    }

    @Override
    public void toast(String msg) {
        ToastUtil.Short(msg);

    }

    @Override
    public void showLoading() {
        dialog.show();

    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }
}
