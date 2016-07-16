package com.bupt.read.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bupt.read.Config.Constant;
import com.bupt.read.Config.LoadType;
import com.bupt.read.R;
import com.bupt.read.adapter.MyBeautyPicAdapter;
import com.bupt.read.base.BaseFragment;
import com.bupt.read.db.BeatyDaoUtil;
import com.bupt.read.model.entity.BeautyPicItem;
import com.bupt.read.presenter.BeautyPicPresenterImpl;
import com.bupt.read.utils.JsonUtils;
import com.bupt.read.utils.LogUtil;
import com.bupt.read.utils.NetUtils;
import com.bupt.read.utils.ToastUtil;
import com.bupt.read.view.mvpview.BeautyPicListView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xs on 2016/6/22.
 */
public class BeautyFragment extends BaseFragment implements BeautyPicListView{
    private XRecyclerView mXRecyclerView;
    private BeautyPicPresenterImpl beautyPresenter;
    private MyBeautyPicAdapter beautyPicAdapter;
    private List<BeautyPicItem> pics = new ArrayList<BeautyPicItem>();
    private StaggeredGridLayoutManager layoutManager;
    private FloatingActionButton mFabButton;
    private String keyWord = "性感";
    private int page = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    protected void setListener() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                LogUtil.i("刷新中...");
                if(NetUtils.checkNet(context)){
                    beautyPresenter.refreshData();
                }else {
                    ToastUtil.Short("请检查网络!");
                }


            }

            @Override
            public void onLoadMore() {
                LogUtil.i("加载更多..");
                if(NetUtils.checkNet(context)){
                    beautyPresenter.loadMoreData();
                }else {
                    ToastUtil.Short("请检查网络");
                    mXRecyclerView.noMoreLoading();
                }


            }
        });

        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(context)
                        .title("搜索")
                                //.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                        .input("请输入关键字", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    doSearch(input.toString());
                                } else {
                                    ToastUtil.Short("输入的内容为空");
                                }
                            }
                        }).show();
            }
        });


    }

    private void doSearch(String s) {

        if(NetUtils.checkNet(context)){
            keyWord = s;
            beautyPresenter = new BeautyPicPresenterImpl(context,"meinv", Constant.KEY,20,keyWord,page);
            beautyPresenter.attachView(this);
            beautyPresenter.refreshData();
        }else{
            ToastUtil.Short("网络异常");
        }


    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        mXRecyclerView = (XRecyclerView) rootView.findViewById(R.id.beauty_xrecyclerview);
        mFabButton = (FloatingActionButton) rootView.findViewById(R.id.fab_normal);
        layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setHasFixedSize(true);
        beautyPicAdapter = new MyBeautyPicAdapter(pics, context);
        mXRecyclerView.setAdapter(beautyPicAdapter);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallBeat);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallClipRotate);
        loadData();


    }

    private void loadData() {
        if(NetUtils.checkNet(context)){
            beautyPresenter = new BeautyPicPresenterImpl(context,"meinv", Constant.KEY,20,keyWord,1);
            beautyPresenter.attachView(this);
            beautyPresenter.refreshData();
        }else {
            ToastUtil.Short("网络异常,请检查网络!");
            LogUtil.i("从缓存中获取数据");
            pics.clear();
            pics.addAll(BeatyDaoUtil.getInstance(context).getCacheByPage(page));
            beautyPicAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        beautyPresenter.detachView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_beauty;
    }

    @Override
    public void onClick(View v) {

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
    public void updateBeautyPicList(List<BeautyPicItem> beautyPics, int load_status) {
        switch (load_status){
            case LoadType.TYPE_REFRESH_SUCCESS:
                if(beautyPics != null){
                    LogUtil.i("成功请求到数据");
                    pics.clear();
                    pics.addAll(beautyPics);
                    beautyPicAdapter.notifyDataSetChanged();
                    mXRecyclerView.refreshComplete();
                    BeatyDaoUtil.getInstance(context).clearAllCache();
                    BeatyDaoUtil.getInstance(context).addToDb(JsonUtils.toString(beautyPics),page);
                }
                break;
            case LoadType.TYPE_REFRESH_FAIL:
                ToastUtil.Short("没有获取到数据");
                break;
            case LoadType.TYPE_LOAD_MORE_SUCCESS:
                if(beautyPics == null || beautyPics.size() == 0){
                    ToastUtil.Short("已全部加载完毕");
                    mXRecyclerView.noMoreLoading();
                }else{
                    LogUtil.i("成功加载更多数据");
                    pics.addAll(beautyPics);
                    beautyPicAdapter.notifyDataSetChanged();
                    mXRecyclerView.loadMoreComplete();
                }
                break;
            case LoadType.TYPE_LOAD_MORE_FAIL:
                ToastUtil.Short("加载失败");
                break;
        }

    }
}
