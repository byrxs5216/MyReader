package com.bupt.read.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.bupt.read.R;
import com.bupt.read.base.BaseActivity;
import com.bupt.read.base.BaseFragment;
import com.bupt.read.event.ChangeThemeEvent;
import com.bupt.read.ui.fragment.SettingFragment;
import com.bupt.read.utils.RxBus;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by xs on 2016/6/26.
 */
public class SettingsActivity extends BaseActivity {
    private Toolbar toolbar;
    private FrameLayout preference;

    public Subscription rxSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        preference = (FrameLayout) findViewById(R.id.fl_preference);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //订阅并接收事件
        rxSubscription = RxBus.getDefault().toObserverable(ChangeThemeEvent.class)
                .subscribe(new Action1<ChangeThemeEvent>() {
                    @Override
                    public void call(ChangeThemeEvent event) {
                        recreate();
                    }
                });
        SettingFragment settingFragment = new SettingFragment();

        getFragmentManager().beginTransaction().replace(getFragmentContentId(),settingFragment
                ).addToBackStack(settingFragment.getClass().getSimpleName()).commitAllowingStateLoss();

    }



    @Override
    protected void setListener() {
        super.setListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.fl_preference;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消订阅
        if (!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }
}
