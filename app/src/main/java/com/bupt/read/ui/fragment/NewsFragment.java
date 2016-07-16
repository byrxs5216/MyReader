package com.bupt.read.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bupt.read.Config.Constant;
import com.bupt.read.Config.LoadType;
import com.bupt.read.R;
import com.bupt.read.adapter.MyNewsAdapter;
import com.bupt.read.base.BaseFragment;
import com.bupt.read.callback.OnItemClickListener;
import com.bupt.read.db.NewsDaoUtil;
import com.bupt.read.model.entity.NewsItem;
import com.bupt.read.presenter.NewsPresenterImpl;
import com.bupt.read.ui.activity.NewsDetailsActivity;
import com.bupt.read.utils.JsonUtils;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.utils.NetUtils;
import com.bupt.read.utils.ToastUtil;
import com.bupt.read.view.MyBanner;
import com.bupt.read.view.mvpview.NewsListView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xs on 2016/5/29.
 */
public class NewsFragment extends BaseFragment implements NewsListView{
    private XRecyclerView mXRecyclerView;
    private MyNewsAdapter adapter;
    private NewsPresenterImpl newsPresenter;
    private MyBanner banner;
    private List<NewsItem> newsList = new ArrayList<NewsItem>();
    private List<String> img_urls = new ArrayList<>();//图片url集合
    private LinearLayoutManager layoutManager;
    private String type;
    private int page = 1;
    //private String channelId = "5572a108b3cdc86cf39001cd";
    public static NewsFragment getInstance(String type) {
        Bundle bundle = new Bundle();
        NewsFragment fragment = new NewsFragment();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString("type");
        LogUtil.i("'news type:"+type);
        if(type.equals("")){
            type = "guonei";
        }

    }

    @Override
    protected void setListener() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                LogUtil.i("刷新中...");
                if(NetUtils.checkNet(context)){
                    newsPresenter.refreshData();
                }else {
                    ToastUtil.Short("请检查网络!");
                }

            }

            @Override
            public void onLoadMore() {
                LogUtil.i("加载更多..");
                if(NetUtils.checkNet(context)){
                    newsPresenter.loadMoreData();
                }else {
                    ToastUtil.Short("请检查网络");
                    mXRecyclerView.noMoreLoading();
                }

            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                NewsItem news = adapter.getItem(position-2);
                LogUtil.i("点击了:" + position + "tilte:" + news.getTitle() + " " + "link:" + news.getUrl());
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("news", news);

                View transitionView = view.findViewById(R.id.iv_news_img);
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                transitionView, "transition_news_img");

                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        banner.setOnItemClickListener(new MyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //ToastUtil.Short("点击了第:"+position+"张图片");
                NewsItem news = adapter.getItem(position);
                LogUtil.i("点击了:" + position + "tilte:" + news.getTitle() + " " + "link:" + news.getUrl());
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("news", news);
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        LogUtil.i("initView");
        mXRecyclerView = (XRecyclerView) rootView.findViewById(R.id.news_xrecyclerview);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setHasFixedSize(true);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallBeat);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallClipRotate);
        adapter = new MyNewsAdapter(newsList, context);
        mXRecyclerView.setAdapter(adapter);
        View header =   LayoutInflater.from(context).inflate(R.layout.xrecycler_header, (ViewGroup)rootView.findViewById(android.R.id.content),false);
        mXRecyclerView.addHeaderView(header);
        banner = (MyBanner) header.findViewById(R.id.flybanner);
        loadData();


    }

    private void loadData() {
        if(NetUtils.checkNet(context)){
            newsPresenter = new NewsPresenterImpl(context,type, Constant.KEY,10,page);
            newsPresenter.attachView(this);
            newsPresenter.refreshData();
        }else{
            ToastUtil.Short("网络异常,请检查网络!");
            newsList.clear();
            newsList.addAll(NewsDaoUtil.getInstance(context).getCacheByPage(page));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        newsPresenter.detachView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_news_fragment;
    }



    @Override
    public void toast(String msg) {
        ToastUtil.toast(context, msg);

    }

    @Override
    public void showLoading() {
        dialog.show();

    }

    @Override
    public void hideLoading() {
        dialog.dismiss();

    }




    @Override
    public void onClick(View v) {

    }


    @Override
    public void updateNewsList(List<NewsItem> newsdata, int load_type) {
        switch (load_type){
            case LoadType.TYPE_REFRESH_SUCCESS:
                if(newsdata != null){
                    LogUtil.i("成功刷新数据");
                    if(newsdata.size()>=10){
                        for(int i=0;i<6;i++){
                            if(!TextUtils.isEmpty(newsdata.get(i).getPicUrl()))
                            img_urls.add(newsdata.get(i).getPicUrl());
                        }
                        banner.setImagesUrl(img_urls);
                    }
                    newsList.clear();
                    newsList.addAll(newsdata);
                    mXRecyclerView.refreshComplete();
                    //加入缓存
                    NewsDaoUtil.getInstance(context).clearAllCache();
                    NewsDaoUtil.getInstance(context).addToDb(JsonUtils.toString(newsdata), page);
                }
                break;
            case LoadType.TYPE_REFRESH_FAIL:
                ToastUtil.Short("刷新失败");
                break;
            case LoadType.TYPE_LOAD_MORE_SUCCESS:
                if(newsdata == null || newsdata.size() == 0){
                    ToastUtil.Short("已全部加载完毕");
                    mXRecyclerView.loadMoreComplete();
                    mXRecyclerView.noMoreLoading();
                }else{
                    LogUtil.i("成功加载更多数据");
                    LogUtil.i("加载的更多数据:" + newsdata.toString());
                    newsList.addAll(newsdata);
                    //adapter.notifyItemRangeInserted(newsList.size(), newsdata.size());
                    adapter.notifyDataSetChanged();
                    mXRecyclerView.loadMoreComplete();
                }
                break;
            case LoadType.TYPE_LOAD_MORE_FAIL:
                ToastUtil.Short("加载更多失败");
                break;
        }
    }

}
