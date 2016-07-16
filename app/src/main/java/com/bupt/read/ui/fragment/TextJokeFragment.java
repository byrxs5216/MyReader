package com.bupt.read.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.bupt.read.Config.Constant;
import com.bupt.read.Config.LoadType;
import com.bupt.read.R;
import com.bupt.read.adapter.MyTextJokesAdapter;
import com.bupt.read.base.BaseFragment;
import com.bupt.read.model.entity.TextJokeItem;
import com.bupt.read.presenter.TextJokePresenterImpl;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.utils.ToastUtil;
import com.bupt.read.view.mvpview.TextJokesListView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xs on 2016/6/21.
 */
public class TextJokeFragment extends BaseFragment implements TextJokesListView{
    private XRecyclerView mXRecyclerView;
    private TextJokePresenterImpl jokesPresenter;
    private MyTextJokesAdapter textAdapter;
    private List<TextJokeItem> text_jokes = new ArrayList<TextJokeItem>();
    private LinearLayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = createLoadingDialog("加载中...",true);
    }

    @Override
    protected void setListener() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                LogUtil.i("刷新中...");
                jokesPresenter.refreshData();

            }

            @Override
            public void onLoadMore() {
                LogUtil.i("加载更多..");
                jokesPresenter.loadMoreData();

            }
        });
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        mXRecyclerView = (XRecyclerView) rootView.findViewById(R.id.textjokes_xrecyclerview);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setHasFixedSize(true);
        textAdapter = new MyTextJokesAdapter(text_jokes, context);
        mXRecyclerView.setAdapter(textAdapter);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallBeat);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallClipRotate);
        jokesPresenter = new TextJokePresenterImpl(context,"341-1", Constant.APP_ID,Constant.APP_SECRET,"2015-07-10",1);
        jokesPresenter.attachView(this);
        jokesPresenter.refreshData();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_text_jokes;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        jokesPresenter.detachView();
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
    public void updateTextJokeDatas(List<TextJokeItem> jokedata, int load_status) {
        switch (load_status){
            case LoadType.TYPE_REFRESH_SUCCESS:
                if(jokedata != null){
                    LogUtil.i("成功请求到数据");
                    text_jokes.clear();
                    text_jokes.addAll(jokedata);
                    textAdapter.notifyDataSetChanged();
                    mXRecyclerView.refreshComplete();
                }
                break;
            case LoadType.TYPE_REFRESH_FAIL:
                ToastUtil.Short("刷新失败");
                break;
            case LoadType.TYPE_LOAD_MORE_SUCCESS:
                if(jokedata == null || jokedata.size() == 0){
                    ToastUtil.Short("已全部加载完毕");
                }else{
                    LogUtil.i("成功加载更多数据");
                    LogUtil.i("加载的更多数据:"+jokedata.toString());
                    text_jokes.addAll(jokedata);
                    textAdapter.notifyDataSetChanged();
                    mXRecyclerView.loadMoreComplete();
                }
                break;
            case LoadType.TYPE_LOAD_MORE_FAIL:
                ToastUtil.Short("加载更多失败");
                break;
        }
    }
}
