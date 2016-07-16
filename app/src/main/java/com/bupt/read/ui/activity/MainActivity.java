package com.bupt.read.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bupt.read.R;
import com.bupt.read.base.BaseActivity;
import com.bupt.read.ui.fragment.BeautyFragment;
import com.bupt.read.ui.fragment.JokeFragment;
import com.bupt.read.ui.fragment.NewsMainFragment;
import com.bupt.read.ui.fragment.TestFragment;
import com.bupt.read.utils.SPUtils;
import com.bupt.read.utils.ToastUtil;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private boolean isNight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            //boolean isNight = (boolean) SPUtils.get(this,"isNight",false);
            if (isNight) {
                getDelegate().setLocalNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                getDelegate().setLocalNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO);
            }
            // 调用 recreate() 使设置生效
            recreate();
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        addFragment(new NewsMainFragment());
        toolbar.setTitle("资讯");
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        switch (id){
            case  R.id.nav_news:
                addFragment(new NewsMainFragment());
                toolbar.setTitle("资讯");
                break;
            case  R.id.nav_joke:
                addFragment(new JokeFragment());
                toolbar.setTitle("笑话");
                break;
            case  R.id.nav_beauty:
                addFragment(new BeautyFragment());
                toolbar.setTitle("福利");
                break;
            case R.id.nav_settings:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;
//            case R.id.nav_night:
//                isNight = !isNight;
//                finish();
//                startActivity(new Intent(this, MainActivity.class));
//                break;
        }
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.frame_content;
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.Short("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {

    }
}
